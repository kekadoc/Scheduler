package app.ui.schedule.create.result

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.ui.schedule.create.ScheduleCreatingViewModel
import app.ui.schedule.create.plan.AcademicPlanViewModel
import common.view_model.viewModel

@Composable
fun ScheduleCreatingResultScreen() {
    val viewModel: ScheduleCreatingViewModel = viewModel()
    val planViewModel: AcademicPlanViewModel = viewModel()
    val state by planViewModel.container.stateFlow.collectAsState()

    Box {
        Button(
            onClick = {
                viewModel.buildSchedule(
                    plan = state.plan.associateBy { it.group },
                    availableGroups = planViewModel.availableGroups
                )
            }
        ) {
            Text("Построить расписание")
        }
    }
}