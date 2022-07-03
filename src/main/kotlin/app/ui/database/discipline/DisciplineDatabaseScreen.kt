@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.discipline

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
import common.extensions.collectState
import common.view_model.viewModel
import app.domain.model.Discipline
import app.domain.model.Discipline.Companion.isEmpty

@Composable
fun DisciplinesDatabaseScreen() {
    val viewModel = viewModel<DisciplinesViewModel>()
    val state by viewModel.collectState()
    val (disciplines, _, _) = state

    var selectedDiscipline: Discipline? by remember { mutableStateOf(null) }

    selectedDiscipline?.also { discipline ->
        DialogDiscipline(
            discipline = discipline,
            onCloseRequest = { selectedDiscipline = null },
            onUpdate = { newDiscipline ->
                if (discipline.isEmpty) {
                    viewModel.create(
                        name = newDiscipline.name,
                        teachers = newDiscipline.teachers,
                        rooms = newDiscipline.rooms
                    )
                } else {
                    viewModel.update(newDiscipline)
                }
                selectedDiscipline = null
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            disciplines.forEach { discipline ->
                item {
                    SimpleItemComponent(
                        title = discipline.name,
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = { viewModel.delete(discipline) },
                        onClick = { selectedDiscipline = discipline }
                    )
                }
            }
        }
        Card(
            modifier = Modifier.height(56.dp).fillMaxWidth(),
            onClick = {
                selectedDiscipline = Discipline.Empty
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
