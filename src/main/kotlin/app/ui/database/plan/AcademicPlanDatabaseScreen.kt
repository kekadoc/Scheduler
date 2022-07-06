@file:OptIn(ExperimentalMaterialApi::class)

package app.ui.database.plan

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
import app.schedule.plan.AcademicPlan
import app.schedule.plan.AcademicPlan.Companion.isEmpty
import common.extensions.collectState
import common.ui.ImageThemed
import common.ui.SimpleItemComponent
import common.view_model.viewModel

@Composable
fun AcademicPlanDatabaseScreen() {
    val viewModel = viewModel<AcademicPlanViewModel>()
    val state by viewModel.collectState()
    val (availableGroups, availableRooms, availableTeachers, availableDisciplines, plans) = state
    var selectedPlan: AcademicPlan? by remember { mutableStateOf(null) }

    selectedPlan?.let { selected ->
        DialogAcademicPlanEditing(
            plan = selected,
            availableGroups = availableGroups,
            availableDisciplines = availableDisciplines,
            availableRooms = availableRooms,
            availableTeachers = availableTeachers,
            onCommit = { commitedPlan ->
                if (selected.isEmpty) viewModel.createAcademicPlan(commitedPlan)
                else viewModel.updateAcademicPlan(commitedPlan)
                selectedPlan = null
            },
            onCancel = { selectedPlan = null }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            plans.forEach { academicPlan ->
                item {
                    SimpleItemComponent(
                        title = academicPlan.name,
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = {
                            viewModel.deleteAcademicPlan(academicPlan)
                        },
                        onClick = {
                            selectedPlan = academicPlan
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(4.dp),
            onClick = { selectedPlan = AcademicPlan.Empty },
        ) {
            ImageThemed(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}