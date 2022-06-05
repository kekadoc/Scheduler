@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.model.mvi.isAuthorized
import app.ui.AuthorizationScreen
import app.ui.MainScreen
import common.extensions.viewModel
import common.view_model.LocalViewModelStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class Application : KoinComponent {

    private val viewModel by viewModel<ApplicationViewModel>()

    @Composable
    fun onCreate() {
        var darkTheme by remember { mutableStateOf(true) }
        val state by viewModel.container.stateFlow.collectAsState()
        CompositionLocalProvider(
            LocalViewModelStore provides get()
        ) {
            MaterialTheme(
                colors = appColors(isDark = darkTheme)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (!state.isAuthorized) {
                            AuthorizationScreen(onEnter = { space -> viewModel.setSpaceName(space) })
                        } else {
                            MainScreen()
                        }
                        Switch(
                            modifier = Modifier.align(alignment = Alignment.TopEnd),
                            checked = darkTheme,
                            onCheckedChange = { darkTheme = it },
                        )
                    }
                }
            }
        }

    }

}


