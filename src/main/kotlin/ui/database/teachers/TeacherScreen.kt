package ui.database.teachers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import common.dialog.LifecycleDialog
import common.dialog.rememberDialogLifecycleOwner
import common.extensions.container
import common.lifecycle.onDestroy
import common.navigation.destination.Fragment
import common.navigation.destination.viewModel
import common.view_model.ViewModel
import common.view_model.viewModel
import data.repository.TeachersRepository
import domain.model.Teacher
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost

class DialogTeacherViewModel(
    private val teachersRepository: TeachersRepository
) : ViewModel(), ContainerHost<Boolean, Int> {

    override val container: Container<Boolean, Int> = container(false)

}

// TODO: 02.06.2022  
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogTeacher(teacher: Teacher, onCloseRequest: () -> Unit, onUpdate: (Teacher) -> Unit) {

    val viewModel = viewModel<DialogTeacherViewModel>()

    //val state = viewModel.collectAsState().value

    //viewModel.collectSideEffect { handleSideEffect(it) }
    val dialogLifecycleOwner = rememberDialogLifecycleOwner()
    val dialogState = rememberDialogState(
        position = WindowPosition.Aligned(alignment = Alignment.Center),
        width = 400.dp,
        height = 450.dp
    )
    LifecycleDialog(
        lifecycleOwner = dialogLifecycleOwner,
        onCloseRequest = {
            onCloseRequest()
            true
        },
        state = dialogState,
        title = "Teacher", // TODO: 30.05.2022 Text
        icon = rememberVectorPainter(Icons.Default.Edit), // TODO: 30.05.2022 Icon
        resizable = false,
        onKeyEvent = {
            if (it.type == KeyEventType.KeyUp && it.key == Key.Escape) {
                onCloseRequest()
                dialogLifecycleOwner.onDestroy()
                true
            } else {
                false
            }
        }
    ) {
        var mutatedTeacher: Teacher? by remember { mutableStateOf(null) }
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = (mutatedTeacher ?: teacher).lastName,
                    label = { Text("Фамилия") },
                    onValueChange = { text ->
                        mutatedTeacher = (mutatedTeacher ?: teacher).copy(lastName = text)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = (mutatedTeacher ?: teacher).firstName,
                    label = { Text("Имя") },
                    onValueChange = { text ->
                        mutatedTeacher = (mutatedTeacher ?: teacher).copy(firstName = text)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = (mutatedTeacher ?: teacher).middleName,
                    label = { Text("Отчество") },
                    onValueChange = { text ->
                        mutatedTeacher = (mutatedTeacher ?: teacher).copy(middleName = text)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = (mutatedTeacher ?: teacher).speciality,
                    label = { Text("Должность") },
                    onValueChange = { text ->
                        mutatedTeacher = (mutatedTeacher ?: teacher).copy(speciality = text)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                onClick = { mutatedTeacher?.apply(onUpdate) },
                enabled = mutatedTeacher != null && mutatedTeacher != teacher
            ) {
                Text("Сохранить")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
object TeacherScreen : Fragment() {


    @Composable
    override fun draw() {
        val viewModel = viewModel<TeachersViewModel>()
        val teachers by viewModel.all.collectAsState(emptyList())
        val dialogState = DialogState(WindowPosition.Aligned(alignment = Alignment.Center))
        var selectedTeacher: Teacher? by remember { mutableStateOf(null) }

        selectedTeacher?.also { teacher ->
            DialogTeacher(
                teacher = teacher,
                onCloseRequest = { selectedTeacher = null },
                onUpdate = { println(it) }
            )
        }

        Column {
            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                teachers.forEach { teacher ->
                    item {
                        TeacherItem(
                            teacher = teacher,
                            onClick = {
                                selectedTeacher = teacher
                            }
                        )
                    }
                }
            }
            Card(
                modifier = Modifier.height(56.dp),
                onClick = {}
            ) {
                Text(text = "Add")
            }
        }

    }

    @Composable
    fun TeacherItem(teacher: Teacher, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            onClick = onClick
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = "${teacher.speciality}\n${teacher.firstName} ${teacher.middleName} ${teacher.lastName}"
                )
            }

            Icons.Filled.Edit
        }
    }
}