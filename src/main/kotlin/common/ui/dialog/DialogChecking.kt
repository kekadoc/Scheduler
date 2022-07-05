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
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import app.domain.model.Model
import common.extensions.emptyString
import common.extensions.onEmpty

@Composable
fun <T : Model> DialogChecking(
    title: String,
    icon: Painter? = null,
    list: Map<T, Boolean>,
    getText: (T) -> String,
    isEnabled: (T) -> Boolean = { true },
    onCommit: (Map<T, Boolean>) -> Unit,
    onItemClick: (Pair<T, Boolean>) -> Unit = {}
) {
    val mutableItems = remember { SnapshotStateMap<T, Boolean>().also { it.putAll(list) } }
    var query: String by remember { mutableStateOf(emptyString()) }
    Dialog(
        state = rememberDialogState(width = 300.dp, height = 400.dp),
        title = title,
        icon = icon,
        resizable = false,
        onCloseRequest = { onCommit(mutableItems) }
    ) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize().padding(4.dp)
            ) {
                common.ui.OutlinedTextField(
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
                        mutableItems
                            .filter { (item, _) -> query.isEmpty() || getText(item).contains(query) }
                            .onEmpty {
                                item {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text =  "Ничего не найдено",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            .forEach { (item, checked) ->
                                item(item.id) {
                                    CheckableItem(
                                        text = getText(item),
                                        isEnabled = isEnabled(item),
                                        isChecked = checked,
                                        onClick = { onItemClick(item to checked) },
                                        onChecked = { mutableItems[item] = it }
                                    )
                                }
                            }
                    }
                    VerticalScrollbar(ScrollbarAdapter(state))
                }
            }
        }
    }
}


@Composable
private fun CheckableItem(
    text: String,
    isEnabled: Boolean = true,
    isChecked: Boolean = true,
    onClick: () -> Unit,
    onChecked: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.height(44.dp).fillMaxWidth(),
        enabled = isEnabled,
        onClick = onClick,
        elevation = 4.dp
    ) {
        val color = if (isEnabled) Color.Transparent else MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled)
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp).background(color),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                text = text,
                style = MaterialTheme.typography.body1
            )
            Checkbox(
                modifier = Modifier,
                checked = isChecked,
                onCheckedChange = { isChecked -> onChecked(isChecked) }
            )
        }
    }
}