package app.ui.schedule.create.plan

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import app.schedule.plan.AcademicPlan
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

    Box(contentAlignment = Alignment.Center) {
        if (state.selectedPlan.isEmpty) {
            Button(
                onClick = { academicPlanSelection = true }
            ) {
                Text(text = "Выбрать учебный план")
            }
        } else {
            SimpleItemComponent(
                title = state.selectedPlan.name,
                rightImage = Icons.Default.Close,
                onRightImageClick = { viewModel.selectAcademicPlan(AcademicPlan.Empty) }
            )
        }
    }
}