package common.navigation.destination

import androidx.compose.runtime.Composable

interface Destination {

    @Composable
    fun draw()

    fun attach()

    fun detach()

    fun destroy()
}