package domain.model

import common.extensions.emptyString

/**
 * Академический предмет
 */
data class Discipline(
    override val id: Long,
    val name: String,
    val description: String = emptyString(),
    val targetTeacher: Teacher = Teacher.Empty,
    val targetRoom: Room = Room.Empty
) : Model {

    companion object {
        val Empty = Discipline(id = -1L, name = emptyString())
    }
}
