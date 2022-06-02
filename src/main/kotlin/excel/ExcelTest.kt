package excel

import excel.model.Schedule
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.SheetUtil
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

object ExcelTest {

/*    context(XSSFWorkbook)
    fun buildTitle(sheet: XSSFSheet, schedule: Schedule) {
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, schedule.groups.size - 1))
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
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 50.0))
                    appendLine(
                        text = "очной формы обучения",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 36.0))
                    appendLine(
                        text = "2 семестр 2021-2022 учебного года",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 36.0))
                }
                setCellValue(text)
            }


        sheet.addMergedRegion(CellRangeAddress(0, 0, schedule.groups.size, schedule.groups.size + 1))
        sheet.getOrCreateRow(0)
            .apply {
                //height = (-1).toShort()
            }
            .getOrCreateCell( schedule.groups.size)
            .apply {
                style {
                    centering()
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


        sheet.getOrCreateRow(0).heightInPoints = 200f
    }*/

    fun create() {
        val workbook = XSSFWorkbook().apply {
            val sheet = createSheet()
            /*sheet.getOrCreateRow(0).getOrCreateCell(2).apply {
                style {
                    font(style = FontStyle.BookmanOldStyle, size = 36.0)
                    centering()
                    wrapText = true
                }
                setCellValue(
                    "Hello\nMy\nFriend\n!!!"
                )
            }*/
            sheet.apply {
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
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 50.0))
                    appendLine(
                        text = "очной формы обучения",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 36.0))
                    appendLine(
                        text = "2 семестр 2021-2022 учебного года",
                        font = createFont(style = FontStyle.BookmanOldStyle, bold = true, size = 36.0))
                }
                sheet.getOrCreateRow(0).getOrCreateCell(0).apply {
                    style {
                        wrapText = true
                        centering()
                    }
                    setCellValue(text)
                    row.height = 1
                }
                println(sheet.getOrCreateRow(0).height)
                println(sheet.getOrCreateRow(0).heightInPoints)
                //addMergedRegion(0, 0, 0, 12)
                println(sheet.getOrCreateRow(0).height)
                println(sheet.getOrCreateRow(0).heightInPoints)
            }

        }
        val file = File("files/test.xlsx")
        file.parentFile.mkdirs()

        val outFile = FileOutputStream(file)
        workbook.write(outFile)
    }

}