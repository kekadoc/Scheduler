package app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.ApplicationViewModel
import app.ui.database.DatabaseScreen
import app.ui.schedule.create.ScheduleCreatingScreen
import common.ui.ImageThemed
import common.ui.menu.SimpleMenuItem
import common.ui.menu.SimpleMenuItemLayout
import common.view_model.viewModel

private enum class MainMenuItem : SimpleMenuItem {
    SCHEDULE_CREATING {
        override val id: Long = 2L
        override val text: String = "Создать"
        override val image: ImageVector = Icons.Default.DesignServices
    },
    DATABASE {
        override val id: Long = 3L
        override val text: String = "База данных"
        override val image: ImageVector = Icons.Default.Folder
    },
}

@Composable
fun MainScreen() {
    val viewModel = viewModel<ApplicationViewModel>()
    val state by viewModel.container.stateFlow.collectAsState()
    var currentScreen: MainMenuItem by remember { mutableStateOf(MainMenuItem.SCHEDULE_CREATING) }

    Row(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 8.dp
        ) {
            Column {
                MenuHeader(
                    title = state.space.name,
                    onLogout = { viewModel.logout() }
                )
                SimpleMenuItemLayout(
                    modifier = Modifier.width(250.dp).fillMaxHeight(),
                    items = MainMenuItem.values().toList(),
                    selected = currentScreen,
                    header = {},
                    onItemSelect = { currentScreen = it },
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            currentScreen.apply {
                when(this) {
                    //MainMenuItem.SCHEDULE -> ScheduleSelectionScreen()
                    MainMenuItem.SCHEDULE_CREATING -> ScheduleCreatingScreen()
                    MainMenuItem.DATABASE -> DatabaseScreen()
                }
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