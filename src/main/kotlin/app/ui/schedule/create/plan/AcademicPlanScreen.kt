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
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberDialogState
import app.mock.Mock
import app.ui.common.SimpleItemComponent
import app.ui.common.dialog.AppDialog
import app.ui.common.dialog.DialogSelection
import common.extensions.container
import common.extensions.orElse
import common.view_model.ViewModel
import common.view_model.viewModel
import domain.model.Discipline
import domain.model.Group
import domain.model.WorkType
import domain.model.fullName
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import schedule.plan.DisciplinePlan
import schedule.plan.GroupPlan

private val GroupPlansLocal = compositionLocalOf { emptyList<GroupPlan>() }

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

        private val mockGroupPlans: List<GroupPlan> = Mock.groupPlans(5)
    }

}

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
    val isPlanCreating: Boolean = remember { groupPlan.group == Group.Empty }
    var group: Group by remember { mutableStateOf(groupPlan.group) }
    val plans: MutableMap<Discipline, DisciplinePlan> = remember {
        SnapshotStateMap<Discipline, DisciplinePlan>().apply { putAll(groupPlan.items) }
    }

    var groupSelection: Boolean by remember { mutableStateOf(false) }
    var teachingEditing: DisciplinePlan? by remember { mutableStateOf(null) }

    if (groupSelection) {
        val groups = viewModel.availableGroups.toMutableList().apply { removeAll(GroupPlansLocal.current.map { it.group }) }
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

/**
 * Диалог для добавления/изменения плана дисциплины
 */
@Composable
fun DialogDisciplinePlanEditor(
    plan: DisciplinePlan,
    onCommit: (DisciplinePlan) -> Unit,
    onCancel: () -> Unit
) {
    val isCreating = remember { plan.discipline == Discipline.Empty }
    var mutablePlan: DisciplinePlan by remember { mutableStateOf(plan.copy()) }

    val (discipline, teacher, room, works, fillingType) = mutablePlan

    var selectionDiscipline: Boolean by remember { mutableStateOf(false) }
    var selectionTeacher: Boolean by remember { mutableStateOf(false) }
    var selectionRoom: Boolean by remember { mutableStateOf(false) }


    if (selectionDiscipline) {
        DialogSelection(
            title = "Выбор предмета",
            list = Mock.disciplines(20),
            getText = { it.name },
            onSelect = { mutablePlan = mutablePlan.copy(discipline = it); selectionDiscipline = false },
            onCancel = { selectionDiscipline = false }
        )
    }
    if (selectionTeacher) {
        DialogSelection(
            title = "Выбор предмета",
            list = Mock.teachers(20), // TODO: 28.06.2022  Mock
            getText = { it.fullName },
            onSelect = { mutablePlan = mutablePlan.copy(teacher = it); selectionTeacher = false },
            onCancel = { selectionTeacher = false }
        )
    }
    if (selectionRoom) {
        DialogSelection(
            title = "Выбор предмета",
            list = Mock.rooms(20), // TODO: 28.06.2022  Mock
            getText = { it.name },
            onSelect = { mutablePlan = mutablePlan.copy(room = it); selectionRoom = false },
            onCancel = { selectionRoom = false }
        )
    }

    AppDialog(
        onCloseRequest = onCancel,
        state = rememberDialogState(width = 350.dp, height = 500.dp),
        contentModifier = Modifier.padding(8.dp),
        title = if (isCreating) "Создание" else "Редактирование"
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            if (discipline == Discipline.Empty) {
                item {
                    SimpleItemComponent(
                        modifier = Modifier.height(56.dp),
                        title = "Выбрать предмет",
                        onClick = {
                            selectionDiscipline = true
                        }
                    )
                }
            } else {
                //Spacer(modifier = Modifier.height(8.dp))
                //todo Возможно выбирать предмет можно только при создании плана, а не редактивровании
                item {
                    SimpleItemComponent(
                        modifier = Modifier.height(56.dp),
                        title = discipline.name
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    SimpleItemComponent(
                        modifier = Modifier,
                        label = "Преподаватель:",
                        title = teacher.fullName,
                        onClick = {
                            selectionTeacher = true
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item {
                    SimpleItemComponent(
                        modifier = Modifier,
                        label = "Помещение:",
                        title = room.name,
                        onClick = {
                            selectionRoom = true
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = works[WorkType.LECTURE].orElse { 0 }.toString(),
                        onValueChange = { value ->
                            val hours = value.toIntOrNull() ?: return@OutlinedTextField
                            val newWorks = mutablePlan.works.toMutableMap().apply { put(WorkType.LECTURE, hours) }
                            mutablePlan = mutablePlan.copy(works = newWorks)
                        },
                        label = { Text(text = "Лекционных часов") }
                    )
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = works[WorkType.LABORATORY].orElse { 0 }.toString(),
                        onValueChange = { value ->
                            val hours = value.toIntOrNull() ?: return@OutlinedTextField
                            val newWorks = mutablePlan.works.toMutableMap().apply { put(WorkType.LABORATORY, hours) }
                            mutablePlan = mutablePlan.copy(works = newWorks)
                        },
                        label = { Text(text = "Лабораторных часов") }
                    )
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = works[WorkType.PRACTICE].orElse { 0 }.toString(),
                        onValueChange = { value ->
                            val hours = value.toIntOrNull() ?: return@OutlinedTextField
                            val newWorks = mutablePlan.works.toMutableMap().apply { put(WorkType.PRACTICE, hours) }
                            mutablePlan = mutablePlan.copy(works = newWorks)
                        },
                        label = { Text(text = "Практических часов") }
                    )
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = (plan != mutablePlan) && mutablePlan.works.values.sum() > 0,
            onClick = { onCommit(mutablePlan) }
        ) {
            Text("Сохранить")
        }
    }

}
