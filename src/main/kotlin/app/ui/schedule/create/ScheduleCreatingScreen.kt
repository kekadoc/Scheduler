@file:OptIn(ExperimentalMaterialApi::class)

package app.ui.schedule.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Rule
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.ui.schedule.create.plan.ScheduleCreatingPlanScreen
import app.ui.schedule.create.result.ScheduleCreatingResultScreen
import app.ui.schedule.create.rules.ScheduleCreatingRulesScreen
import common.ui.menu.SimpleMenuItem
import common.ui.menu.SimpleMenuItemLayout
import common.view_model.viewModel

private enum class ScheduleCreatingItem : SimpleMenuItem {
    PLAN {
        override val id: Long = 1L
        override val text: String = "Учебный план"
        override val image: ImageVector = Icons.Default.ListAlt
    },
    RULES {
        override val id: Long = 2L
        override val text: String = "Правила"
        override val image: ImageVector = Icons.Default.Rule
    },
    FINAL {
        override val id: Long = 3L
        override val text: String = "Final"
        override val image: ImageVector = Icons.Default.Build
    }
}

@Composable
private fun MenuHeader(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Создание",
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun ScheduleCreatingScreen() {

    val viewModel = viewModel<ScheduleCreatingViewModel>()
    //val state by viewModel.container.stateFlow.collectAsState()
    var currentScreen: ScheduleCreatingItem by remember { mutableStateOf(ScheduleCreatingItem.PLAN) }

    Row(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 4.dp
        ) {
            Column {
                MenuHeader(
                    onClick = {  }
                )
                SimpleMenuItemLayout(
                    modifier = Modifier.width(250.dp).fillMaxHeight(),
                    items = ScheduleCreatingItem.values().toList(),
                    selected = currentScreen,
                    header = {},
                    onItemSelect = { currentScreen = it },
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (currentScreen) {
                ScheduleCreatingItem.PLAN -> ScheduleCreatingPlanScreen()
                ScheduleCreatingItem.RULES -> ScheduleCreatingRulesScreen()
                ScheduleCreatingItem.FINAL -> ScheduleCreatingResultScreen()
            }
        }
    }

}

