@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package app.ui.database.teachers

import androidx.compose.foundation.ExperimentalFoundationApi
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
import app.domain.model.Teacher
import app.domain.model.fullName
import app.domain.model.isEmpty
import app.ui.database.teachers.dialog.DialogTeacher
import common.extensions.collectState
import common.ui.ImageThemed
import common.ui.SimpleItemComponent
import common.view_model.viewModel

@Composable
fun TeachersDatabaseScreen() {
    val viewModel = viewModel<TeachersDatabaseViewModel>()
    val state by viewModel.collectState()
    val (teachers, isLoading) = state

    var selectedTeacher: Teacher? by remember { mutableStateOf(null) }

    selectedTeacher?.also { teacher ->
        DialogTeacher(
            teacher = teacher,
            onCloseRequest = { selectedTeacher = null },
            onUpdate = { newTeacher ->
                if (teacher.isEmpty) {
                    viewModel.create(
                        lastName = newTeacher.lastName,
                        firstName = newTeacher.firstName,
                        middleName = newTeacher.middleName,
                        speciality = newTeacher.speciality
                    )
                } else {
                    viewModel.update(newTeacher)
                }
                selectedTeacher = null
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            teachers.forEach { teacher ->
                item {
                    SimpleItemComponent(
                        title = teacher.fullName,
                        caption = teacher.speciality,
                        rightImage = Icons.Default.Delete,
                        onRightImageClick = { viewModel.delete(teacher) },
                        onClick = { selectedTeacher = teacher }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(4.dp),
            onClick = { selectedTeacher = Teacher.Empty },
        ) {
            ImageThemed(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }

}

