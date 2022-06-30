@file:OptIn(ExperimentalComposeUiApi::class)

package app.ui.common

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*

fun KeyEvent.onEscape(block: () -> Unit): Boolean {
    return if (type == KeyEventType.KeyUp && key == Key.Escape) {
        block()
        true
    } else {
        false
    }
}