package domain.model

data class Teacher(
    override val id: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val speciality: String
) : Model

val Teacher.fullName: String
    get() = "$lastName $firstName $middleName"