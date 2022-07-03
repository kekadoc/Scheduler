package app.ui.database.teachers.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import app.ui.common.onEscape
import app.domain.model.Teacher
import app.domain.model.isAllFieldsDeclared

@Composable
fun DialogTeacher(
    teacher: Teacher,
    onCloseRequest: () -> Unit,
    onUpdate: (Teacher) -> Unit
) {
    var mutatedTeacher: Teacher by remember { mutableStateOf(teacher) }

    val dialogState = rememberDialogState(
        position = WindowPosition.Aligned(alignment = Alignment.Center),
        width = 400.dp,
        height = 450.dp
    )
    Dialog(
        onCloseRequest = onCloseRequest,
        state = dialogState,
        title = "Teacher", // TODO: 30.05.2022 Text
        icon = rememberVectorPainter(Icons.Default.Edit), // TODO: 30.05.2022 Icon
        resizable = false,
        onKeyEvent = { event -> event.onEscape(onCloseRequest) }
    ) {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mutatedTeacher.lastName,
                        label = { Text("Фамилия") }, // TODO: 30.05.2022 Text
                        onValueChange = { text -> mutatedTeacher = mutatedTeacher.copy(lastName = text) }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mutatedTeacher.firstName,
                        label = { Text("Имя") }, // TODO: 30.05.2022 Text
                        onValueChange = { text -> mutatedTeacher = mutatedTeacher.copy(firstName = text) }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mutatedTeacher.middleName,
                        label = { Text("Отчество") }, // TODO: 30.05.2022 Text
                        onValueChange = { text -> mutatedTeacher = mutatedTeacher.copy(middleName = text) }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mutatedTeacher.speciality,
                        label = { Text("Должность") }, // TODO: 30.05.2022 Text
                        onValueChange = { text -> mutatedTeacher = mutatedTeacher.copy(speciality = text) }
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    enabled = teacher != mutatedTeacher && mutatedTeacher.isAllFieldsDeclared,
                    onClick = { onUpdate(mutatedTeacher) }
                ) {
                    Text(text = "Сохранить") // TODO: 30.05.2022 Text
                }
            }
        }
    }
}