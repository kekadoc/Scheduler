package app.domain.model

import common.extensions.emptyString

@kotlinx.serialization.Serializable
data class Group(
    override val id: Long,
    val name: String
) : Model {
    companion object {
        val Empty = Group(id = -1, name = emptyString())
    }
}

val Group.isEmpty: Boolean
    get() = this == Group.Empty