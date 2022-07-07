package app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.ui.ImageThemed

@Composable
// TODO: 06.07.2022 Auth Loading
fun AuthorizationScreen(onEnter: (String) -> Unit, isAuthLoading: Boolean) {
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
            contentPadding = PaddingValues(12.dp),
            onClick = {
                onEnter(spaceName)
            },
        ) {
            if (isAuthLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(44.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            } else {
                ImageThemed(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null
                )
            }
        }
    }
}