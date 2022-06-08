package domain.model

enum class DayOfWeek(override val id: Long, val time: java.time.DayOfWeek) : Model {
    MONDAY(id = 1, time = java.time.DayOfWeek.MONDAY),
    TUESDAY(id = 2, time = java.time.DayOfWeek.TUESDAY),
    WEDNESDAY(id = 3, time = java.time.DayOfWeek.WEDNESDAY),
    THURSDAY(id = 4, time = java.time.DayOfWeek.THURSDAY),
    FRIDAY(id = 5, time = java.time.DayOfWeek.FRIDAY),
    SATURDAY(id = 6, time = java.time.DayOfWeek.SATURDAY),
    SUNDAY(id = 7, time = java.time.DayOfWeek.SUNDAY),
}