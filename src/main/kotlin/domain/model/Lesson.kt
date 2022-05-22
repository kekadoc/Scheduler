package domain.model

class Lesson(
    override val id: Long,
    override val description: String,
    override val name: String,
    val teacher: Teacher,
    val groups: List<StudentGroup>,
) : Occupation