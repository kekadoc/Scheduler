@file:OptIn(ExperimentalMaterialApi::class)

package app.ui.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


interface SimpleMenuItem : MenuItem {
    override val id: Long
    val text: String
    val image: ImageVector
}

@Composable
private fun SimpleMenuItem(
    text: String,
    image: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        onClick = onClick,
        border = if (isSelected) BorderStroke(width = 2.dp, color = MaterialTheme.colors.secondary) else null,
        elevation = 6.dp
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(36.dp).padding(8.dp),
                imageVector = image,
                contentDescription = null,
                colorFilter = ColorFilter.tint(LocalContentColor.current.copy(alpha = LocalContentAlpha.current))
            )
            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun <T : SimpleMenuItem> SimpleMenuItemLayout(
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    header: @Composable BoxScope.() -> Unit = {},
    items: List<T>,
    selected: T,
    onItemSelect: (T) -> Unit = {}
) {
    var selectedItem: T by remember { mutableStateOf(selected) }
    MenuLayout(
        modifier = modifier,
        header = header,
        items = items,
        itemContent = { item ->
            SimpleMenuItem(
                text = item.text,
                image = item.image,
                isSelected = selectedItem.id == item.id,
                onClick = {
                    selectedItem = item
                    onItemSelect(item)
                }
            )
        },
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement
    )
}




interface MenuItem {
    val id: Long
}

@Composable
fun <T : MenuItem> MenuLayout(
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    header: @Composable BoxScope.() -> Unit = {},
    items: List<T>,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    BaseMenuLayout(
        modifier = modifier,
        header = header,
        content = {
            items.forEach { item ->
                item(item.id) {
                    itemContent(this, item)
                }
            }
        },
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement
    )
}



@Composable
fun BaseMenuLayout(
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    header: @Composable BoxScope.() -> Unit = {},
    content: LazyListScope.() -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            content = header
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            content = content
        )
    }
}