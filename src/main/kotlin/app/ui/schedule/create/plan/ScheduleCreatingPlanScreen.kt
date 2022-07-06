package app.ui.schedule.create.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schedule.plan.AcademicPlan.Companion.isEmpty
import app.ui.schedule.create.ScheduleCreatingViewModel
import common.extensions.collectState
import common.ui.SimpleItemComponent
import common.ui.dialog.DialogSelection
import common.view_model.viewModel

@Composable
fun ScheduleCreatingPlanScreen() {
    val viewModel = viewModel<ScheduleCreatingViewModel>()
    val state by viewModel.collectState()

    var academicPlanSelection: Boolean by remember { mutableStateOf(false) }

    if (academicPlanSelection) {
        DialogSelection(
            title = "Выбор академического плана",
            list = state.plans,
            getText = { it.name },
            onSelect = { viewModel.selectAcademicPlan(it); academicPlanSelection = false },
            onCancel = { academicPlanSelection = false }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (!state.selectedPlan.isEmpty) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = state.selectedPlan.name,
                    style = MaterialTheme.typography.h5
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    state.selectedPlan.plans.forEach { groupPlan ->
                        item {
                            SimpleItemComponent(
                                labelStyle = MaterialTheme.typography.h6,
                                label = groupPlan.group.name,
                                titleStyle = MaterialTheme.typography.body2,
                                title = "${groupPlan.items.size} предмета",
                                captionStyle = MaterialTheme.typography.body2,
                                caption = "${groupPlan.items.sumOf { it.works.values.sum() }} часов",
                            )
                        }
                    }

                }
            }
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { academicPlanSelection = true }
        ) {
            Text(text = "Выбрать учебный план")
        }
    }
}