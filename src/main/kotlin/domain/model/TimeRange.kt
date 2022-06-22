package domain.model

data class TimeRange(
    val from: Time,
    val to: Time
) {
    companion object {

        fun parse(times: Pair<String, String>): TimeRange {
            return TimeRange(from = Time(times.first), to = Time(times.first))
        }

        fun from(times: Pair<Time, Time>): TimeRange {
            return TimeRange(from = times.first, to = times.second)
        }

        infix fun Time.to(time: Time): TimeRange {
            return TimeRange(from = this, to = time)
        }

        infix fun String.to(time: String): TimeRange {
            return TimeRange(from = Time(this), to = Time(time))
        }
    }
}