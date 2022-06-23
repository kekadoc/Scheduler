package domain.model

import common.extensions.emptyString

data class Teacher(
    override val id: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val speciality: String
) : Model {

    companion object {
        val Empty = Teacher(
            id = -1L,
            lastName = emptyString(),
            firstName = emptyString(),
            middleName = emptyString(),
            speciality = emptyString()
        )
    }
}

val Teacher.fullName: String
    get() = "$lastName $firstName $middleName"