package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import common.extensions.emptyString

@Composable
fun InitialScreen(onEnter: (String) -> Unit) {
    var spaceName by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(emptyString())) }
    Column {
        Text(text = "Please enter space name: ")
        OutlinedTextField(
            value = spaceName,
            onValueChange = { spaceName = it }
        )
        Button(
            onClick = {
                onEnter(spaceName.text)
            }
        ) {
            Text("Next")
        }
    }
}