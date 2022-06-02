package common.navigation.controller

import common.navigation.destination.Destination
import common.navigation.graph.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultNavController(override val graph: Graph) : NavController {

    private val _currentDestination = MutableStateFlow<Destination>(graph.homeDestination)
    override val currentDestination = _currentDestination.asStateFlow()

    private val _backStack = mutableListOf<Destination>()
    override val backStack: List<Destination>
        get() = _backStack


    init {
        val homeDestination = graph.homeDestination

        navigate(graph.homeDestination)
    }

    override fun navigate(destination: Destination) {
        println("navigate destination=$destination")
        currentDestination.value.apply { detach() }
        _backStack.add(destination)
        _currentDestination.value = destination
        destination.attach()
        println("navigate backStack=$backStack")
    }

    override fun navigateBack(): Boolean {
        println("navigateBack backStack=$backStack")
        if (backStack.isEmpty()) return false
        val currentDestination = _backStack.removeLastOrNull() ?: return false
        println("navigateBack currentDestination=$currentDestination")
        currentDestination.detach()
        val newDestination = _backStack.lastOrNull() ?: return false
        println("navigateBack newDestination=$newDestination")
        _currentDestination.value = newDestination
        newDestination.attach()
        currentDestination.destroy()
        return true
    }

}