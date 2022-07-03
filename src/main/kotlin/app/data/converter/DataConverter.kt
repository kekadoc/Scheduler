package app.data.converter

import app.data.data_source.local.unit.discipline.DisciplineLocalDataSource
import app.data.data_source.local.unit.discipline.dao.DisciplineEntity
import app.data.data_source.local.unit.group.GroupLocalDataSource
import app.data.data_source.local.unit.group.dao.GroupEntity
import app.data.data_source.local.unit.plan.academic.dao.AcademicPlanEntity
import app.data.data_source.local.unit.plan.discipline.DisciplinePlanLocalDataSource
import app.data.data_source.local.unit.plan.discipline.dao.DisciplinePlanEntity
import app.data.data_source.local.unit.plan.group.GroupPlanLocalDataSource
import app.data.data_source.local.unit.plan.group.dao.GroupPlanEntity
import app.data.data_source.local.unit.room.RoomsLocalDataSource
import app.data.data_source.local.unit.room.dao.RoomEntity
import app.data.data_source.local.unit.teacher.TeacherLocalDataSource
import app.data.data_source.local.unit.teacher.dao.TeacherEntity
import app.domain.model.Discipline
import app.domain.model.Group
import app.domain.model.Room
import app.domain.model.Teacher
import app.schedule.plan.AcademicPlan
import app.schedule.plan.DisciplinePlan
import app.schedule.plan.GroupPlan

interface DataConverter {

    suspend fun DisciplineEntity.convert(
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource
    ): Discipline

    suspend fun TeacherEntity.convert(): Teacher

    suspend fun RoomEntity.convert(): Room

    suspend fun GroupEntity.convert(): Group

    suspend fun AcademicPlanEntity.convert(
        groups: GroupLocalDataSource,
        disciplines: DisciplineLocalDataSource,
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource,
        groupPlans: GroupPlanLocalDataSource,
        disciplinePlans: DisciplinePlanLocalDataSource,
    ): AcademicPlan

    suspend fun GroupPlanEntity.convert(
        groups: GroupLocalDataSource,
        disciplines: DisciplineLocalDataSource,
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource,
        disciplinePlans: DisciplinePlanLocalDataSource
    ): GroupPlan

    suspend fun DisciplinePlanEntity.convert(
        disciplines: DisciplineLocalDataSource,
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource
    ): DisciplinePlan
}