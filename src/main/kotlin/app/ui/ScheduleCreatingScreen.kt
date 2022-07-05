@file:OptIn(ExperimentalMaterialApi::class)

package app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import app.domain.model.AcademicHour
import app.domain.model.Discipline
import app.domain.model.Group
import app.mock.Mock
import common.ui.CardBox
import common.ui.dialog.DialogChecking

private enum class ItemType {
    GROUP,
    TEACHER,
    ROOM,
    SUBJECT
}

@Composable
fun ScheduleCreatingScreen() {
    var selection: ItemType? by remember { mutableStateOf(null) }
    var groupInfo: Pair<Group, Boolean>? by remember { mutableStateOf(null) }

    groupInfo?.let { (group, _) ->
        DialogGroupInfo(
            group = group,
            availableSubjects = Mock.disciplines(20),
            onClose = { groupInfo = null },
            selectedSubjects = emptyMap()
        )
    }
    selection?.also { selectedType ->
        when (selectedType) {
            ItemType.GROUP -> {
                DialogChecking(
                    title = "Группы",
                    list = Mock.studentGroups(20).map { it to true }.toMap(),
                    getText = { it.name },
                    onCommit = {},
                    onItemClick = {
                        groupInfo = it
                    }
                )
            }
            ItemType.TEACHER -> {
                DialogChecking(
                    title = "Учителя",
                    list = Mock.studentGroups(20).map { it to true }.toMap(),
                    getText = { it.name },
                    onCommit = {},
                    onItemClick = { }
                )
            }
            ItemType.ROOM -> {
                DialogChecking(
                    title = "Кабинеты",
                    list = Mock.studentGroups(20).map { it to true }.toMap(),
                    getText = { it.name },
                    onCommit = {},
                    onItemClick = { }
                )
            }
            ItemType.SUBJECT -> {
                DialogChecking(
                    title = "Предметы",
                    list = Mock.studentGroups(20).map { it to true }.toMap(),
                    getText = { it.name },
                    onCommit = {},
                    onItemClick = { }
                )
            }
        }
/*        DialogGroupInfo(
            group = StudentGroup(0, "ASU"),
            availableSubjects = Mock.academicSubjects(20),
            onClose = {},
            selectedSubjects = emptyMap()
        )*/
        /*DialogSelection(
            items = items,
            onCommit = {
                when (selectedType) {
                    ItemType.GROUP -> println("Ready GROUP $it")
                    ItemType.TEACHER -> println("Ready TEACHER $it")
                    ItemType.ROOM -> println("Ready ROOM $it")
                    ItemType.SUBJECT -> println("Ready SUBJECT $it")
                }
                selection = null
            }
        )*/
    }
    Column {
        Text("ScheduleCreatingScreen")
        Button(
            onClick = { selection = ItemType.GROUP }
        ) {
            Text("Группы")
        }
        Button(
            onClick = { selection = ItemType.TEACHER }
        ) {
            Text("Учителя")
        }
        Button(
            onClick = { selection = ItemType.SUBJECT }
        ) {
            Text("Предметы")
        }
        Button(
            onClick = { selection = ItemType.ROOM }
        ) {
            Text("Кабинеты")
        }
        Button(
            onClick = { }
        ) {
            Text("Детальная групп")
        }
    }

}

/**
 * Диалог информации об группе. Можно указать ученые предметы для указанной группе
 */
@Composable
fun DialogGroupInfo(
    group: Group,
    selectedSubjects: Map<Discipline, AcademicHour> = emptyMap(),
    availableSubjects: List<Discipline> = emptyList(),
    onClose: () -> Unit
) {

    Dialog(
        onCloseRequest = { onClose() },
        state = rememberDialogState(size = DpSize(width = 400.dp, height = 400.dp)),
        title = group.name,
        icon = null,
        resizable = false
    ) {

        val subjects: MutableMap<Discipline, Int> = remember<MutableMap<Discipline, Int>> {
            SnapshotStateMap<Discipline, Int>().apply { this.putAll(selectedSubjects) }
        }
        var target: Pair<Discipline?, AcademicHour?>? by remember { mutableStateOf(null) }

        target?.also { (targetSubject, targetHours) ->
            AddingAcademicSubject(
                availableSubjects = availableSubjects,
                currentDiscipline = targetSubject,
                currentAcademicHours = targetHours,
                onCommit = { subject, hours -> subjects[subject] = hours; target = null },
                onCancel = { target = null }
            )
        }

        Surface {
            Column(
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = group.name,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (subjects.isNotEmpty()) {
                    Text(
                        text = "Учебные предметы:"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                val state = rememberLazyListState()
                Row(
                    modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.wrapContentHeight().weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        state = state
                    ) {
                        subjects.forEach { (subject, hours) ->
                            item(subject.id) {
                                CardBox(
                                    modifier = Modifier.fillMaxWidth().height(44.dp),
                                    elevation = 4.dp,
                                    onClick = { target = subject to hours }
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart),
                                        text = "${subject.name} $hours ч."
                                    )
                                }

                            }
                        }
                    }
                    VerticalScrollbar(ScrollbarAdapter(state))
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.width(200.dp).align(Alignment.CenterHorizontally),
                    onClick = { target = null to null }
                ) {
                    if (subjects.isEmpty()) {
                        Text(
                            text = "Добавить предмет"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }

                }
            }
        }
    }
}

/**
 * Диалог для добавления предмета и указание кол-ва часов
 */
@Composable
fun AddingAcademicSubject(
    availableSubjects: List<Discipline>,
    currentDiscipline: Discipline? = null,
    currentAcademicHours: AcademicHour? = null,
    onCommit: (Discipline, AcademicHour) -> Unit,
    onCancel: () -> Unit
) {
    var selectedSubject: Discipline? by remember { mutableStateOf(currentDiscipline) }
    var hours: AcademicHour? by remember { mutableStateOf(currentAcademicHours) }
    var selection: Boolean by remember { mutableStateOf(false) }

    if (selection) {
        common.ui.dialog.DialogSelection(
            title ="Выбор предмета", // TODO: 05.06.2022 Text
            list = availableSubjects,
            getText = { it.name },
            onSelect = { selectedSubject = it; selection = false },
            onCancel = { selection = false }
        )
    }

    Dialog(
        title = "Selection",
        icon = null,
        resizable = false,
        onCloseRequest = { onCancel() },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Предмет") },
                        value = selectedSubject?.name.orEmpty(),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Image(
                                modifier = Modifier.clickable { selection = true },
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Часы") },
                        value = hours?.toString().orEmpty(),
                        onValueChange = { hours = it.toIntOrNull() }
                    )
                }
                Button(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = { onCommit(selectedSubject!!, hours!!) },
                    enabled = selectedSubject != null && hours != null
                ) {
                    Text("Ok")
                }
            }
        }
    }

}