package app.ui.schedule.create.plan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberDialogState
import app.domain.model.Discipline
import app.domain.model.Group
import app.schedule.plan.DisciplinePlan
import app.schedule.plan.GroupPlan
import app.ui.common.SimpleItemComponent
import app.ui.common.dialog.AppDialog
import app.ui.common.dialog.DialogSelection
import common.extensions.collectState
import common.logger.Logger
import common.view_model.viewModel

private val GroupPlansLocal = compositionLocalOf { emptyList<GroupPlan>() }

/**
 * Экран формирования учебного плана для каждой группы
 */
@Composable
fun ScheduleCreatingAcademicPlanScreen() {
    val viewModel = viewModel<AcademicPlanViewModel>()
    val state by viewModel.container.stateFlow.collectAsState()

    //Диалог измененеия учебного плана группы
    var dialogGroupPlanEditor: GroupPlan? by remember { mutableStateOf(null) }

    dialogGroupPlanEditor?.also { plan ->
        CompositionLocalProvider(GroupPlansLocal provides state.plan) {
            val index = state.plan.indexOf(plan)
            DialogGroupPlanEditor(
                groupPlan = plan,
                onCommit = { newPlan ->
                    if (index >= 0) viewModel.setPlan(newPlan, index)
                    else viewModel.addPlan(newPlan)
                    dialogGroupPlanEditor = null
                },
                onCancel = { dialogGroupPlanEditor = null }
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.plan.forEach { plan ->
                item(key = plan.group.id) {
                    SimpleItemComponent(
                        label = plan.group.name,
                        labelStyle = MaterialTheme.typography.h6,
                        title = "${plan.getAll().size} предмета, ${plan.getAll().values.sumOf { it.works.values.sum() }} часов",
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = { viewModel.deletePlan(plan) },
                        onClick = { dialogGroupPlanEditor = plan }
                    )
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
            onClick = {
                dialogGroupPlanEditor = GroupPlan(Group.Empty) // TODO: 27.06.2022 Прокидывать кол-во недель
            }
        ) {
            Text(text = "Add group")
        }
    }
}

/**
 * Диалог для добавления/изменения учебного плана для группы
 */
@Composable
private fun DialogGroupPlanEditor(
    groupPlan: GroupPlan,
    onCancel: () -> Unit,
    onCommit: (GroupPlan) -> Unit
) {
    val viewModel = viewModel<AcademicPlanViewModel>()
    val state by viewModel.collectState()
    val (availableGroups, planList) = state
    Logger.log(availableGroups.toString())
    val isPlanCreating: Boolean = remember { groupPlan.group == Group.Empty }

    var group: Group by remember { mutableStateOf(groupPlan.group) }

    val plans: MutableMap<Discipline, DisciplinePlan> = remember {
        SnapshotStateMap<Discipline, DisciplinePlan>().apply { putAll(groupPlan.items) }
    }

    var groupSelection: Boolean by remember { mutableStateOf(false) }
    var teachingEditing: DisciplinePlan? by remember { mutableStateOf(null) }

    if (groupSelection) {
        val groups = availableGroups.toMutableList().apply { removeAll(GroupPlansLocal.current.map { it.group }) }
        DialogSelection(
            title = "Выбор группы",
            list = groups,
            getText = { it.name },
            onSelect = { group = it; groupSelection = false },
            onCancel = { groupSelection = false }
        )
    }
    teachingEditing?.also { plan ->
        DialogDisciplinePlanEditor(
            plan = plan,
            onCommit = { newPlan -> plans[newPlan.discipline] = newPlan; teachingEditing = null },
            onCancel = { teachingEditing = null }
        )
    }

    AppDialog(
        onCloseRequest = {
            if (isPlanCreating) {
                onCancel()
            } else {
                onCommit(groupPlan.copy(group = group, items = plans))
            }
        },
        state = rememberDialogState(width = 400.dp, height = 600.dp),
        contentModifier = Modifier.padding(8.dp),
        title = "План группы"
    ) {
        if (group == Group.Empty) {
            SimpleItemComponent(
                modifier = Modifier.sizeIn(minHeight = 44.dp),
                onClick = { groupSelection = true },
                label = "Выбрать группу",
                labelStyle = MaterialTheme.typography.h5,
                labelAlignment = Alignment.CenterHorizontally
            )
        } else {
            SimpleItemComponent(
                label = group.name,
                labelStyle = MaterialTheme.typography.h6,
                title = "${plans.size} предмета, ${plans.values.sumOf { it.works.values.sum() }} часов",
                onClick = if (isPlanCreating) {
                    { groupSelection = true }
                } else {
                    null
                }
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
        ) {
            plans.values.forEachIndexed { index, plan ->
                item {
                    SimpleItemComponent(
                        label = plan.discipline.name,
                        title = plan.works.values.sum().toString(),
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = {
                            plans.remove(plan.discipline)
                        },
                        onClick = {
                            teachingEditing = plan
                        }
                    )
                }
            }
        }
        if (group != Group.Empty) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = { teachingEditing = DisciplinePlan(Discipline.Empty) }
            ) {
                Text(text = "Добавить предмет")
            }
        }
        if (isPlanCreating) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                enabled = plans != groupPlan.items && plans.isNotEmpty(),
                onClick = { onCommit(groupPlan.copy(group = group, items = plans)) }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}

