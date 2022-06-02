package common.navigation.graph

import androidx.compose.runtime.Composable
import common.navigation.destination.Destination
import common.navigation.destination.NestedDestination
import kotlin.properties.Delegates

open class Graph : Destination {

    var homeDestination: NestedDestination by Delegates.notNull()

    private var _destinations = mutableListOf<NestedDestination>()
    val destinations: List<NestedDestination>
        get() = _destinations

    fun addDestination(destination: NestedDestination) {
        _destinations.add(destination.apply { parent = this@Graph })
    }

    @Composable
    override fun draw() {
        homeDestination.draw()
    }

    override fun attach() {
        homeDestination.attach()
    }

    override fun detach() {
        homeDestination.detach()
    }

    override fun destroy() {
        homeDestination.destroy()
    }

}