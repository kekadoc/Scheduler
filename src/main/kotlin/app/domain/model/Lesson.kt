package app.domain.model

data class Lesson(
    override val id: Long,
    val name: String,
    val type: String,
    val teacher: Teacher,
    val room: Room
) : Model {

    constructor(id: Long, teaching: Teaching, teacher: Teacher, room: Room) : this(
        id = id,
        name = teaching.discipline.name,
        type = teaching.type.text,
        teacher = teacher,
        room = room
    )
}