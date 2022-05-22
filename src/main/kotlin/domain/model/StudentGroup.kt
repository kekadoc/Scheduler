package domain.model

data class StudentGroup(
    override val id: Long,
    val name: String
) : Model