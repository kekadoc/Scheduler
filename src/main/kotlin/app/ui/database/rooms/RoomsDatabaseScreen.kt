@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.rooms

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
import app.domain.model.Room
import app.domain.model.isEmpty
import app.ui.database.rooms.dialog.DialogRooms
import common.extensions.collectState
import common.ui.ImageThemed
import common.ui.SimpleItemComponent
import common.view_model.viewModel

@Composable
fun RoomsDatabaseScreen() {
    val viewModel = viewModel<RoomsDatabaseViewModel>()
    val state by viewModel.collectState()
    val (rooms, isLoading) = state

    var selectedRoom: Room? by remember { mutableStateOf(null) }

    selectedRoom?.also { room ->
        DialogRooms(
            room = room,
            onCloseRequest = { selectedRoom = null },
            onUpdate = { newRoom ->
                if (room.isEmpty) {
                    viewModel.create(name = newRoom.name)
                } else {
                    viewModel.update(newRoom)
                }
                selectedRoom = null
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
            rooms.forEach { room ->
                item {
                    SimpleItemComponent(
                        title = room.name,
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = { viewModel.delete(room) },
                        onClick = { selectedRoom = room }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(4.dp),
            onClick = { selectedRoom = Room.Empty },
        ) {
            ImageThemed(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }

}

