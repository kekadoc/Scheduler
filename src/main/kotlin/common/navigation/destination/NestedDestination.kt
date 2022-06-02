package common.navigation.destination

import common.navigation.graph.Graph

interface NestedDestination : Destination {
    var parent: Graph
}