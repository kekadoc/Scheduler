package common.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindowScope
import androidx.compose.ui.window.rememberDialogState
import common.lifecycle.*

// TODO: 02.06.2022  
@Composable
fun LifecycleDialog(
    lifecycleOwner: MutableLifecycleOwner = rememberDialogLifecycleOwner(),
    onCloseRequest: () -> Boolean,
    state: DialogState = rememberDialogState(),
    visible: Boolean = true,
    title: String = "Untitled",
    icon: Painter? = null,
    undecorated: Boolean = false,
    transparent: Boolean = false,
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    onPreviewKeyEvent: ((KeyEvent) -> Boolean) = { false },
    onKeyEvent: ((KeyEvent) -> Boolean) = { false },
    content: @Composable DialogWindowScope.() -> Unit
) {
    lifecycleOwner.onCreate()
    if (visible) {
        lifecycleOwner.onStart()
        if (enabled && focusable) {
            lifecycleOwner.onResume()
        } else {
            lifecycleOwner.onPause()
        }
    } else {
        lifecycleOwner.onStop()
    }
    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner) {
        Dialog(
            onCloseRequest = {
                if (onCloseRequest()) {
                    lifecycleOwner.onDestroy()
                }
            },
            state = state,
            visible = visible,
            title = title,
            icon = icon,
            undecorated = undecorated,
            transparent = transparent,
            resizable = resizable,
            enabled = enabled,
            focusable = focusable,
            onPreviewKeyEvent = onPreviewKeyEvent,
            onKeyEvent = onKeyEvent,
            content = content
        )
    }
}

@Composable
fun rememberDialogLifecycleOwner(
    lifecycleOwner: MutableLifecycleOwner = DialogLifecycleOwner()
): MutableLifecycleOwner = remember { lifecycleOwner }

private class DialogLifecycleOwner : DefaultMutableLifecycleOwner()