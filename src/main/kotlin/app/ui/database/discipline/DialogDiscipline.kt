package app.ui.database.discipline

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import app.ui.common.LazyColumnWithScrollbar
import app.ui.common.SimpleItemComponent
import app.ui.common.dialog.AppDialog
import app.ui.common.dialog.DialogSelection
import app.ui.common.onEscape
import common.extensions.collectState
import common.view_model.viewModel
import app.domain.model.Discipline
import app.domain.model.fullName

@Composable
fun DialogDiscipline(discipline: Discipline, onCloseRequest: () -> Unit, onUpdate: (Discipline) -> Unit) {
    val viewModel = viewModel<DisciplinesViewModel>()
    val state by viewModel.collectState()
    val (disciplines, availableTeachers, availableRooms) = state

    val dialogState = rememberDialogState(
        position = WindowPosition.Aligned(alignment = Alignment.Center),
        width = 400.dp,
        height = 450.dp
    )
    AppDialog(
        onCloseRequest = onCloseRequest,
        state = dialogState,
        title = "Дисциплина", // TODO: 30.05.2022 Text
        icon = rememberVectorPainter(Icons.Default.Edit), // TODO: 30.05.2022 Icon
        resizable = false,
        onKeyEvent = { it.onEscape(onCloseRequest) },
        contentModifier = Modifier.padding(8.dp)
    ) {
        var mutatedDiscipline: Discipline by remember { mutableStateOf(discipline) }
        var selectionTeacher: Boolean by remember { mutableStateOf(false) }
        var selectionRoom: Boolean by remember { mutableStateOf(false) }

        if (selectionTeacher) {
            DialogSelection(
                title = "Выбор преподавателя",
                list = availableTeachers,
                getText = { it.fullName },
                onSelect = { newTeacher ->
                    val newTeachers = mutatedDiscipline.teachers.toMutableList().apply {
                        add(newTeacher)
                    }
                    mutatedDiscipline = mutatedDiscipline.copy(teachers = newTeachers)
                    selectionTeacher = false
                },
                onCancel = { selectionTeacher = false }
            )
        }
        if (selectionRoom) {
            DialogSelection(
                title = "Выбор помещения",
                list = availableRooms,
                getText = { it.name },
                onSelect = { newRoom ->
                    val newRooms = mutatedDiscipline.rooms.toMutableList().apply {
                        add(newRoom)
                    }
                    mutatedDiscipline = mutatedDiscipline.copy(rooms = newRooms)
                    selectionRoom = false
                },
                onCancel = { selectionRoom = false }
            )
        }

        LazyColumnWithScrollbar(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            item("NameInput") {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = mutatedDiscipline.name,
                    label = { Text("Имя") },
                    onValueChange = { text -> mutatedDiscipline = mutatedDiscipline.copy(name = text) }
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }

            val teachers = mutatedDiscipline.teachers
            teachers.forEachIndexed { index, teacher ->
                item(index to teacher) {
                    SimpleItemComponent(
                        title = teacher.fullName,
                        caption = teacher.speciality,
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = {
                            val newTeachers = teachers.toMutableList().apply { removeAt(index) }
                            mutatedDiscipline = mutatedDiscipline.copy(teachers = newTeachers)
                        }
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item("AddTeacher") {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { selectionTeacher = true }
                ) {
                    Text("Добавить преподавателя")
                }
            }
            item {  Spacer(modifier = Modifier.height(24.dp)) }

            val rooms = mutatedDiscipline.rooms
            rooms.forEachIndexed { index, room ->
                item(index to room) {
                    SimpleItemComponent(
                        title = room.name,
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = {
                            val newTeachers = rooms.toMutableList().apply { removeAt(index) }
                            mutatedDiscipline = mutatedDiscipline.copy(rooms = newTeachers)
                        }
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item("AddRoom") {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { selectionRoom = true }
                ) {
                    Text("Добавить помещение")
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { mutatedDiscipline.apply(onUpdate) },
            enabled = mutatedDiscipline != discipline
        ) {
            Text("Сохранить")
        }
    }
}
