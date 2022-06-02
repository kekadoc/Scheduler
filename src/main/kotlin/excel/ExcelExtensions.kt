package excel

import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellAddress
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.*


typealias RowIndex = Int
typealias ColumnIndex = Int


fun XSSFSheet.getOrCreateRow(row: Int): XSSFRow {
    return getRow(row) ?: createRow(row)
}

fun XSSFRow.getOrCreateCell(column: Int): XSSFCell {
    return getCell(column, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
}

fun XSSFCellStyle.centering() {
    alignment = HorizontalAlignment.CENTER
    verticalAlignment = VerticalAlignment.CENTER
}

context(XSSFWorkbook)
fun XSSFCell.style(block: XSSFCellStyle.() -> Unit) {
    cellStyle = createCellStyle().apply(block)
}

context(XSSFWorkbook)
fun XSSFCellStyle.font(block: XSSFFont.() -> Unit) {
    setFont(createFont().apply(block))
}

fun XSSFWorkbook.createCellStyle(block: XSSFCellStyle.() -> Unit): XSSFCellStyle {
    return createCellStyle().apply(block)
}

context(XSSFWorkbook)
fun XSSFCellStyle.font(
    style: FontStyle,
    size: Double = 12.0,
    bold: Boolean = false,
    italic: Boolean = false,
    block: XSSFFont.() -> Unit = {}) {
    val font = createFont(style, size, bold, italic)
    setFont(font)
}

fun XSSFWorkbook.createFont(
    style: FontStyle,
    size: Double = 12.0,
    bold: Boolean = false,
    italic: Boolean = false,
    block: XSSFFont.() -> Unit = {}
): XSSFFont {
    return createFont().apply {
        this.fontName = style.name
        this.bold = bold
        this.italic = italic
        this.setFontHeight(size)
        block(this)
    }
}

fun XSSFWorkbook.createFont(font: Font): XSSFFont {
    return createFont().apply {
        this.fontName = font.style.name
        this.bold = font.bold
        this.italic = font.italic
        this.setFontHeight(font.size)
    }
}

data class Font(
    val style: FontStyle,
    val bold: Boolean,
    val italic: Boolean,
    val size: Double
) {
    companion object {

        context(XSSFWorkbook)
        fun Font.toNative(): XSSFFont {
            return createFont(this)
        }
    }
}

fun XSSFRichTextString(block: XSSFRichTextString.() -> Unit): XSSFRichTextString {
    return XSSFRichTextString().apply(block)
}

fun XSSFRichTextString.appendLine(text: String? = null) {
    append("${text.orEmpty()}\n")
}

fun XSSFRichTextString.appendLine(text: String? = null, font: XSSFFont) {
    append("${text.orEmpty()}\n", font)
}

fun XSSFRichTextString.appendLine(text: String? = null, font: Font) {
    append("${text.orEmpty()}\n", )
}

fun XSSFSheet.addMergedRegion(firstRow: RowIndex, lastRow: RowIndex, firstCol: ColumnIndex, lastCol: ColumnIndex): CellRangeAddress {
    val range = CellRangeAddress(firstRow, lastRow, firstCol, lastCol)
    addMergedRegion(range)
    return range
}

fun XSSFSheet.getOrCreateCells(rangeAddress: CellRangeAddress): Set<XSSFCell> {
    return rangeAddress.map { getOrCreateCell(it) }.toSet()
}

fun XSSFSheet.getOrCreateCell(address: CellAddress): XSSFCell {
    return getOrCreateRow(address.row).getOrCreateCell(address.column)
}

fun XSSFCellStyle.setAllBordersStyle(borderStyle: BorderStyle) {
    borderLeft = borderStyle
    borderTop = borderStyle
    borderRight = borderStyle
    borderBottom = borderStyle
}

var XSSFCellStyle.allBorders: BorderStyle
    get() = throw NotImplementedError()
    set(value) {
        borderLeft = value
        borderTop = value
        borderRight = value
        borderBottom = value
    }

context(XSSFSheet)
fun CellRangeAddress.applyAll(block: XSSFCell.() -> Unit) {
    this.forEach {
        getOrCreateCell(it).apply(block)
    }
}

context(XSSFWorkbook)
fun XSSFCellStyle.setFont(font: Font) {
    setFont(createFont(font))
}

object Fonts {
    val font_20_bold = Font(style = FontStyle.BookmanOldStyle, bold = true, italic = false, size = 20.0)
    val font_24_bold = Font(style = FontStyle.BookmanOldStyle, bold = true, italic = false, size = 24.0)
    val font_24_bold_italic = Font(style = FontStyle.BookmanOldStyle, bold = true, italic = true, size = 24.0)
    val font_26_bold = Font(style = FontStyle.BookmanOldStyle, bold = true, italic = false, size = 26.0)
    val font_35_bold = Font(style = FontStyle.BookmanOldStyle, bold = true, italic = false, size = 35.0)
    val font_48_bold = Font(style = FontStyle.BookmanOldStyle, bold = true, italic = false, size = 48.0)
}


data class FontStyle(val name: String) {

    companion object {
        val BookmanOldStyle = FontStyle(name = "Bookman Old Style")
    }
}


data class TableRange(
    val topLeftRow: RowIndex,
    val topLeftColumn: ColumnIndex,
    val bottomRightRow: RowIndex,
    val bottomRightColumn: ColumnIndex
) {

    companion object {

        fun point(row: RowIndex, column: ColumnIndex): TableRange {
            return TableRange(
                topLeftRow = row,
                topLeftColumn = column,
                bottomRightRow = row,
                bottomRightColumn = column
            )
        }

        fun fixedRow(row: RowIndex, columnStart: ColumnIndex, columnEnd: ColumnIndex): TableRange {
            return TableRange(
                topLeftRow = row,
                topLeftColumn = columnStart,
                bottomRightRow = row,
                bottomRightColumn = columnEnd
            )
        }

        fun fixedColumn(column: ColumnIndex, rowStart: RowIndex, rowEnd: RowIndex): TableRange {
            return TableRange(
                topLeftRow = rowStart,
                topLeftColumn = column,
                bottomRightRow = rowEnd,
                bottomRightColumn = column
            )
        }
    }
}

fun TableRange.asCellRangeAddress(): CellRangeAddress {
    return CellRangeAddress(
        this.topLeftRow,
        this.bottomRightRow,
        this.topLeftColumn,
        this.bottomRightColumn
    )
}