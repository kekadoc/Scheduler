package app.ui.database.group

import app.domain.model.Group

data class GroupsDatabaseState(
    val groups: List<Group> = emptyList(),
    val isLoading: Boolean = false
)