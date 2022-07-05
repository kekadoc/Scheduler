package app.data.injector

import app.data.repository.discipline.DisciplinesRepository
import app.data.repository.group.GroupsRepository
import app.data.repository.plan.AcademicPlanRepository
import app.data.repository.room.RoomsRepository
import app.data.repository.teacher.TeachersRepository

data class DataRepository(
    val teachers: TeachersRepository,
    val rooms: RoomsRepository,
    val disciplines: DisciplinesRepository,
    val groups: GroupsRepository,
    val academicPlan: AcademicPlanRepository
)