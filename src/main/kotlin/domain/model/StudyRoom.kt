package domain.model

import common.extensions.emptyString

data class StudyRoom(
    override val id: Long,
    val name: String,
    val description: String = emptyString()
) : Model