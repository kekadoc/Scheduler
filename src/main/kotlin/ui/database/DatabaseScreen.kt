package ui.database

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import ui.database.teachers.TeachersDatabaseScreen

enum class DatabaseItem {
    TEACHERS,
    ROOMS,
    ACADEMIC_SUBJECTS
}

@Composable
fun DatabaseScreen() {
    var item: DatabaseItem? by remember { mutableStateOf(null) }
    when (item) {
        DatabaseItem.TEACHERS -> TeachersDatabaseScreen()
        DatabaseItem.ROOMS -> TODO()
        DatabaseItem.ACADEMIC_SUBJECTS -> TODO()
        null -> MenuSelectionScreen { item = it }
    }
}

@Composable
private fun MenuSelectionScreen(onSelect: (DatabaseItem) -> Unit) {
    Column {
        Text("DatabaseScreen")
        Button(
            onClick = {
                onSelect(DatabaseItem.TEACHERS)
            }
        ) {
            Text("Teachers")
        }
    }
}