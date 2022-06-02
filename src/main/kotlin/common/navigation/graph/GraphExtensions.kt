package common.navigation.graph

import androidx.compose.runtime.Composable
import common.navigation.destination.Destination
import common.navigation.destination.Fragment
import common.navigation.destination.NestedDestination

fun <D : NestedDestination> Graph.homeDestination(destination: D): D {
    addDestination(destination)
    homeDestination = destination
    return destination
}

fun Graph.homeDestination(build: @Composable Destination.() -> Unit): Destination {
    val dest = object : Fragment() {
        @Composable
        override fun draw() {
            build.invoke(this)
        }
    }
    addDestination(dest)
    homeDestination = dest
    return dest
}


fun <D : NestedDestination> Graph.destination(destination: D): D {
    addDestination(destination)
    return destination
}

fun Graph.destination(destination: @Composable NestedDestination.() -> Unit): NestedDestination {
    val dest = object : Fragment() {
        @Composable
        override fun draw() {
            destination()
        }
    }
    addDestination(dest)
    return dest
}

fun <G : NestedGraph> Graph.nestedGraph(graph: G): G {
    addDestination(graph)
    return graph
}