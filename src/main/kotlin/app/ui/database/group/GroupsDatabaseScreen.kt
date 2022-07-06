@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.domain.model.Group
import app.domain.model.isEmpty
import app.ui.database.group.dialog.DialogGroup
import common.extensions.collectState
import common.ui.ImageThemed
import common.ui.SimpleItemComponent
import common.view_model.viewModel

@Composable
fun GroupsDatabaseScreen() {
    val viewModel = viewModel<GroupsDatabaseViewModel>()
    val state by viewModel.collectState()
    val (groups, isLoading) = state

    var selectedGroup: Group? by remember { mutableStateOf(null) }

    selectedGroup?.also { group ->
        DialogGroup(
            group = group,
            onCloseRequest = { selectedGroup = null },
            onUpdate = { newGroup ->
                if (group.isEmpty) {
                    viewModel.create(name = newGroup.name)
                } else {
                    viewModel.update(newGroup)
                }
                selectedGroup = null
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groups.forEach { room ->
                item {
                    SimpleItemComponent(
                        title = room.name,
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = { viewModel.delete(room) },
                        onClick = { selectedGroup = room }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(4.dp),
            onClick = { selectedGroup = Group.Empty },
        ) {
            ImageThemed(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }

}

