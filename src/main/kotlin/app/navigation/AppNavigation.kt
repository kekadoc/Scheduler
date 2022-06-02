package app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import common.extensions.emptyString
import common.navigation.controller.findNavController
import common.navigation.graph.Graph
import common.navigation.graph.destination
import common.navigation.graph.homeDestination
import ui.database.teachers.TeacherScreen

object AppNavigation : Graph() {

    val Initial = homeDestination {
        val navController = findNavController()
        var spaceName by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(emptyString())) }
        Column {
            Text(text = "Please enter space name: ")
            OutlinedTextField(
                value = spaceName,
                onValueChange = { spaceName = it }
            )
            Button(
                onClick = {
                    navController.navigate(this@AppNavigation.StartScreen)
                }
            ) {
                Text("Next")
            }
        }
    }

    val StartScreen = destination {
        val navController = findNavController()
        Column {
            Button(
                onClick = {
                    navController.navigate(ScheduleSelectionScreen)
                }
            ) {
                Text("Open schedule")
            }
            Button(
                onClick = {
                    navController.navigate(ScheduleCreatingScreen)
                }
            ) {
                Text("Create schedule")
            }
            Button(
                onClick = {
                    navController.navigate(DatabaseScreen)
                }
            ) {
                Text("Database")
            }
            Button(
                onClick = {
                    //navController.navigate(this@AppNavigation.Initial)
                }
            ) {
                Text("Change space")
            }
        }
    }

    val ScheduleSelectionScreen = destination {
        Text("ScheduleSelectionScreen")
    }
    val ScheduleCreatingScreen = destination {
        Text("ScheduleCreatingScreen")
    }
    val DatabaseScreen = destination {
        val navController = findNavController()
        Text("DatabaseScreen")
        Button(
            onClick = {
                navController.navigate(TeachersDatabaseScreen)
            }
        ) {
            Text("Teachers")
        }
    }

    val TeachersDatabaseScreen = homeDestination(TeacherScreen)

}