package app.ui.database.rooms.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import app.ui.common.onEscape
import app.domain.model.Room

@Composable
fun DialogRooms(
    room: Room,
    onCloseRequest: () -> Unit,
    onUpdate: (Room) -> Unit
) {
    var mutatedRoom: Room by remember { mutableStateOf(room) }

    val dialogState = rememberDialogState(
        position = WindowPosition.Aligned(alignment = Alignment.Center),
        width = 400.dp,
        height = 450.dp
    )
    Dialog(
        onCloseRequest = onCloseRequest,
        state = dialogState,
        title = "Кабинет", // TODO: 30.05.2022 Text
        icon = rememberVectorPainter(Icons.Default.Edit), // TODO: 30.05.2022 Icon
        resizable = false,
        onKeyEvent = { event -> event.onEscape(onCloseRequest) }
    ) {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mutatedRoom.name,
                        label = { Text("Название") }, // TODO: 30.05.2022 Text
                        onValueChange = { text -> mutatedRoom = mutatedRoom.copy(name = text) }
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    enabled = room != mutatedRoom && mutatedRoom.name.isNotEmpty(),
                    onClick = { onUpdate(mutatedRoom) }
                ) {
                    Text(text = "Сохранить") // TODO: 30.05.2022 Text
                }
            }
        }
    }
}