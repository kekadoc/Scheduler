package app.ui.database.rooms

import common.extensions.container
import common.logger.Logger
import common.view_model.ViewModel
import app.data.repository.room.RoomsRepository
import app.domain.model.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class RoomsDatabaseViewModel(
    private val roomsRepository: RoomsRepository
) : ViewModel(), ContainerHost<RoomsDatabaseState, Unit> {

    override val container = container<RoomsDatabaseState, Unit>(RoomsDatabaseState())

    init {
        viewModelScope.launch {
            combine(
                roomsRepository.allRooms.map { it.getOrNull() }.filterNotNull(),
                flowOf(Unit)
            ) { rooms, _ -> RoomsDatabaseState(rooms) }
                .flowOn(Dispatchers.IO)
                .onEach { newState -> intent { reduce { newState } } }
                .launchIn(viewModelScope)
        }
    }

    fun create(name: String) = intent {
        roomsRepository.addRoom(name = name)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { teacher ->
                Logger.log("create successfully $teacher")
            }
            .onFailure { error ->
                Logger.log("create failed $error")
            }

    }

    fun update(room: Room) = intent {
        roomsRepository.updateRoom(room)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { newRoom ->
                Logger.log("update successfully $newRoom")
            }
            .onFailure { error ->
                Logger.log("update failed", error)
            }
    }

    fun delete(room: Room) = intent {
        roomsRepository.deleteRoom(room.id)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { deletedRoom ->
                Logger.log("delete successfully $deletedRoom")
            }
            .onFailure { error ->
                Logger.log("delete failed", error)
            }
    }

}