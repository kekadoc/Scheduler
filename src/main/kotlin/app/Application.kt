package app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.ui.Modifier
import app.navigation.AppNavigation
import common.navigation.controller.NavControllerScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Application : KoinComponent {

    private val viewModel by inject<ApplicationViewModel>()

    @Composable
    fun onCreate() {
        MaterialTheme {
            NavControllerScope(
                modifier = Modifier.fillMaxSize(),
                graph = AppNavigation
            )
        }
    }
}