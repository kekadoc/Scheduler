package domain.model

import common.extensions.emptyString
import domain.model.ModelProvider.Companion.EMPTY_ID

/**
 * Академический предмет
 */
data class Discipline(
    override val id: Long,
    val name: String,
    val teachers: List<Teacher>,
    val rooms: List<Room>
) : Model {

    companion object : ModelProvider<Discipline> {

        override val Empty = Discipline(
            id = EMPTY_ID,
            name = emptyString(),
            teachers = listOf(),
            rooms = listOf()
        )

        val Discipline.isEmpty: Boolean
            get() = this == Empty
    }
}
