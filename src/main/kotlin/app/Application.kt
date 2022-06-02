package app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.InitialScreen
import ui.StartScreen

class Application : KoinComponent {

    private val viewModel by inject<ApplicationViewModel>()

    @Composable
    fun onCreate() {
        MaterialTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                var authorized by remember { mutableStateOf(false) }
                //InitialScreen {  }
                if (authorized) {
                    StartScreen { authorized = false }
                } else {
                    InitialScreen { authorized = true }
                }
            }
        }
    }
}