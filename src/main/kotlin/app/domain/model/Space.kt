package app.domain.model

import common.extensions.emptyString

data class Space(
    override val id: Long,
    val name: String
) : Model {

    companion object : ModelProvider<Space> {
        override val Empty: Space = Space(
            id = ModelProvider.EMPTY_ID,
            name = emptyString()
        )

        val Space.isEmpty: Boolean
            get() = this == Empty
    }
}