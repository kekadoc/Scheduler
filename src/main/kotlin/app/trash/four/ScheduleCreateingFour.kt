@file:OptIn(ExperimentalMaterialApi::class)

package app.trash.four

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mock.Mock
import app.ui.common.CardColumn
import app.ui.common.FixedDimenLazyGrid
import domain.model.DayOfWeek
import domain.model.Lesson

fun Modifier.vertical() = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    println(placeable.height)
    println(placeable.width)
    layout(placeable.height, placeable.width) {
        placeable.place(
            x = -(placeable.width / 2 - placeable.height / 2),
            y = -(placeable.height / 2 - placeable.width / 2)
        )
    }
}

@Composable
fun ScheduleCreateFourScreen() {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        AcademicSubjectsComponent()
        val groups = Mock.studentGroups(10)
        val dayOfWeeks = DayOfWeek.values().toList()
        FixedDimenLazyGrid(
            modifier = Modifier.weight(1f),
            columns = groups.size,
            rows = dayOfWeeks.size,
            cellWidth = 200.dp,
            cellHeight = 300.dp * 6,
            cellHeaderColumnHeight = 300.dp,
            cellHeaderRowWidth = 200.dp,
            buildCell = { row, column ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(6) {
                        PairLessonComponent(
                            modifier = Modifier.weight(1f),
                            lesson = Mock.LESSON to Mock.LESSON.copy(id = 1)
                        )
                    }
                }
            },
            buildColumnHeader = {
                CardItem(text = groups[it].name)
            },
            buildRowHeader = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .vertical()
                            .rotate(-90f)
                            .background(Color.Gray)
                            .padding(4.dp),
                        text = dayOfWeeks[it].name,
                        fontSize = 28.sp,
                        style = TextStyle(
                            letterSpacing = 4.sp
                        )
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        repeat(6) { index ->
                            Row(
                                modifier = Modifier.weight(1f).fillMaxWidth().background(Color.Gray),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.wrapContentSize().padding(16.dp),
                                    text = index.toString(),
                                    fontSize = 28.sp
                                )
                                Text(
                                    modifier = Modifier
                                        .vertical()
                                        .weight(1f)
                                        //.rotate(-90f)
                                        .background(Color.Cyan)
                                        .padding(16.dp),
                                    text = "8.30-\n10.00",
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            },
            buildCrossHeader = {
                CardItem(text = "CROSS")
            },
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        )
    }
}

@Composable
fun PairLessonComponent(
    modifier: Modifier,
    lesson: Pair<Lesson?, Lesson?>
) {
    CardColumn(
        modifier = modifier
    ) {
        val (first, second) = lesson
        when {
            first == null && second == null -> return@CardColumn
            first == second -> {
                LessonComponent(
                    modifier = Modifier.fillMaxSize(), first
                )
            }
            else -> {
                LessonComponent(
                    modifier = Modifier.weight(1f),
                    lesson = first
                )
                LessonComponent(
                    modifier = Modifier.weight(1f),
                    lesson = second
                )
            }
        }
    }
}

@Composable
fun LessonComponent(
    modifier: Modifier,
    lesson: Lesson?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (lesson != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Биология",
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Практическая",
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "д-р техн.наук, профессор Морозов Е.А.",
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "ауд.1",
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = 6.dp
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text
            )
        }
    }
}

@Composable
fun AcademicSubjectsComponent() {
    LazyColumn(
        modifier = Modifier.width(100.dp)
    ) {
        repeat(10) {
            item { AcademicSubjectComponent() }
        }
    }
}

@Composable
fun AcademicSubjectComponent() {
    var expanded: Boolean by remember { mutableStateOf(false) }
    CardColumn {
        Row(
            modifier = Modifier.height(56.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Name"
            )
            Image(
                modifier = Modifier.clickable {
                    expanded = !expanded
                },
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        if (expanded) {
            Column {
                Card(
                    modifier = Modifier.height(44.dp),
                    onClick = {}
                ) {
                    Text(text = "Учитель")
                }
                Card(
                    modifier = Modifier.height(44.dp),
                    onClick = {}
                ) {
                    Text(text = "Кабинет")
                }
                Text("Асу-19 - 56ч")
                Text("Эс-19 - 156ч")
                Text("ПГС-19 - 51ч")
            }
        }
    }
}