package common.navigation.controller

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.navigation.graph.Graph

val LocalNavController = staticCompositionLocalOf<NavController> {
    throw NotImplementedError()
}

@Composable
fun NavControllerScope(modifier: Modifier = Modifier, graph: Graph) {
    val navController = remember { DefaultNavController(graph) }
    val dest by navController.currentDestination.collectAsState()
    CompositionLocalProvider(LocalNavController provides navController) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            dest.draw()
        }
    }
}

@Composable
fun findNavController(): NavController {
    return LocalNavController.current
}
