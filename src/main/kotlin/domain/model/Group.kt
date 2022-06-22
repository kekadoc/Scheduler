package domain.model

data class Group(
    override val id: Long,
    val name: String
) : Model