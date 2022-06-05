package app.ui.database

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.ApplicationViewModel
import app.ui.common.ImageThemed
import app.ui.database.teachers.TeachersDatabaseScreen
import app.ui.menu.SimpleMenuItem
import app.ui.menu.SimpleMenuItemLayout
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
        override val image: ImageVector = Icons.Default.Place
    },
    ACADEMIC_SUBJECTS {
        override val id: Long = 3L
        override val text: String = "Предметы"
        override val image: ImageVector = Icons.Default.Phone
    },
    GROUPS {
        override val id: Long = 4L
        override val text: String = "Группы"
        override val image: ImageVector = Icons.Default.Person
    }
}

@Composable
fun DatabaseScreen() {

    val viewModel = viewModel<ApplicationViewModel>()
    val state by viewModel.container.stateFlow.collectAsState()
    var currentScreen: DatabaseItem by remember { mutableStateOf(DatabaseItem.ROOMS) }

    Row(modifier = Modifier.fillMaxSize()) {
        Card {
            Column {
                MenuHeader(
                    title = "Database",
                    onLogout = {  }
                )
                SimpleMenuItemLayout(
                    modifier = Modifier.width(300.dp).fillMaxHeight(),
                    items = DatabaseItem.values().toList(),
                    selected = currentScreen,
                    header = {},
                    onItemSelect = { currentScreen = it },
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (currentScreen) {
                DatabaseItem.TEACHERS -> TeachersDatabaseScreen()
                DatabaseItem.ROOMS -> Box { Text("ROOMS") }
                DatabaseItem.ACADEMIC_SUBJECTS -> Box { Text("ACADEMIC_SUBJECTS") }
                DatabaseItem.GROUPS -> Box { Text("GROUPS") }
            }
        }
    }

}

@Composable
private fun MenuHeader(
    title: String,
    onLogout: () -> Unit
) {
    Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.size(44.dp),
            onClick = { onLogout() },
            contentPadding = PaddingValues(4.dp)
        ) {
            ImageThemed(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.h5
        )
    }
}