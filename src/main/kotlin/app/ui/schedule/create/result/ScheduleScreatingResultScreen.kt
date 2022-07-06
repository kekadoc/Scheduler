package app.ui.schedule.create.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.schedule.plan.AcademicPlan.Companion.isEmpty
import app.ui.schedule.create.ScheduleCreatingViewModel
import common.view_model.viewModel

@Composable
fun ScheduleCreatingResultScreen() {
    val viewModel: ScheduleCreatingViewModel = viewModel()
    val state by viewModel.container.stateFlow.collectAsState()

    Column {
        Button(
            modifier = Modifier.fillMaxWidth(0.5f).sizeIn(minHeight = 40.dp),
            onClick = { viewModel.buildSchedule() },
            enabled = !state.selectedPlan.isEmpty && !state.isScheduleCreating
        ) {
            if (state.isScheduleCreating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                    color = MaterialTheme.colors.onPrimary
                )
            } else {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Построить расписание",
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
        if (state.file != null) {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.5f).sizeIn(minHeight = 40.dp).align(Alignment.CenterHorizontally),
                onClick = { viewModel.openFile() }
            ) {
                Text(
                    text = "Открыть файл",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.5f).sizeIn(minHeight = 40.dp).align(Alignment.CenterHorizontally),
                onClick = { viewModel.openFileFolder() }
            ) {
                Text(
                    text = "Открыть расположение файла",
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}