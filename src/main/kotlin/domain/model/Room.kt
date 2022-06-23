package domain.model

import common.extensions.emptyString

data class Room(
    override val id: Long,
    val name: String,
    val description: String = emptyString()
) : Model {
    companion object {
        val Empty = Room(
            id = -1L,
            name = emptyString(),
            description = emptyString()
        )
    }
}