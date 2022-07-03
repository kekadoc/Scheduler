package app.ui.database.rooms

import app.domain.model.Room

data class RoomsDatabaseState(
    val rooms: List<Room> = emptyList(),
    val isLoading: Boolean = false
)