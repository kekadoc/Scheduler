package app.ui.database.plan

import app.domain.model.Discipline
import app.domain.model.Group
import app.domain.model.Room
import app.domain.model.Teacher
import app.schedule.plan.AcademicPlan

data class AcademicPlanState(
    val availableGroups: List<Group> = emptyList(),
    val availableRooms: List<Room> = emptyList(),
    val availableTeachers: List<Teacher> = emptyList(),
    val availableDisciplines: List<Discipline> = emptyList(),
    val plans: List<AcademicPlan> = emptyList()
)