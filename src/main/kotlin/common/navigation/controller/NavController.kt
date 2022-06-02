package common.navigation.controller

import common.navigation.destination.Destination
import common.navigation.graph.Graph
import kotlinx.coroutines.flow.StateFlow

interface NavController {

    val graph: Graph
    val currentDestination: StateFlow<Destination>
    val backStack: List<Destination>

    fun navigate(destination: Destination)

    fun add(destination: Destination)

    fun navigateBack(): Boolean
}