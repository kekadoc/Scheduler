package domain.model

data class Teacher(
    override val id: Long,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val speciality: String
) : Model