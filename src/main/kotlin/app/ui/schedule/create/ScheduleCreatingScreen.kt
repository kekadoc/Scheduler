@file:OptIn(ExperimentalMaterialApi::class)

package app.ui.schedule.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.ui.common.ImageThemed
import app.ui.menu.SimpleMenuItem
import app.ui.menu.SimpleMenuItemLayout
import app.ui.schedule.create.plan.ScheduleCreatingAcademicPlanScreen
import app.ui.schedule.create.result.ScheduleCreatingResultScreen
import app.ui.schedule.create.rules.ScheduleCreatingRulesScreen
import common.view_model.viewModel

private enum class ScheduleCreatingItem : SimpleMenuItem {
    PLAN {
        override val id: Long = 1L
        override val text: String = "Учебный план"
        override val image: ImageVector = Icons.Default.Person
    },
    RULES {
        override val id: Long = 2L
        override val text: String = "Правила"
        override val image: ImageVector = Icons.Default.Place
    },
    FINAL {
        override val id: Long = 3L
        override val text: String = "Final"
        override val image: ImageVector = Icons.Default.Phone
    }
}

@Composable
private fun MenuHeader(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.size(44.dp),
            onClick = onClick,
            contentPadding = PaddingValues(4.dp)
        ) {
            ImageThemed(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Creator",
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
        Card {
            Column {
                MenuHeader(
                    onClick = {  }
                )
                SimpleMenuItemLayout(
                    modifier = Modifier.width(300.dp).fillMaxHeight(),
                    items = ScheduleCreatingItem.values().toList(),
                    selected = currentScreen,
                    header = {},
                    onItemSelect = { currentScreen = it },
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (currentScreen) {
                ScheduleCreatingItem.PLAN -> ScheduleCreatingAcademicPlanScreen()
                ScheduleCreatingItem.RULES -> ScheduleCreatingRulesScreen()
                ScheduleCreatingItem.FINAL -> ScheduleCreatingResultScreen()
            }
        }
    }

}

