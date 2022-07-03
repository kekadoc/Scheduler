package app.data.repository.room

import app.domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {

    val allRooms: Flow<Result<List<Room>>>


    fun getRoom(id: Long): Flow<Result<Room>>

    fun addRoom(name: String): Flow<Result<Room>>

    fun deleteRoom(id: Long): Flow<Result<Room>>

    fun updateRoom(room: Room): Flow<Result<Room>>

    fun clear(): Flow<Result<Unit>>
}