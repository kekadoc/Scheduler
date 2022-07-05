@file:OptIn(ExperimentalMaterialApi::class)

package common.ui.dialog

import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberDialogState
import app.domain.model.Model
import common.ui.OutlinedTextField
import common.extensions.emptyString
import common.extensions.onEmpty

@Composable
fun <T : Model> DialogSelection(
    title: String,
    icon: Painter? = null,
    list: List<T>,
    isEnabled: (T) -> Boolean = { true },
    getText: (T) -> String,
    onSelect: (T) -> Unit,
    onCancel: () -> Unit
) {
    var query: String by remember { mutableStateOf(emptyString()) }
    AppDialog(
        state = rememberDialogState(width = 300.dp, height = 400.dp),
        title = title,
        icon = icon,
        resizable = false,
        onCloseRequest = onCancel,
        contentModifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            label = { Text("Поиск") },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            value = query,
            onValueChange = { query = it },
            singleLine = true,
            contentPaddingValues = PaddingValues(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            val state = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = state,
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                list
                    .filter { query.isEmpty() || getText(it).contains(query) }
                    .onEmpty {
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text =  "Ничего не найдено",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    .forEach { item ->
                        item(item.id) {
                            SelectionItem(
                                text = getText(item),
                                isEnabled = isEnabled(item),
                                onClick = { onSelect(item) }
                            )
                        }
                    }
            }
            VerticalScrollbar(ScrollbarAdapter(state))
        }
    }
}

@Composable
private fun SelectionItem(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        onClick = onClick,
        enabled = isEnabled,
        elevation = 4.dp
    ) {
        val color = if (isEnabled) Color.Transparent else MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled)
        Box(
            modifier = Modifier
                .heightIn(min = 44.dp)
                .background(color),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.Center),
                text = text,
                style = MaterialTheme.typography.body1,
            )
        }

    }
}