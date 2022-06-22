package domain.model

/**
 * Академический предмет
 */
data class Discipline(
    override val id: Long,
    val name: String,
    val description: String
) : Model
