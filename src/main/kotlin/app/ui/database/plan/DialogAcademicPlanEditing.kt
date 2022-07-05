package app.ui.database.plan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberDialogState
import app.domain.model.Discipline
import app.domain.model.Group
import app.domain.model.Room
import app.domain.model.Teacher
import app.schedule.plan.AcademicPlan
import app.schedule.plan.AcademicPlan.Companion.isEmpty
import app.schedule.plan.GroupPlan
import app.schedule.plan.GroupPlan.Companion.isEmpty
import common.ui.SimpleItemComponent
import common.ui.dialog.AppDialog

@Composable
fun DialogAcademicPlanEditing(
    plan: AcademicPlan,
    availableGroups: List<Group>,
    availableRooms: List<Room>,
    availableDisciplines: List<Discipline>,
    availableTeachers: List<Teacher>,
    onCommit: (AcademicPlan) -> Unit,
    onCancel: () -> Unit
) {
    val isEditing = !plan.isEmpty

    var mutableAcademicPlan by remember { mutableStateOf(plan) }

    var selectedGroupPlan: GroupPlan? by remember { mutableStateOf(null) }

    selectedGroupPlan?.also { selected ->
        DialogGroupPlanEditor(
            groupPlan = selected,
            availableGroups = availableGroups.filter { !mutableAcademicPlan.plans.map { plan -> plan.group }.contains(it) },
            availableTeachers = availableTeachers,
            availableRooms = availableRooms,
            availableDisciplines = availableDisciplines,
            onCommit = { newGroupPlan ->
                val newGroupPlans = mutableAcademicPlan.plans.toMutableList().apply {
                    if (selected.isEmpty) {
                        add(newGroupPlan)
                    } else {
                        set(indexOf(selected), newGroupPlan)
                    }
                }
                mutableAcademicPlan = mutableAcademicPlan.copy(plans = newGroupPlans)
                selectedGroupPlan = null
            },
            onCancel = { selectedGroupPlan = null }
        )
    }

    AppDialog(
        state = rememberDialogState(width = 600.dp, height = 600.dp),
        title = "Учебный план",
        onCloseRequest = {
            if (isEditing) onCommit(mutableAcademicPlan)
            else onCancel()
        },
        contentModifier = Modifier.padding(16.dp)
    ) {
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            singleLine = true,
            value = mutableAcademicPlan.name,
            onValueChange = { mutableAcademicPlan = mutableAcademicPlan.copy(name = it) },
            label = { Text(text = "Имя") }
        )
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            mutableAcademicPlan.plans.forEach { groupPlan ->
                item(key = groupPlan.group.id) {
                    SimpleItemComponent(
                        title = groupPlan.group.name,
                        caption = "${groupPlan.items.size} предмета, ${groupPlan.items.sumOf { it.works.values.sum() }} часов",
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = {
                            val newPlans = mutableAcademicPlan.plans.toMutableList().apply { remove(groupPlan) }
                            mutableAcademicPlan = mutableAcademicPlan.copy(plans = newPlans)
                        },
                        onClick = { selectedGroupPlan = groupPlan }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
            onClick = { selectedGroupPlan = GroupPlan.Empty }
        ) {
            Text(text = "Добавить группу")
        }
        if (!isEditing) {
            Button(
                modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
                onClick = { onCommit(mutableAcademicPlan) }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}