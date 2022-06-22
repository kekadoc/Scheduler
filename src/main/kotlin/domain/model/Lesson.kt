package domain.model

data class Lesson(
    override val id: Long,
    val name: String,
    val description: String,
    val teacher: Teacher,
    val room: Room
) : Model {

    constructor(id: Long, discipline: Discipline, teacher: Teacher, room: Room) : this(
        id = id,
        name = discipline.name,
        description = discipline.description,
        teacher = teacher,
        room = room
    )
    constructor(id: Long, teaching: Teaching, teacher: Teacher, room: Room) : this(
        id = id,
        name = teaching.discipline.name,
        description = teaching.discipline.description,
        teacher = teacher,
        room = room
    )
}