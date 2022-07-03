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

class DataConverterImpl : DataConverter {

    override suspend fun DisciplineEntity.convert(
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource
    ): Discipline {
        return Discipline(
            id = this.id.value,
            name = this.name,
            teachers = this.teachers.map { id ->
                teachers.get(id).getOrThrow().convert()
            },
            rooms = this.rooms.map { id ->
                rooms.get(id).getOrThrow().convert()
            }
        )
    }

    override suspend fun TeacherEntity.convert(): Teacher {
        return Teacher(
            id = this.id.value,
            lastName = this.lastName,
            firstName = this.firstName,
            middleName = this.middleName,
            speciality = this.speciality
        )
    }

    override suspend fun RoomEntity.convert(): Room {
        return Room(
            id = this.id.value,
            name = this.name
        )
    }

    override suspend fun GroupEntity.convert(): Group {
        return Group(
            id = this.id.value,
            name = this.name
        )
    }


    override suspend fun AcademicPlanEntity.convert(
        groups: GroupLocalDataSource,
        disciplines: DisciplineLocalDataSource,
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource,
        groupPlans: GroupPlanLocalDataSource,
        disciplinePlans: DisciplinePlanLocalDataSource
    ): AcademicPlan {
        return AcademicPlan(
            name = this.name,
            plan = this.groupPlanIds.map { id ->
                groupPlans.get(id).getOrThrow().convert(
                    groups = groups,
                    disciplines = disciplines,
                    teachers = teachers,
                    rooms = rooms,
                    disciplinePlans = disciplinePlans
                )
            }
        )
    }

    override suspend fun GroupPlanEntity.convert(
        groups: GroupLocalDataSource,
        disciplines: DisciplineLocalDataSource,
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource,
        disciplinePlans: DisciplinePlanLocalDataSource
    ): GroupPlan {
        return GroupPlan(
            group = groups.get(this.groupId).getOrThrow().convert(),
            weekCount = this.weekCount,
            items = this.disciplinePlanIds.map { id ->
                disciplinePlans.get(id).getOrThrow().convert(
                    disciplines = disciplines,
                    teachers = teachers,
                    rooms = rooms
                )
            }
        )
    }

    override suspend fun DisciplinePlanEntity.convert(
        disciplines: DisciplineLocalDataSource,
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource
    ): DisciplinePlan {
        return DisciplinePlan(
            discipline = disciplines.get(this.disciplineId).getOrThrow().convert(
                teachers = teachers,
                rooms = rooms
            ),
            teacher = teachers.get(this.teacherId).getOrThrow().convert(),
            room = rooms.get(this.roomId).getOrThrow().convert(),
            works = this.works,
            fillingType = this.fillingType
        )
    }

}