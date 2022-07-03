@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.ui.common.SimpleItemComponent
import app.ui.database.group.dialog.DialogGroup
import common.extensions.collectState
import common.view_model.viewModel
import app.domain.model.Group
import app.domain.model.isEmpty

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
        modifier = Modifier.fillMaxSize().padding(16.dp)
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
        Card(
            modifier = Modifier.height(56.dp).fillMaxWidth(),
            onClick = {
                selectedGroup = Group.Empty
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Добавить")
            }
        }
    }

}

