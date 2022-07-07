package app.ui.database

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.ApplicationViewModel
import app.ui.database.discipline.DisciplinesDatabaseScreen
import app.ui.database.group.GroupsDatabaseScreen
import app.ui.database.plan.AcademicPlanDatabaseScreen
import app.ui.database.rooms.RoomsDatabaseScreen
import app.ui.database.teachers.TeachersDatabaseScreen
import common.ui.menu.SimpleMenuItem
import common.ui.menu.SimpleMenuItemLayout
import common.view_model.viewModel

private enum class DatabaseItem : SimpleMenuItem {
    TEACHERS {
        override val id: Long = 1L
        override val text: String = "Преподаватели"
        override val image: ImageVector = Icons.Default.Person
    },
    ROOMS {
        override val id: Long = 2L
        override val text: String = "Кабинеты"
        override val image: ImageVector = Icons.Default.MeetingRoom
    },
    DISCIPLINES {
        override val id: Long = 3L
        override val text: String = "Предметы"
        override val image: ImageVector = Icons.Default.School
    },
    GROUPS {
        override val id: Long = 4L
        override val text: String = "Группы"
        override val image: ImageVector = Icons.Default.Groups
    },
    PLANS {
        override val id: Long = 5L
        override val text: String = "Планы"
        override val image: ImageVector = Icons.Default.ListAlt
    }
}

@Composable
fun DatabaseScreen() {

    val viewModel = viewModel<ApplicationViewModel>()
    val state by viewModel.container.stateFlow.collectAsState()
    var currentScreen: DatabaseItem by remember { mutableStateOf(DatabaseItem.TEACHERS) }

    Row(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 4.dp
        ) {
            Column {
                MenuHeader()
                SimpleMenuItemLayout(
                    modifier = Modifier.width(250.dp).fillMaxHeight(),
                    items = DatabaseItem.values().toList(),
                    selected = currentScreen,
                    header = {},
                    onItemSelect = { currentScreen = it },
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (currentScreen) {
                DatabaseItem.TEACHERS -> TeachersDatabaseScreen()
                DatabaseItem.ROOMS -> RoomsDatabaseScreen()
                DatabaseItem.DISCIPLINES -> DisciplinesDatabaseScreen()
                DatabaseItem.GROUPS -> GroupsDatabaseScreen()
                DatabaseItem.PLANS -> AcademicPlanDatabaseScreen()
            }
        }
    }

}

@Composable
private fun MenuHeader() {
    Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "База данных",
            style = MaterialTheme.typography.h5
        )
    }
}