package domain.model

data class Teaching(
    override val id: Long,
    val discipline: Discipline,
    val type: String
) : Model