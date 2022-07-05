package app.ui.schedule.create.result

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.schedule.plan.AcademicPlan.Companion.isEmpty
import app.ui.schedule.create.ScheduleCreatingViewModel
import common.view_model.viewModel

@Composable
fun ScheduleCreatingResultScreen() {
    val viewModel: ScheduleCreatingViewModel = viewModel()
    val state by viewModel.container.stateFlow.collectAsState()

    Box {
        Button(
            onClick = { viewModel.buildSchedule() },
            enabled = !state.selectedPlan.isEmpty
        ) {
            Text("Построить расписание")
        }
    }
}