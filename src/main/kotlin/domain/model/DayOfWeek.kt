package domain.model

enum class DayOfWeek(
    override val id: Long,
    val text: String,
    val time: java.time.DayOfWeek
) : Model {
    MONDAY(
        id = 1,
        text = "Понедельник",
        time = java.time.DayOfWeek.MONDAY
    ),
    TUESDAY(
        id = 2,
        text = "Вторник",
        time = java.time.DayOfWeek.TUESDAY
    ),
    WEDNESDAY(
        id = 3,
        text = "Среда",
        time = java.time.DayOfWeek.WEDNESDAY
    ),
    THURSDAY(
        id = 4,
        text = "Четверг",
        time = java.time.DayOfWeek.THURSDAY
    ),
    FRIDAY(
        id = 5,
        text = "Пятница",
        time = java.time.DayOfWeek.FRIDAY
    ),
    SATURDAY(
        id = 6,
        text = "Суббота",
        time = java.time.DayOfWeek.SATURDAY
    ),
    SUNDAY(
        id = 7,
        text = "Воскресенье",
        time = java.time.DayOfWeek.SUNDAY
    )
}