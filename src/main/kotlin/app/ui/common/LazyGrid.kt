package app.ui.common

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

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

@Composable
fun FixedDimenLazyGrid(
    modifier: Modifier,
    columns: Int,
    rows: Int,
    cellWidth: Dp,
    cellHeight: Dp,
    cellHeaderRowWidth: Dp = cellWidth,
    cellHeaderColumnHeight: Dp = cellHeight,
    buildCell: @Composable BoxScope.(RowIndex, ColumnIndex) -> Unit,
    buildColumnHeader: @Composable (BoxScope.(ColumnIndex) -> Unit)? = null,
    buildRowHeader: @Composable (BoxScope.(RowIndex) -> Unit)? = null,
    buildCrossHeader: @Composable (BoxScope.() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical,
    horizontalArrangement: Arrangement.Horizontal
) {
    LazyGrid(
        modifier = modifier,
        columns = columns,
        rows = rows,
        buildCell = { row, column ->
            Box(
                modifier = Modifier.size(
                    width = cellWidth,
                    height = cellHeight
                ).background(Color.Blue)
            ) {
                buildCell(this, row, column)
            }
        },
        buildColumnHeader = buildColumnHeader?.let { builder ->
            { index ->
                Box(
                    modifier = Modifier.size(
                        width = cellWidth,
                        height = cellHeaderColumnHeight
                    ).background(Color.Yellow)
                ) {
                    builder(this, index)
                }
            }
        },
        buildRowHeader = buildRowHeader?.let { builder ->
            { index ->
                Box(
                    modifier = Modifier.size(
                        width = cellHeaderRowWidth,
                        height = cellHeight
                    ).background(Color.Red)
                ) {
                    builder(this, index)
                }
            }
        },
        buildCrossHeader = {
            Box(
                modifier = Modifier.size(
                    width = cellHeaderRowWidth,
                    height = cellHeaderColumnHeight
                ).background(Color.Magenta)
            ) {
                buildCrossHeader?.invoke(this)
            }
        },
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement
    )
}