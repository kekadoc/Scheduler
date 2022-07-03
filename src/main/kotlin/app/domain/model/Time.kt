package app.domain.model

data class Time(
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0
) {

    companion object {

        //12:05
        //12:05:12
        fun parse(text: String): Time {
            val numbers = text.split(":")
            val hour = numbers[0].toInt()
            val minute = numbers[1].toInt()
            val second = numbers.getOrNull(2)?.toInt() ?: 0
            return Time(hour, minute, second)
        }
    }
}

fun Time(time: String): Time {
    return Time.parse(time)
}