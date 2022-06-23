package domain.model

data class Teaching(
    override val id: Long,
    val discipline: Discipline,
    val type: Type = Type.UNSPECIFIED
) : Model {

    enum class Type(val text: String) {
        PRACTICE("Практическая"),
        LABORATORY("Лабораторная"),
        LECTURE("Лекция"),
        UNSPECIFIED("-")
    }

    companion object {
        val Empty = Teaching(id = -1, discipline = Discipline.Empty)
    }
}