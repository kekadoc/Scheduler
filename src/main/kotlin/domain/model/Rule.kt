package domain.model

data class Rule(
    override val id: Long,
    val name: String,
    val description: String,
    val enabled: Boolean
) : Model