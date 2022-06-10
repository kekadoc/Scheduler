package domain.model

data class Lesson(
    override val id: Long,
    override val name: String,
    override val description: String,
    val teacher: Teacher,
    val groups: List<StudentGroup>,
) : Occupation