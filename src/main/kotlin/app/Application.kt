@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package app

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.ui.AuthorizationScreen
import app.ui.MainScreen
import app.ui.appColors
import common.extensions.collectState
import common.extensions.viewModel
import common.view_model.LocalViewModelStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class Application : KoinComponent {

    private val viewModel by viewModel<ApplicationViewModel>()

    @Composable
    fun onCreate() {
        var darkTheme by remember { mutableStateOf(true) }
        val state by viewModel.collectState()

        CompositionLocalProvider(
            LocalViewModelStore provides get()
        ) {
            MaterialTheme(
                colors = appColors(isDark = darkTheme)
            ) {
                Surface {
                    if (state.isLoading) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                if (!state.isAuthorized) {
                                    AuthorizationScreen(
                                        onEnter = { space -> viewModel.authorize(space) },
                                        isAuthLoading = state.isAuthorizationProcess
                                    )
                                } else {
                                    MainScreen()
                                }
                            }
                            Card(
                                modifier = Modifier.fillMaxHeight(),
                                elevation = 6.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Switch(
                                        modifier = Modifier,
                                        checked = darkTheme,
                                        onCheckedChange = { darkTheme = it },
                                    )
                                }

                            }

                        }
                    }
                }
            }
        }

    }

}


