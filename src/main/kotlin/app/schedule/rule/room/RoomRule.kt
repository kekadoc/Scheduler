package app.schedule.rule.room

import app.domain.model.Room

class RoomRule {

    private val options = mutableMapOf<Room, Option>()


    fun get(room: Room): Option {
        return options.getOrPut(room) { Option() }
    }

    data class Option(
        var order: Int = 0
    )

}