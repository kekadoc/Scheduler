@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.discipline

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import app.mock.Mock
import app.ui.common.SimpleItemComponent
import app.ui.common.dialog.DialogSelection
import common.extensions.container
import common.extensions.emptyString
import common.view_model.ViewModel
import common.view_model.viewModel
import data.data_source.local.unit.discipline.DisciplineLocalDataSource
import data.repository.TeachersRepository
import domain.model.Discipline
import domain.model.Room
import domain.model.Teacher
import domain.model.fullName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost

class DisciplinesViewModel(
    private val localDataSource: DisciplineLocalDataSource
) : ViewModel() {

    val disciplines = listOf(
        Discipline(id = 0, name = "Биология", description = "", targetTeacher = Mock.TEACHER, targetRoom = Mock.ROOM),
        Discipline(id = 1, name = "Физика", description = ""),
        Discipline(id = 2, name = "Химия", description = ""),
    )

    private val disciplinesFlow = MutableStateFlow(disciplines)

    val all: Flow<List<Discipline>>
        get() = disciplinesFlow

    fun update(discipline: Discipline) {
        disciplinesFlow.value = disciplinesFlow.value.toMutableList().apply {
            removeIf { it.id == discipline.id }
            add(discipline)
        }
    }

    fun delete(teacher: Discipline) {
        disciplinesFlow.value = disciplinesFlow.value.toMutableList().apply {
            removeIf { it.id == teacher.id }
        }
    }

}

@Composable
fun DisciplinesDatabaseScreen() {
    val viewModel = viewModel<DisciplinesViewModel>()
    val disciplines by viewModel.all.collectAsState(emptyList())
    var selectedDiscipline: Discipline? by remember { mutableStateOf(null) }

    selectedDiscipline?.also { discipline ->
        DialogDiscipline(
            discipline = discipline,
            onCloseRequest = { selectedDiscipline = null },
            onUpdate = {
                viewModel.update(it)
                selectedDiscipline = null
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            disciplines.forEach { discipline ->
                item {
                    DisciplineItem(
                        discipline = discipline,
                        onClick = { selectedDiscipline = discipline },
                        onDelete = { viewModel.delete(it) }
                    )
                }
            }
        }
        Card(
            modifier = Modifier.height(56.dp).fillMaxWidth(),
            onClick = {
                selectedDiscipline = Discipline(
                    id = disciplines.maxOfOrNull { it.id }?.inc() ?: 0L,
                    name = emptyString(),
                    description = emptyString()
                )
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Add")
            }
        }
    }

}

@Composable
fun DisciplineItem(discipline: Discipline, onClick: (Discipline) -> Unit, onDelete: (Discipline) -> Unit) {
    Card(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        onClick = { onClick(discipline) }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1.0f),
                text = discipline.name
            )
            Button(
                modifier = Modifier.size(44.dp).padding(0.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { onDelete(discipline) }
            ) {
                Image(
                    modifier = Modifier.size(44.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    }
}

class DialogTeacherViewModel(
    private val teachersRepository: TeachersRepository
) : ViewModel(), ContainerHost<Boolean, Int> {

    override val container: Container<Boolean, Int> = container(false)

}

// TODO: 02.06.2022
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogDiscipline(discipline: Discipline, onCloseRequest: () -> Unit, onUpdate: (Discipline) -> Unit) {
    val dialogState = rememberDialogState(
        position = WindowPosition.Aligned(alignment = Alignment.Center),
        width = 400.dp,
        height = 450.dp
    )
    Dialog(
        onCloseRequest = {
            onCloseRequest()
        },
        state = dialogState,
        title = "Discipline", // TODO: 30.05.2022 Text
        icon = rememberVectorPainter(Icons.Default.Edit), // TODO: 30.05.2022 Icon
        resizable = false,
        onKeyEvent = {
            if (it.type == KeyEventType.KeyUp && it.key == Key.Escape) {
                onCloseRequest()
                true
            } else {
                false
            }
        },
    ) {
        var mutatedDiscipline: Discipline? by remember { mutableStateOf(null) }
        val targetTeacher = (mutatedDiscipline ?: discipline).targetTeacher
        val targetRoom = (mutatedDiscipline ?: discipline).targetRoom
        var selectionTeacher: Boolean by remember { mutableStateOf(false) }
        var selectionRoom: Boolean by remember { mutableStateOf(false) }
        if (selectionTeacher) {
            DialogSelection(
                title = "Выбор преподавателя",
                list = Mock.teachers(20),
                getText = { it.fullName },
                onSelect = { newTeacher ->
                    mutatedDiscipline = (mutatedDiscipline ?: discipline).copy(targetTeacher = newTeacher)
                    selectionTeacher = false
                },
                onCancel = { selectionTeacher = false }
            )
        }
        if (selectionRoom) {
            DialogSelection(
                title = "Выбор кабинета",
                list = Mock.studyRooms(20),
                getText = { it.name },
                onSelect = { newRoom ->
                    mutatedDiscipline = (mutatedDiscipline ?: discipline).copy(targetRoom = newRoom)
                    selectionRoom = false
                },
                onCancel = { selectionRoom = false }
            )
        }
        Surface {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = (mutatedDiscipline ?: discipline).name,
                        label = { Text("Имя") },
                        onValueChange = { text ->
                            mutatedDiscipline = (mutatedDiscipline ?: discipline).copy(name = text)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = (mutatedDiscipline ?: discipline).description,
                        label = { Text("Описание") },
                        onValueChange = { text ->
                            mutatedDiscipline = (mutatedDiscipline ?: discipline).copy(description = text)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TeacherCard(
                        teacher = targetTeacher,
                        onClick = { selectionTeacher = true  },
                        onDelete = { mutatedDiscipline = (mutatedDiscipline ?: discipline).copy(targetTeacher = Teacher.Empty) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RoomCard(
                        room = targetRoom,
                        onClick = { selectionRoom = true },
                        onDelete = { mutatedDiscipline = (mutatedDiscipline ?: discipline).copy(targetRoom = Room.Empty ) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Button(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    onClick = { mutatedDiscipline?.apply(onUpdate) },
                    enabled = mutatedDiscipline != null && mutatedDiscipline != discipline
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}



@Composable
private fun TeacherCard(
    teacher: Teacher? = null,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    if (teacher == null) {
        SimpleItemComponent(
            modifier = Modifier,
            title = "Выбрать преподавателя",
            onClick = onClick
        )
    } else {
        SimpleItemComponent(
            modifier = Modifier,
            onRightImageClick = onDelete,
            rightImage = Icons.Default.Delete,
            title = teacher.fullName,
            subtitle = teacher.speciality,
            onClick = onClick
        )
    }
}

@Composable
private fun RoomCard(
    room: Room?,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    if (room == null) {
        SimpleItemComponent(
            modifier = Modifier,
            title = "Выбрать кабинет",
            onClick = onClick
        )
    } else {
        SimpleItemComponent(
            modifier = Modifier,
            onRightImageClick = onDelete,
            rightImage = Icons.Default.Delete,
            title = room.name,
            onClick = onClick
        )
    }

}
