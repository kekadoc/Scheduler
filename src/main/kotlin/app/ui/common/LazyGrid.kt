package app.ui.common

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

typealias RowIndex = Int
typealias ColumnIndex = Int

@Composable
fun LazyGrid(
    modifier: Modifier,
    columns: Int,
    rows: Int,
    buildCell: @Composable (RowIndex, ColumnIndex) -> Unit,
    buildColumnHeader: @Composable ((ColumnIndex) -> Unit)? = null,
    buildRowHeader: @Composable ((RowIndex) -> Unit)? = null,
    buildCrossHeader: @Composable (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical,
    horizontalArrangement: Arrangement.Horizontal,
) {
    val verticalScroll = rememberLazyListState()
    val horizontalScroll = rememberLazyListState()

    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                verticalArrangement = verticalArrangement,
                modifier = Modifier.weight(1f),
                state = verticalScroll
            ) {
                if (buildColumnHeader != null) {
                    item("row_headers") {
                        LazyRow(
                            horizontalArrangement = horizontalArrangement,
                            state = horizontalScroll
                        ) {
                            if (buildRowHeader != null && buildCrossHeader != null) {
                                item("cross") { buildCrossHeader() }
                            }
                            repeat(columns) { columnIndex ->
                                item(columnIndex) { buildColumnHeader(columnIndex) }
                            }
                        }
                    }
                }
                repeat(rows) { rowIndex ->
                    item(rowIndex) {
                        LazyRow(
                            horizontalArrangement = horizontalArrangement,
                            state = LazyListState(
                                firstVisibleItemIndex = horizontalScroll.firstVisibleItemIndex,
                                firstVisibleItemScrollOffset = horizontalScroll.firstVisibleItemScrollOffset
                            )
                        ) {
                            if (buildRowHeader != null) {
                                item("row_header_$rowIndex") { buildRowHeader(rowIndex) }
                            }
                            repeat(columns) { columnIndex ->
                                item("cell[$rowIndex,$columnIndex]") {
                                    buildCell(rowIndex, columnIndex)
                                }
                            }
                        }
                    }
                }
            }
            HorizontalScrollbar(ScrollbarAdapter(horizontalScroll))
        }
        VerticalScrollbar(ScrollbarAdapter(verticalScroll))
    }

}