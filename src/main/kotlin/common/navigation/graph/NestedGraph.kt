package common.navigation.graph

import common.navigation.destination.NestedDestination
import kotlin.properties.Delegates

open class NestedGraph : Graph(), NestedDestination {

    override var parent: Graph by Delegates.notNull()

}