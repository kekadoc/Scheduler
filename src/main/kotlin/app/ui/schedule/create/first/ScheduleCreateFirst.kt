package app.ui.schedule.create.first

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.ui.common.CardBox
import app.ui.common.dialog.DialogChecking
import app.ui.schedule.create.ScheduleCreatingViewModel
import common.view_model.viewModel

@Composable
fun ScheduleCreateFirstScreen() {

    val viewModel = viewModel<ScheduleCreatingViewModel>()
    val state by viewModel.container.stateFlow.collectAsState()
    var groupsSelection: Boolean by remember { mutableStateOf(false) }
    var teachersSelection: Boolean by remember { mutableStateOf(false) }
    var academicSubjectsSelection: Boolean by remember { mutableStateOf(false) }
    var roomsSelection: Boolean by remember { mutableStateOf(false) }
    var daysOfWeekSelection: Boolean by remember { mutableStateOf(false) }
    var lessonTimesSelection: Boolean by remember { mutableStateOf(false) }

    if (groupsSelection) {
        DialogChecking(
            title = "Группы",
            list = state.availableGroups,
            getText = { it.name },
            onCommit = { result ->
                viewModel.setAvailableGroups(result)
                groupsSelection = false
            },
        )
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
            list = state.availableRooms,
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
                count = state.availableRooms.filter { it.value }.count(),
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
        actionText = "Изменить",
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
        actionText = "Изменить",
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
        actionText = "Изменить",
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
        actionText = "Изменить",
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
        actionText = "Изменить",
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
        actionText = "Изменить",
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