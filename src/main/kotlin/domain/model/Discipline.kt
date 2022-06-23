package domain.model

import common.extensions.emptyString

/**
 * Академический предмет
 */
data class Discipline(
    override val id: Long,
    val name: String,
    val description: String = emptyString(),
    val targetTeacher: Teacher? = null,
    val targetRoom: Room? = null
) : Model {

    companion object {
        val Empty = Discipline(id = -1L, name = emptyString())
    }
}
