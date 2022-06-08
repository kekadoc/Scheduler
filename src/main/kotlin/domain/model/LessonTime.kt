package domain.model

data class LessonTime(
    override val id: Long,
    val range: Pair<String, String>
) : Model