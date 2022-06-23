package app.ui.schedule.create.plan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberDialogState
import app.mock.Mock
import app.ui.common.SimpleItemComponent
import app.ui.common.dialog.AppDialog
import app.ui.common.dialog.DialogSelection
import common.extensions.container
import common.view_model.ViewModel
import common.view_model.viewModel
import domain.model.*
import domain.model.plan.GroupPlan
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

private val GroupPlanLocal = compositionLocalOf { emptyList<GroupPlan>() }

data class AcademicPlanState(
    val plan: List<GroupPlan> = emptyList()
)

class AcademicPlanViewModel : ViewModel(), ContainerHost<AcademicPlanState, Unit> {

    override val container = container<AcademicPlanState, Unit>(AcademicPlanState(mockGroupPlans))

    val availableGroups: Set<Group> = Mock.studentGroups(20).toSet() // TODO: 24.06.2022 Mock

    fun setPlan(plan: GroupPlan, index: Int) = intent {
        reduce { state.copy(plan = state.plan.toMutableList().apply { set(index, plan) }) }
    }
    fun addPlan(plan: GroupPlan) = intent {
        reduce { state.copy(plan = state.plan.toMutableList().apply { add(plan) }) }
    }

    fun deletePlan(plan: GroupPlan) = intent {
        reduce { state.copy(plan = state.plan.toMutableList().apply { remove(plan) }) }
    }

    companion object {

        private val mockGroupPlans: List<GroupPlan> = Mock.studentGroups(5)
            .map { group -> group to Mock.teachings(5).map { it to 10 } }
            .map { GroupPlan(it.first, it.second) }
    }

}

/**
 * Экран формирования учебного плана для каждой группы
 */
@Composable
fun ScheduleCreatingAcademicPlanScreen() {
    val viewModel = viewModel<AcademicPlanViewModel>()
    val state by viewModel.container.stateFlow.collectAsState()

    //Диалог измененеия конкретного экрана
    var dialogGroupPlanEditor: GroupPlan? by remember { mutableStateOf(null) }

    dialogGroupPlanEditor?.also { plan ->
        CompositionLocalProvider(GroupPlanLocal provides state.plan) {
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
                        title = plan.group.name,
                        titleStyle = MaterialTheme.typography.h6,
                        subtitle = "${plan.plan.size} предмета, ${plan.plan.sumOf { it.second }} часов",
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
                dialogGroupPlanEditor = GroupPlan(Group.Empty)
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
    val isPlanCreating = groupPlan.group == Group.Empty
    var mutablePlan: GroupPlan by remember { mutableStateOf(groupPlan.copy()) }
    val group = mutablePlan.group
    val plan = mutablePlan.plan
    var groupSelection: Boolean by remember { mutableStateOf(false) }
    var teachingEditing: Triple<Teaching, AcademicHour, Int?>? by remember { mutableStateOf(null) }
    if (groupSelection) {
        val groups = viewModel.availableGroups.toMutableList().apply { removeAll(GroupPlanLocal.current.map { it.group }) }
        DialogSelection(
            title = "Выбор группы",
            list = groups,
            getText = { it.name },
            onSelect = { mutablePlan = mutablePlan.copy(group = it); groupSelection = false },
            onCancel = { groupSelection = false }
        )
    }
    teachingEditing?.also { (t, h, index) ->
        DialogTeachingEditor(
            teaching = t,
            hours = h,
            onCommit = { teaching, hours ->
                val newPlan = mutablePlan.plan.toMutableList()
                if (index == null) {
                    newPlan.add(teaching to hours)
                } else {
                    newPlan[index] = teaching to hours
                }
                mutablePlan = mutablePlan.copy(plan = newPlan)
                teachingEditing = null
            },
            onCancel = { teachingEditing = null })
    }

    AppDialog(
        onCloseRequest = { if (isPlanCreating) onCancel() else onCommit(mutablePlan) },
        state = rememberDialogState(width = 400.dp, height = 600.dp),
        contentModifier = Modifier.padding(8.dp),
        title = "План группы"
    ) {
        if (group == Group.Empty) {
            SimpleItemComponent(
                modifier = Modifier.sizeIn(minHeight = 44.dp),
                onClick = { groupSelection = true },
                title = "Выбрать группу",
                titleStyle = MaterialTheme.typography.h5,
                titleAlignment = Alignment.CenterHorizontally
            )
        } else {
            SimpleItemComponent(
                title = group.name,
                titleStyle = MaterialTheme.typography.h6,
                subtitle = "${mutablePlan.plan.size} предмета, ${mutablePlan.plan.sumOf { it.second }} часов",
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
            plan.forEachIndexed { index, (teaching, hours) ->
                item {
                    SimpleItemComponent(
                        title = teaching.discipline.name,
                        subtitle = teaching.type.text,
                        caption = hours.toString(),
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = {
                            mutablePlan = mutablePlan.copy(
                                plan = mutablePlan.plan.toMutableList().apply { removeAt(index) }
                            )
                        },
                        onClick = { teachingEditing = Triple(teaching, hours, index) }
                    )
                }
            }
        }
        if (group != Group.Empty) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = { teachingEditing = Triple(Teaching.Empty, 0, null) }
            ) {
                Text(text = "Добавить предмет")
            }
        }
        if (isPlanCreating) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                enabled = mutablePlan != groupPlan && mutablePlan.plan.isNotEmpty(),
                onClick = { onCommit(mutablePlan) }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}

/**
 * Диалог для добавления/изменения предмета и часов
 */
@Composable
fun DialogTeachingEditor(
    teaching: Teaching,
    hours: AcademicHour,
    onCommit: (Teaching, AcademicHour) -> Unit,
    onCancel: () -> Unit
) {

    var mutableTeaching: Teaching by remember { mutableStateOf(teaching.copy()) }
    val (_, discipline, type) = mutableTeaching
    var mutableHours: AcademicHour by remember { mutableStateOf(hours) }

    var selectionType: Boolean by remember { mutableStateOf(false) }
    var selectionDiscipline: Boolean by remember { mutableStateOf(false) }

    if (selectionType) {
        DialogSelection(
            "Выбор типа",
            list = Teaching.Type.values()
                .toList().filter { it != Teaching.Type.UNSPECIFIED }
                .mapIndexed { index, item -> SubModel(id = index.toLong(), item = item) },
            getText = { it.item.text },
            onSelect = { mutableTeaching = mutableTeaching.copy(type = it.item); selectionType = false },
            onCancel = { selectionType = false }
        )
    }
    if (selectionDiscipline) {
        DialogSelection(
            title = "Выбор предмета",
            list = Mock.disciplines(20),
            getText = { it.name },
            onSelect = { mutableTeaching = mutableTeaching.copy(discipline = it); selectionDiscipline = false },
            onCancel = { selectionDiscipline = false }
        )
    }

    AppDialog(
        onCloseRequest = onCancel,
        state = rememberDialogState(width = 300.dp, height = 400.dp),
        contentModifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (discipline == Discipline.Empty) {
                SimpleItemComponent(
                    title = "Выбрать предмет",
                    onClick = {
                        selectionDiscipline = true
                    }
                )
            } else {
                SimpleItemComponent(
                    title = discipline.name,
                    subtitle = type.text,
                    onClick = {
                        selectionDiscipline = true
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                SimpleItemComponent(
                    modifier = Modifier.height(44.dp),
                    title = "Изменить тип",
                    onClick = { selectionType = true }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = mutableHours.toString(),
                    onValueChange = { mutableHours = it.toIntOrNull() ?: mutableHours },
                    label = { Text(text = "Кол-во часов") }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = (teaching != mutableTeaching || hours != mutableHours) && mutableHours > 0 && type != Teaching.Type.UNSPECIFIED,
            onClick = { onCommit(mutableTeaching, mutableHours) }
        ) {
            Text("Сохранить")
        }
    }

}
