package domain.model

data class Space(
    override val id: Long,
    val name: String
) : Model