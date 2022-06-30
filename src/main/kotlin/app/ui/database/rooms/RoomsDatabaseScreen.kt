@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.rooms

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
import app.ui.database.rooms.dialog.DialogRooms
import common.extensions.collectState
import common.view_model.viewModel
import domain.model.Room
import domain.model.isEmpty

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
        Card(
            modifier = Modifier.height(56.dp).fillMaxWidth(),
            onClick = {
                selectedRoom = Room.Empty
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

