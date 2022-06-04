package app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.ui.common.ImageThemed

@Composable
fun AuthorizationScreen(onEnter: (String) -> Unit) {
    var spaceName by remember { mutableStateOf("ПНИПУ") }
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            label = {
                Text(text = "Please enter space name: ", style = MaterialTheme.typography.h6)
            },
            value = spaceName,
            onValueChange = { spaceName = it },
            singleLine = true
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier.size(56.dp),
            onClick = {
                onEnter(spaceName)
            }
        ) {
            ImageThemed(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null
            )
        }
    }
}