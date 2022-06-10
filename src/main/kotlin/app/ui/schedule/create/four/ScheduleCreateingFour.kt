@file:OptIn(ExperimentalMaterialApi::class)

package app.ui.schedule.create.four

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mock.Mock
import app.ui.common.CardColumn
import app.ui.common.LazyGrid
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
        LazyGrid(
            modifier = Modifier.weight(1f),
            columns = groups.size,
            rows = dayOfWeeks.size,
            buildCell = { row, column ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(6) {
                        PairLessonComponent(
                            Mock.lesson to Mock.lesson.copy(id = 1)
                        )
                    }
                }
                //CardItem(text = "[$row, $column]")
            },
            buildColumnHeader = {
                CardItem(text = groups[it].name)
            },
            buildRowHeader = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().height((6 * 200).dp).background(Color.Yellow),
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
                    Column {
                        repeat(6) {
                            Row {
                                CardItem(modifier = Modifier.width(50.dp).height(200.dp), text = "TXT")
                                CardItem(modifier = Modifier.width(50.dp).height(200.dp), text = "TXT")
                            }
                        }
                    }
                    /*Text(
                        modifier = Modifier
                            .width(150.dp)
                            .background(Color.Yellow),
                        textAlign = TextAlign.Center,
                        text = "This is a big yellow box that should take up most of the space"
                    )*/
                }
               /* Row(
                    modifier = Modifier.width(100.dp).height((6 * 200).dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(300.dp).vertical().rotate(-90f).background(Color.Red),
                        text = dayOfWeeks[it].name,
                        fontSize = 30.sp
                    )
                }*/
                //CardItem(text = dayOfWeeks[it].name)
            },
            buildCrossHeader = {
                CardItem(text = "CROSS")
            },
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        )
        /*val verticalScroll = rememberLazyListState()
        val horizontalScroll = rememberLazyListState()
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f),
                state = verticalScroll
            ) {
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        state = horizontalScroll
                    ) {
                        groups.forEach { group ->
                            item {

                            }
                        }
                    }
                }
                DayOfWeek.values().forEach { dayOfWeek ->
                    item {
                        println(horizontalScroll.firstVisibleItemIndex)
                        println(horizontalScroll.firstVisibleItemScrollOffset)
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            state = LazyListState(
                                firstVisibleItemIndex = horizontalScroll.firstVisibleItemIndex,
                                firstVisibleItemScrollOffset = horizontalScroll.firstVisibleItemScrollOffset
                            )
                        ) {
                            item {
                                Card(
                                    modifier = Modifier.size(100.dp),
                                    elevation = 6.dp
                                ) {
                                    Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
                                        Text(
                                            modifier = Modifier.align(Alignment.Center),
                                            text = dayOfWeek.name
                                        )
                                    }
                                }
                            }
                            groups.forEach { group ->
                                item {
                                    Card(
                                        modifier = Modifier.size(100.dp),
                                        elevation = 6.dp
                                    ) {
                                        Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
                                            Text(
                                                modifier = Modifier.align(Alignment.Center),
                                                text = "+"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HorizontalScrollbar(ScrollbarAdapter(horizontalScroll))
        }
        VerticalScrollbar(ScrollbarAdapter(verticalScroll))*/

    }
}

@Composable
fun PairLessonComponent(lesson: Pair<Lesson?, Lesson?>) {
    CardColumn(
        modifier = Modifier.width(150.dp).height(200.dp)
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
            Text(text = "Биология", maxLines = 1)
            Text(text = "Практическая", maxLines = 1)
            Text(text = "д-р техн.наук, профессор Морозов Е.А.", maxLines = 1)
            Text(text = "ауд.1", maxLines = 1)
        }
    }
}

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Card(
        modifier = modifier.size(100.dp),
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