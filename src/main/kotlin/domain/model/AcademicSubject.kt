package domain.model

/**
 * Академический предмет
 */
data class AcademicSubject(
    override val id: Long,
    val name: String,
    val description: String
) : Model
