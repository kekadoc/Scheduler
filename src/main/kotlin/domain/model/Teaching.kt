package domain.model

data class Teaching(
    override val id: Long,
    val discipline: Discipline,
    val teacher: Teacher,
    val room: Room,
    val type: WorkType = WorkType.UNSPECIFIED
) : Model {

    companion object {
        val Empty = Teaching(
            id = -1,
            discipline = Discipline.Empty,
            teacher = Teacher.Empty,
            room = Room.Empty
        )
    }
}