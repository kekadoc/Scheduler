package app.ui.schedule.create.first

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.ui.common.CardBox

@Composable
fun ScheduleCreateFirstScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GroupCounterComponent(
                modifier = Modifier.weight(1f)
            )
            TeacherCounterComponent(
                modifier = Modifier.weight(1f)
            )
            SubjectCounterComponent(
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RoomCounterComponent(
                modifier = Modifier.weight(1f)
            )
            DayOfWeekCounterComponent(
                modifier = Modifier.weight(1f)
            )
            TimeSpaceCounterComponent(
                modifier = Modifier.weight(1f)
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
    modifier: Modifier = Modifier
) {
    CounterComponent(
        modifier = modifier,
        text = "24 учебных групп",
        actionText = "Изменить",
        onAction = {}
    )
}

@Composable
private fun TeacherCounterComponent(
    modifier: Modifier = Modifier
) {
    CounterComponent(
        modifier = modifier,
        text = "9 преподавателей",
        actionText = "Изменить",
        onAction = {}
    )
}

@Composable
private fun RoomCounterComponent(
    modifier: Modifier = Modifier
) {
    CounterComponent(
        modifier = modifier,
        text = "21 учебных помещений",
        actionText = "Изменить",
        onAction = {}
    )
}

@Composable
private fun SubjectCounterComponent(
    modifier: Modifier = Modifier
) {
    CounterComponent(
        modifier = modifier,
        text = "21 учебных предметов",
        actionText = "Изменить",
        onAction = {}
    )
}


@Composable
private fun DayOfWeekCounterComponent(
    modifier: Modifier = Modifier
) {
    CounterComponent(
        modifier = modifier,
        text = "6 учебных дней",
        actionText = "Изменить",
        onAction = {}
    )
}

@Composable
private fun TimeSpaceCounterComponent(
    modifier: Modifier = Modifier
) {
    CounterComponent(
        modifier = modifier,
        text = "6 временных зон",
        actionText = "Изменить",
        onAction = {}
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