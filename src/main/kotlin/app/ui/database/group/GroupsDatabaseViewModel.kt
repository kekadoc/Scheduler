package app.ui.database.group

import common.extensions.container
import common.logger.Logger
import common.view_model.ViewModel
import data.repository.group.GroupRepository
import domain.model.Group
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class GroupsDatabaseViewModel(
    private val groupRepository: GroupRepository
) : ViewModel(), ContainerHost<GroupsDatabaseState, Unit> {

    override val container = container<GroupsDatabaseState, Unit>(GroupsDatabaseState())

    init {
        viewModelScope.launch {
            combine(
                groupRepository.allGroups.map { it.getOrNull() }.filterNotNull(),
                flowOf(Unit)
            ) { groups, _ -> GroupsDatabaseState(groups) }
                .flowOn(Dispatchers.IO)
                .onEach { newState -> intent { reduce { newState } } }
                .launchIn(viewModelScope)
        }
    }

    fun create(name: String) = intent {
        groupRepository.addGroup(name = name)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { teacher ->
                Logger.log("create successfully $teacher")
            }
            .onFailure { error ->
                Logger.log("create failed $error")
            }

    }

    fun update(group: Group) = intent {
        groupRepository.updateGroup(group)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { newRoom ->
                Logger.log("update successfully $newRoom")
            }
            .onFailure { error ->
                Logger.log("update failed", error)
            }
    }

    fun delete(group: Group) = intent {
        groupRepository.deleteGroup(group.id)
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