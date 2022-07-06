package excel

import common.extensions.forEachApply
import excel.model.*
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFRichTextString
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

object CreateScheduleXLSX {

    fun create(schedule: Schedule) {
        val workbook = XSSFWorkbook().apply {
            build(schedule)
        }
        val file = File("files/app.schedule.xlsx")
        file.parentFile.mkdirs()

        val outFile = FileOutputStream(file)
        workbook.write(outFile)
    }

    
    context(XSSFWorkbook)
    private fun build(schedule: Schedule) {
        val sheet = createSheet("Schedule")

        buildTitle(sheet, schedule)

        buildGroupRow(sheet, schedule)

        val columnOfDayOfWeek: ColumnIndex = 0
        val columnOfLessonNumber: ColumnIndex = 1
        val columnOfLessonTime: ColumnIndex = 2
        val columnOfFirstLesson: ColumnIndex = 3
        val rowOfStartLesson: RowIndex = 5
        val columnOfEndLesson: RowIndex = columnOfFirstLesson + schedule.groups.size

        var rowOfStartDayOfWeek: RowIndex
        var rowIndex = rowOfStartLesson

        schedule.dayOfWeeks.forEach { dayOfWeek ->
            rowOfStartDayOfWeek = rowIndex
            schedule.iterate(dayOfWeek) { index, time, groups ->
                groups.forEachIndexed { groupIndex, (_, lessonCell) ->
                    val rowIndexFirst = rowIndex
                    val rowIndexSecond = rowIndexFirst + 1
                    val columnIndex = columnOfFirstLesson + groupIndex
                    val columnWidth = (65.0 * 255).roundToInt()

                    val place = TableRange.fixedColumn(
                        column = columnIndex,
                        rowStart = rowIndexFirst,
                        rowEnd = rowIndexSecond
                    )
                    sheet.apply {
                        buildLessonCell(place, lessonCell)
                        setColumnWidth(columnIndex, columnWidth)
                    }
                }

                sheet.apply {
                    buildLessonTime(
                        place = TableRange.fixedColumn(
                            column = columnOfLessonTime,
                            rowStart = rowIndex,
                            rowEnd = rowIndex + 1
                        ),
                        time = time
                    )
                    buildLessonIndex(
                        place = TableRange.fixedColumn(
                            column = columnOfLessonNumber,
                            rowStart = rowIndex,
                            rowEnd = rowIndex + 1
                        ),
                        index = index
                    )

                    buildLessonTime(
                        place = TableRange.fixedColumn(
                            column = columnOfFirstLesson + schedule.groups.size,
                            rowStart = rowIndex,
                            rowEnd = rowIndex + 1
                        ),
                        time = time
                    )
                    buildLessonIndex(
                        place = TableRange.fixedColumn(
                            column = columnOfFirstLesson + schedule.groups.size + 1,
                            rowStart = rowIndex,
                            rowEnd = rowIndex + 1
                        ),
                        index = index
                    )
                }

                rowIndex += 2
            }
            sheet.apply {
                val dayOfWeekPlace1 = TableRange(
                    topLeftRow = rowOfStartDayOfWeek,
                    topLeftColumn = columnOfDayOfWeek,
                    bottomRightRow = rowIndex - 1,
                    bottomRightColumn = columnOfDayOfWeek
                )
                buildDayOfWeek(dayOfWeekPlace1, dayOfWeek)

                val dayOfWeekPlace2 = TableRange(
                    topLeftRow = rowOfStartDayOfWeek,
                    topLeftColumn = columnOfEndLesson + 2,
                    bottomRightRow = rowIndex - 1,
                    bottomRightColumn = columnOfEndLesson + 2
                )
                buildDayOfWeek(dayOfWeekPlace2, dayOfWeek)
            }
        }
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildLessonIndex(place: TableRange, index: LessonIndex) {
        val columnWidth = 18 * 255
        val range = place.asCellRangeAddress()
        val cells = getOrCreateCells(range)
        cells.forEachApply {
            style {
                setFont(Fonts.font_26_bold)
                centering()
                setAllBordersStyle(BorderStyle.MEDIUM)
                wrapText = true
            }
            setCellValue(index.toString())
        }
        addMergedRegion(range)
        (range.firstColumn..range.lastColumn).forEach { column ->
            setColumnWidth(column, columnWidth)
        }
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildLessonTime(place: TableRange, time: LessonTime) {
        val columnWidth = 18 * 255
        val range = place.asCellRangeAddress()
        val cells = getOrCreateCells(range)
        cells.forEachApply {
            style {
                setFont(Fonts.font_26_bold)
                centering()
                setAllBordersStyle(BorderStyle.MEDIUM)
                wrapText = true
            }
            setCellValue(time)
        }
        addMergedRegion(range)
        (range.firstColumn..range.lastColumn).forEach { column ->
            setColumnWidth(column, columnWidth)
        }
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildLessonCell(place: TableRange, lessonCell: LessonCell) {
        val range = place.asCellRangeAddress()
        if (lessonCell.first == lessonCell.second) {
            buildLesson(place = place, lesson = lessonCell.first)
            addMergedRegion(range)
        } else {
            buildLesson(
                place = TableRange.point(row = range.firstRow, column = range.firstColumn),
                lesson = lessonCell.first
            )
            buildLesson(
                place = TableRange.point(row = range.lastRow, column = range.firstColumn),
                lesson = lessonCell.second
            )
        }
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildLesson(place: TableRange, lesson: Lesson?) {
        val range = place.asCellRangeAddress()
        val cells = getOrCreateCells(range)
        cells.forEachApply {
            style {
                wrapText = true
                centering()
                setAllBordersStyle(BorderStyle.MEDIUM)
                if (lesson == null) {
                    setCellValue(createLessonTextEmpty())
                } else {
                    setCellValue(createLessonText(lesson))
                }
            }
        }
    }

    context(XSSFWorkbook)
    private fun buildTitle(sheet: XSSFSheet, schedule: Schedule) {
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, 3 + schedule.groups.size - 2))
        sheet.getOrCreateRow(0)
            .apply {
                //height = (-1).toShort()
            }
            .getOrCreateCell(0)
            .apply {
                style {
                    wrapText = true
                    centering()
                }
                val text = XSSFRichTextString {
                    appendLine(
                        text = "ЧАЙКОВСКИЙ ФИЛИАЛ",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 26.0)
                    )
                    appendLine(
                        text = "федерального государственного автономного образовательного учреждения высшего образования",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 26.0)
                    )
                    appendLine()
                    appendLine()
                    appendLine()
                    appendLine(
                        text = "Пермский национальный исследовательский политехнический университет",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 26.0)
                    )
                    appendLine(
                        text = "РАСПИСАНИЕ ЗАНЯТИЙ",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 50.0)
                    )
                    appendLine(
                        text = "очной формы обучения",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 36.0)
                    )
                    appendLine(
                        text = "2 семестр 2021-2022 учебного года",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 36.0)
                    )
                }
                setCellValue(text)
            }


        sheet.addMergedRegion(CellRangeAddress(0, 0, 3 + schedule.groups.size - 1, 3 + schedule.groups.size - 1 + 3))
        sheet.getOrCreateRow(0)
            .apply {
                //height = (-1).toShort()
            }
            .getOrCreateCell(3 + schedule.groups.size - 1)
            .apply {
                style {
                    verticalAlignment = VerticalAlignment.CENTER
                    alignment = HorizontalAlignment.LEFT
                    wrapText = true
                }
                val text = XSSFRichTextString {
                    val font = createFont(style = FontStyle.BookmanOldStyle, size = 28.0, bold = true)
                    appendLine(
                        text = "УТВЕРЖДАЮ:",
                        font = font
                    )
                    appendLine(font = font)
                    appendLine(
                        text = "Исполняющий обязанности директора, заместитель директора по учебной работе",
                        font = font
                    )
                    appendLine(
                        text = "_______________________ Н.М. Куликов",
                        font = font
                    )
                    appendLine(
                        text = "___17________января______     2022 г.",
                        font = font
                    )
                }
                setCellValue(text)
            }


        sheet.getOrCreateRow(0).heightInPoints = 400f
    }

    context(XSSFWorkbook)
    private fun buildGroupRow(sheet: XSSFSheet, schedule: Schedule) {
        val topRow = 3
        val bottomRow = 4
        val groupStart = 3
        val groupEnd = groupStart + schedule.groups.size - 1
        val designationColumnCount = 3
        val columnDesignationStart = 0
        val columnDesignationEnd = designationColumnCount - 1
        val designationRowHeight = 26.0f

        sheet.apply {
            val placeGroup = TableRange.fixedRow(
                row = topRow,
                columnStart = columnDesignationStart,
                columnEnd = columnDesignationEnd
            )
            buildGroupsDesignation(placeGroup)
            val placeTime = TableRange.fixedRow(
                row = bottomRow,
                columnStart = columnDesignationStart,
                columnEnd = columnDesignationEnd
            )
            buildTimesDesignation(placeTime)
        }

        schedule.groups.forEachIndexed { index, group ->
            val column = groupStart + index
            val place = TableRange.fixedColumn(column = column, rowStart = topRow, rowEnd = bottomRow)
            sheet.apply { buildGroupName(place, group) }
        }

        sheet.apply {
            val placeGroup = TableRange.fixedRow(
                row = topRow,
                columnStart = groupEnd + 1,
                columnEnd = groupEnd + designationColumnCount
            )
            buildGroupsDesignation(placeGroup)
            val placeTime = TableRange.fixedRow(
                row = bottomRow,
                columnStart = groupEnd + 1,
                columnEnd = groupEnd + designationColumnCount
            )
            buildTimesDesignation(placeTime)
        }

        sheet.getOrCreateRow(topRow).heightInPoints = designationRowHeight
        sheet.getOrCreateRow(bottomRow).heightInPoints = designationRowHeight
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildGroupsDesignation(place: TableRange) {
        val range = place.asCellRangeAddress()
        val cells = getOrCreateCells(range)
        val style = createCellStyle {
            setFont(Fonts.font_20_bold)
            centering()
            setAllBordersStyle(BorderStyle.MEDIUM)
        }
        val text = "группа"

        cells.forEachApply {
            cellStyle = style
            setCellValue(text)
        }
        addMergedRegion(range)
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildTimesDesignation(place: TableRange) {
        val range = place.asCellRangeAddress()
        val cells = getOrCreateCells(range)
        val style = createCellStyle {
            setFont(Fonts.font_20_bold)
            centering()
            setAllBordersStyle(BorderStyle.MEDIUM)
        }
        val text = "время"

        cells.forEachApply {
            cellStyle = style
            setCellValue(text)
        }
        addMergedRegion(range)
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildGroupName(place: TableRange, group: Group) {
        val range = place.asCellRangeAddress()
        val cells = getOrCreateCells(range)
        val style = createCellStyle {
            setFont(Fonts.font_35_bold)
            centering()
            setAllBordersStyle(BorderStyle.MEDIUM)
        }
        val text = group.name

        cells.forEachApply {
            cellStyle = style
            setCellValue(text)
        }
        addMergedRegion(range)
    }

    context(XSSFWorkbook, XSSFSheet)
    private fun buildDayOfWeek(place: TableRange, dayOfWeek: DayOfWeek) {
        val range = place.asCellRangeAddress()
        val cells = getOrCreateCells(range)
        val style = createCellStyle {
            setFont(Fonts.font_48_bold)
            centering()
            setAllBordersStyle(BorderStyle.MEDIUM)
            rotation = 90
        }
        cells.forEachApply {
            cellStyle = style
            setCellValue(dayOfWeek)
        }
        addMergedRegion(range)
    }

    context(XSSFWorkbook)
    private fun createLessonText(lesson: Lesson): XSSFRichTextString {
        val boldFont = createFont(Fonts.font_24_bold)
        val boldItalicFont = createFont(Fonts.font_24_bold_italic)
        return XSSFRichTextString {
            appendLine()
            appendLine(lesson.name, boldFont)
            appendLine(lesson.type, boldItalicFont)
            appendLine(lesson.teacherAbout, boldFont)
            appendLine(lesson.teacherName, boldFont)
            append(lesson.room, boldFont)
            appendLine()
        }
    }

    context(XSSFWorkbook)
    private fun createLessonTextEmpty(): XSSFRichTextString {
        val boldFont = createFont(Fonts.font_24_bold)
        val boldItalicFont = createFont(Fonts.font_24_bold_italic)
        return XSSFRichTextString {
            appendLine()
            appendLine(" ", boldFont)
            appendLine(" ", boldItalicFont)
            appendLine(" ", boldFont)
            appendLine(" ", boldFont)
            append(" ", boldFont)
            appendLine()
        }
    }

}