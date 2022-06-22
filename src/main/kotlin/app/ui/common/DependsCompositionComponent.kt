package app.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.ui.common.dialog.DialogSelection
import app.ui.common.dialog.DialogStyle
import app.ui.common.dialog.Long
import domain.model.Room
import domain.model.Teacher
import domain.model.fullName

@Composable
fun <Type> BaseDependsCompositionComponent(
    modifier: Modifier = Modifier,
    items: Map<Type, Int>,
    buildItem: @Composable (Type, Int) -> Unit,
    buildTypeAction: @Composable (Type) -> Unit,
) {

    LazyColumnWithScrollbar(
        modifier = modifier
    ) {
        items.forEach { (type, count) ->
            repeat(count) { index ->
                item(key = type to index, content = { buildItem(type, index) })
            }
            item(type) { buildTypeAction(type) }
        }
    }
}

@Composable
fun TestDependsDialog(
    onCLose: () -> Unit
) {
    Dialog(
        onCloseRequest = onCLose,
        state = DialogStyle.Long.states.large
    ) {
        Surface {
            BaseDependsCompositionComponent<Int>(
                modifier = Modifier.fillMaxSize(),
                items = mapOf(0 to 3, 1 to 1, 2 to 0),
                buildItem = { type, index ->
                    CardBox(
                        modifier = Modifier.height(56.dp).fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Type=$type Index=$index"
                        )
                    }
                },
                buildTypeAction = { type ->
                    Button(
                        onClick = {}
                    ) {
                        Text("Action type $type")
                    }
                }
            )
        }
    }
}


private enum class AcademicSubjectDepends {
    TEACHER,
    ROOM
}

@Composable
fun DialogAcademicSubjectInfo(
    currentTeachers: List<Teacher>,
    availableTeachers: List<Teacher>,
    currentRoom: List<Room>,
    availableRoom: List<Room>,
    onCommit: (List<Teacher>, List<Room>) -> Unit
) {

    val nowTeachers = remember { mutableStateListOf(currentTeachers) }
    val nowRooms = remember { mutableStateListOf(currentRoom) }

    val items = mapOf(
        AcademicSubjectDepends.TEACHER to nowTeachers.size,
        AcademicSubjectDepends.ROOM to nowRooms.size
    )

    var teacherSelection by remember { mutableStateOf(false) }
    var roomSelection by remember { mutableStateOf(false) }

    if (teacherSelection) {
        DialogSelection(
            title = "Выбор преподавателя",
            list = availableTeachers,
            getText = { it.fullName },
            onSelect = { nowTeachers.add(it); teacherSelection = false },
            onCancel = { teacherSelection = false }
        )
    }
    if (roomSelection) {
        DialogSelection(
            title = "Выбор помещения",
            list = availableRoom,
            getText = { it.name },
            onSelect = { nowRooms.add(it); roomSelection = false },
            onCancel = { roomSelection = false }
        )
    }

    Dialog(
        onCloseRequest = { onCommit(nowTeachers, nowRooms) },
        resizable = false,
        state = DialogStyle.Long.states.large
    ) {
        Surface {
            BaseDependsCompositionComponent<AcademicSubjectDepends>(
                modifier = Modifier.fillMaxSize(),
                items = items,
                buildItem = { type, index ->
                    when (type) {
                        AcademicSubjectDepends.TEACHER -> {
                            Box(
                                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                            ) {
                                CardBox(
                                    modifier = Modifier.height(56.dp).fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = nowTeachers[index].fullName
                                    )
                                }
                            }
                        }
                        AcademicSubjectDepends.ROOM -> {
                            Box(
                                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                            ) {
                                CardBox(
                                    modifier = Modifier.height(56.dp).fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = nowRooms[index].name
                                    )
                                }
                            }
                        }
                    }
                },
                buildTypeAction = { type ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (type) {
                            AcademicSubjectDepends.TEACHER -> {
                                Button(
                                    onClick = { teacherSelection = true }
                                ) {
                                    Text("Добавить преподавателя")
                                }
                            }
                            AcademicSubjectDepends.ROOM -> {
                                Button(
                                    onClick = { roomSelection = true }
                                ) {
                                    Text("Добавить кабинет")
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}