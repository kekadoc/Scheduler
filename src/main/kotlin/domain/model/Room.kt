package domain.model

import common.extensions.emptyString

data class Room(
    override val id: Long,
    val name: String,
    val description: String = emptyString()
) : Model