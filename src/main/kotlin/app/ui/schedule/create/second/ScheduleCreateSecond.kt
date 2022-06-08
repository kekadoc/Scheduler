package app.ui.schedule.create.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import app.ui.common.CardBox
import app.ui.common.CardColumn
import app.ui.common.LazyColumnWithScrollbar
import app.ui.common.dialog.DialogChecking
import app.ui.common.dialog.DialogStyle
import app.ui.common.dialog.Long
import app.ui.schedule.create.ScheduleCreatingViewModel
import common.extensions.collectState
import common.view_model.viewModel
import domain.model.AcademicHour
import domain.model.AcademicSubject
import domain.model.StudentGroup
import domain.model.schedule.GroupSettings

@Composable
fun ScheduleCreateSecondScreen() {
    val viewModel = viewModel<ScheduleCreatingViewModel>()
    val state by viewModel.container.stateFlow.collectAsState()

    var groupsSelection: Boolean by remember { mutableStateOf(false) }
    var teachersSelection: Boolean by remember { mutableStateOf(false) }
    var academicSubjectsSelection: Boolean by remember { mutableStateOf(false) }
    var roomsSelection: Boolean by remember { mutableStateOf(false) }
    var daysOfWeekSelection: Boolean by remember { mutableStateOf(false) }
    var lessonTimesSelection: Boolean by remember { mutableStateOf(false) }

    if (groupsSelection) {
        println(state.groupSettings.values.toList())
        DialogAllGroupSettings(
            items = state.groupSettings.values.toList(),
            onCLose = { groupsSelection = false }
        )
        /*DialogChecking(
            title = "Группы",
            list = state.availableGroups,
            getText = { it.name },
            onCommit = { result ->
                viewModel.setAvailableGroups(result)
                groupsSelection = false
            },
        )*/
    }

    if (teachersSelection) {
        DialogChecking(
            title = "Преподаватели",
            list = state.availableTeachers,
            getText = { "${it.lastName} ${it.firstName} ${it.middleName}" },
            onCommit = { result ->
                viewModel.setAvailableTeachers(result)
                teachersSelection = false
            },
        )
    }

    if (academicSubjectsSelection) {
        DialogChecking(
            title = "Учебные предметы",
            list = state.availableAcademicSubjects,
            getText = { it.name },
            onCommit = { result ->
                viewModel.setAvailableAcademicSubjects(result)
                academicSubjectsSelection = false
            },
        )
    }

    if (roomsSelection) {
        DialogChecking(
            title = "Кабинеты",
            list = state.availableStudyRooms,
            getText = { it.name },
            onCommit = { result ->
                viewModel.setAvailableStudyRooms(result)
                roomsSelection = false
            },
        )
    }

    if (daysOfWeekSelection) {
        DialogChecking(
            title = "Дни недели",
            list = state.availableDays,
            getText = { it.name },
            onCommit = { result ->
                viewModel.setAvailableDays(result)
                daysOfWeekSelection = false
            },
        )
    }

    if (lessonTimesSelection) {
        DialogChecking(
            title = "Время",
            list = state.availableLessonTimes,
            getText = { it.range.toString() },
            onCommit = { result ->
                viewModel.setAvailableLessonTimes(result)
                lessonTimesSelection = false
            },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GroupCounterComponent(
                modifier = Modifier.weight(1f),
                count = state.availableGroups.filter { it.value }.count(),
                onAction = { groupsSelection = true }
            )
            TeacherCounterComponent(
                modifier = Modifier.weight(1f),
                count = state.availableTeachers.filter { it.value }.count(),
                onAction = { teachersSelection = true }
            )
            SubjectCounterComponent(
                modifier = Modifier.weight(1f),
                count = state.availableAcademicSubjects.filter { it.value }.count(),
                onAction = { academicSubjectsSelection = true }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RoomCounterComponent(
                modifier = Modifier.weight(1f),
                count = state.availableStudyRooms.filter { it.value }.count(),
                onAction = { roomsSelection = true }
            )
            DayOfWeekCounterComponent(
                modifier = Modifier.weight(1f),
                count = state.availableDays.filter { it.value }.count(),
                onAction = { daysOfWeekSelection = true }
            )
            TimeSpaceCounterComponent(
                modifier = Modifier.weight(1f),
                count = state.availableLessonTimes.filter { it.value }.count(),
                onAction = { lessonTimesSelection = true }
            )
        }
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {}
        ) {
            Text(
                text = "Дальше"
            )
        }
    }
}

@Composable
private fun GroupCounterComponent(
    count: Int,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    CounterComponent(
        modifier = modifier,
        text = "$count учебных групп",
        actionText = "Настроить",
        onAction = onAction
    )
}

@Composable
private fun TeacherCounterComponent(
    count: Int,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    CounterComponent(
        modifier = modifier,
        text = "$count преподавателей",
        actionText = "Настроить",
        onAction = onAction
    )
}

@Composable
private fun RoomCounterComponent(
    count: Int,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    CounterComponent(
        modifier = modifier,
        text = "$count учебных помещений",
        actionText = "Настроить",
        onAction = onAction
    )
}

@Composable
private fun SubjectCounterComponent(
    count: Int,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    CounterComponent(
        modifier = modifier,
        text = "$count учебных предметов",
        actionText = "Настроить",
        onAction = onAction
    )
}


@Composable
private fun DayOfWeekCounterComponent(
    count: Int,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    CounterComponent(
        modifier = modifier,
        text = "$count учебных дней",
        actionText = "Настроить",
        onAction = onAction
    )
}

@Composable
private fun TimeSpaceCounterComponent(
    count: Int,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    CounterComponent(
        modifier = modifier,
        text = "$count времен занятий",
        actionText = "Настроить",
        onAction = onAction
    )
}

@Composable
private fun CounterComponent(
    modifier: Modifier = Modifier,
    text: String,
    actionText: String,
    onAction: () -> Unit
) {
    CardBox(
        modifier = modifier,
        elevation = 2.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 8.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = text,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onAction
            ) {
                Text(
                    text = actionText
                )
            }
        }
    }
}

@Composable
fun DialogAllGroupSettings(
    items: List<GroupSettings>,
    onCLose: () -> Unit
) {
    Dialog(
        title = "",
        icon = null,
        state = DialogStyle.Long.states.medium,
        onCloseRequest = onCLose
    ) {
        var targetGroupSetting: GroupSettings? by remember { mutableStateOf(null) }

        targetGroupSetting?.also {
            DialogGroupInfo(
                group = it.group,
                onClose = { targetGroupSetting = null }
            )
        }

        Surface {
            LazyColumnWithScrollbar(
                modifier = Modifier.padding(8.dp),
                contentPadding = PaddingValues(end = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.forEach { groupSettings ->
                    item(groupSettings.group.id) {
                        GroupSettingsComponent(
                            groupSettings = groupSettings,
                            onClick = { targetGroupSetting = groupSettings }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GroupSettingsComponent(
    groupSettings: GroupSettings,
    onClick: () -> Unit
) {
    CardColumn(
        modifier = Modifier,
        columnModifier = Modifier.padding(8.dp),
        elevation = 4.dp,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        onClick = onClick
    ) {
        Text(
            text = groupSettings.group.name
        )
        Text(
            text = "${groupSettings.lessons.size} учебных предметов"
        )
        Text(
            text = "${groupSettings.lessons.values.sum()} суммарно часов"
        )
    }
}


/**
 * Диалог информации об группе. Можно указать ученые предметы для указанной группе
 */
@Composable
fun DialogGroupInfo(
    group: StudentGroup,
    onClose: () -> Unit
) {
    val viewModel: ScheduleCreatingViewModel = viewModel()
    val state by viewModel.collectState()
    val groupSettings: GroupSettings? = state.groupSettings[group]
    val lessons = groupSettings?.lessons.orEmpty()
    Dialog(
        state = rememberDialogState(size = DpSize(width = 400.dp, height = 400.dp)),
        title = group.name,
        icon = null,
        resizable = false,
        onCloseRequest = onClose,
    ) {

        var target: Pair<AcademicSubject?, AcademicHour?>? by remember { mutableStateOf(null) }

        target?.also { (targetSubject, targetHours) ->
            AddingAcademicSubject(
                availableSubjects = state.availableAcademicSubjects.filter { it.value }.map { it.key },
                currentAcademicSubject = targetSubject,
                currentAcademicHours = targetHours,
                onCommit = { subject, hours -> viewModel.setGroupLesson(group, subject, hours); target = null },
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

                if (lessons.isNotEmpty()) {
                    Text(
                        text = "Учебные предметы:"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val listState = rememberLazyListState()
                    Row(
                        modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.wrapContentHeight().weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            state = listState
                        ) {
                            lessons.forEach { (subject, hours) ->
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
                        VerticalScrollbar(ScrollbarAdapter(listState))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    modifier = Modifier.width(200.dp).align(Alignment.CenterHorizontally),
                    onClick = { target = null to null }
                ) {
                    if (lessons.isEmpty()) {
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
    availableSubjects: List<AcademicSubject>,
    currentAcademicSubject: AcademicSubject? = null,
    currentAcademicHours: AcademicHour? = null,
    onCommit: (AcademicSubject, AcademicHour) -> Unit,
    onCancel: () -> Unit
) {
    var selectedSubject: AcademicSubject? by remember { mutableStateOf(currentAcademicSubject) }
    var hours: AcademicHour? by remember { mutableStateOf(currentAcademicHours) }
    var selection: Boolean by remember { mutableStateOf(false) }

    if (selection) {
        app.ui.common.dialog.DialogSelection(
            title = "Выбор предмета", // TODO: 05.06.2022 Text
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