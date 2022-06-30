package app.ui.common

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LazyColumnWithScrollbar(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    scrollbarStyle: ScrollbarStyle = LocalScrollbarStyle.current,
    scrollbarInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: LazyListScope.() -> Unit
) {
    Row(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = content
        )
        /*if (state.layoutInfo.visibleItemsInfo.size < state.layoutInfo.totalItemsCount) {
            VerticalScrollbar(ScrollbarAdapter(state), reverseLayout = reverseLayout, style = scrollbarStyle, interactionSource = scrollbarInteractionSource)
        }*/
        VerticalScrollbar(ScrollbarAdapter(state), reverseLayout = reverseLayout, style = scrollbarStyle, interactionSource = scrollbarInteractionSource)

    }

}