package app.ui.schedule.create.plan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberDialogState
import app.mock.Mock
import app.ui.common.SimpleItemComponent
import app.ui.common.dialog.AppDialog
import app.ui.common.dialog.DialogSelection
import common.extensions.orElse
import domain.model.Discipline
import domain.model.WorkType
import domain.model.fullName
import schedule.plan.DisciplinePlan

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