package app.ui.database.plan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberDialogState
import app.domain.model.*
import app.schedule.plan.DisciplinePlan
import app.schedule.plan.DisciplinePlan.Companion.isEmpty
import app.schedule.plan.GroupPlan
import app.schedule.plan.GroupPlan.Companion.isEmpty
import app.ui.common.SimpleItemComponent
import app.ui.common.dialog.AppDialog
import app.ui.common.dialog.DialogSelection

/**
 * Диалог для добавления/изменения учебного плана для группы
 */
@Composable
fun DialogGroupPlanEditor(
    groupPlan: GroupPlan,
    availableGroups: List<Group>,
    availableRooms: List<Room>,
    availableDisciplines: List<Discipline>,
    availableTeachers: List<Teacher>,
    onCancel: () -> Unit,
    onCommit: (GroupPlan) -> Unit
) {
    val isEditing: Boolean = remember { !groupPlan.isEmpty }

    var mutableGroupPlan: GroupPlan by remember { mutableStateOf(groupPlan) }

    var groupSelection: Boolean by remember { mutableStateOf(false) }
    var disciplinePlanSelected: DisciplinePlan? by remember { mutableStateOf(null) }

    if (groupSelection) {
        val groups = availableGroups
        DialogSelection(
            title = "Выбор группы",
            list = groups,
            getText = { it.name },
            onSelect = { mutableGroupPlan = mutableGroupPlan.copy(group = it); groupSelection = false },
            onCancel = { groupSelection = false }
        )
    }

    disciplinePlanSelected?.also { selected ->
        DialogDisciplinePlanEditor(
            plan = selected,
            availableDisciplines = availableDisciplines,
            availableRooms = availableRooms,
            availableTeachers = availableTeachers,
            onCommit = { newPlan ->
                val newPlans = mutableGroupPlan.items.toMutableList().apply {
                    if (selected.isEmpty) {
                        add(newPlan)
                    } else {
                        set(indexOf(selected), newPlan)
                    }
                }
                mutableGroupPlan = mutableGroupPlan.copy(items = newPlans)
                disciplinePlanSelected = null
            },
            onCancel = { disciplinePlanSelected = null }
        )
    }

    AppDialog(
        onCloseRequest = {
            if (isEditing) {
                onCommit(mutableGroupPlan)
            } else {
                onCancel()
            }
        },
        state = rememberDialogState(width = 400.dp, height = 600.dp),
        contentModifier = Modifier.padding(8.dp),
        title = "План группы"
    ) {
        if (mutableGroupPlan.group.isEmpty) {
            SimpleItemComponent(
                modifier = Modifier.height(44.dp),
                onClick = { groupSelection = true },
                label = "Выбрать группу",
                labelStyle = MaterialTheme.typography.h5,
                labelAlignment = Alignment.CenterHorizontally
            )
        } else {
            SimpleItemComponent(
                modifier = Modifier.wrapContentHeight(),
                title = mutableGroupPlan.group.name,
                labelStyle = MaterialTheme.typography.h6,
                caption = "${mutableGroupPlan.items.size} предмета, ${mutableGroupPlan.items.sumOf { it.works.values.sum() }} часов",
                onClick = if (!isEditing) {
                    { groupSelection = true }
                } else {
                    null
                }
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
        ) {
            mutableGroupPlan.items.forEachIndexed { index, plan ->
                item {
                    SimpleItemComponent(
                        label = plan.discipline.name,
                        title = plan.works.values.sum().toString(),
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = {
                            val newPlans = mutableGroupPlan.items.toMutableList().apply { remove(plan) }
                            mutableGroupPlan = mutableGroupPlan.copy(items = newPlans)
                        },
                        onClick = { disciplinePlanSelected = plan }
                    )
                }
            }
        }
        if (!mutableGroupPlan.group.isEmpty) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = { disciplinePlanSelected = DisciplinePlan.Empty }
            ) {
                Text(text = "Добавить предмет")
            }
        }
        if (!isEditing) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                enabled = !mutableGroupPlan.group.isEmpty && mutableGroupPlan.items.isNotEmpty(),
                onClick = { onCommit(mutableGroupPlan) }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}