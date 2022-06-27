package data.repository.room

import domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    val allRooms: Flow<Result<List<Room>>>


    fun getRoom(id: Long): Flow<Result<Room>>

    fun addRoom(name: String): Flow<Result<Room>>

    fun deleteRoom(id: Long): Flow<Result<Room>>

    fun updateRoom(room: Room): Flow<Result<Room>>
}