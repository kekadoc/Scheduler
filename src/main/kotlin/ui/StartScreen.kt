package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import ui.database.DatabaseScreen

enum class StartScreenMenuItem {
    OPEN_SCHEDULE,
    CREATE_SCHEDULE,
    DATABASE,
    LOGOUT
}

@Composable
fun StartScreen(onLogout: () -> Unit) {
    var item: StartScreenMenuItem? by remember { mutableStateOf(null) }
    when (item) {
        StartScreenMenuItem.OPEN_SCHEDULE -> ScheduleSelectionScreen()
        StartScreenMenuItem.CREATE_SCHEDULE -> ScheduleCreatingScreen()
        StartScreenMenuItem.DATABASE -> DatabaseScreen()
        StartScreenMenuItem.LOGOUT -> onLogout()
        null -> MenuScreen { item = it }
    }
}

@Composable
fun MenuScreen(onSelect: (StartScreenMenuItem) -> Unit) {
    Column {
        Button(
            onClick = {
                onSelect(StartScreenMenuItem.OPEN_SCHEDULE)
            }
        ) {
            Text("Open schedule")
        }
        Button(
            onClick = {
                onSelect(StartScreenMenuItem.CREATE_SCHEDULE)
            }
        ) {
            Text("Create schedule")
        }
        Button(
            onClick = {
                onSelect(StartScreenMenuItem.DATABASE)
            }
        ) {
            Text("Database")
        }
        Button(
            onClick = {
                onSelect(StartScreenMenuItem.LOGOUT)
            }
        ) {
            Text("Change space")
        }
    }
}
