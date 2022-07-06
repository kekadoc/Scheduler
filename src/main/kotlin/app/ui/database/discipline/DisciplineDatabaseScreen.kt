@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.discipline

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
import app.domain.model.Discipline
import app.domain.model.Discipline.Companion.isEmpty
import common.extensions.collectState
import common.ui.ImageThemed
import common.ui.SimpleItemComponent
import common.view_model.viewModel

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
        modifier = Modifier.fillMaxSize(),
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(4.dp),
            onClick = { selectedDiscipline = Discipline.Empty },
        ) {
            ImageThemed(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }

}
