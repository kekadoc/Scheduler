package app.data.converter

import app.data.data_source.local.unit.discipline.dao.DisciplineEntity
import app.data.data_source.local.unit.group.dao.GroupEntity
import app.data.data_source.local.unit.room.RoomsLocalDataSource
import app.data.data_source.local.unit.room.dao.RoomEntity
import app.data.data_source.local.unit.teacher.TeacherLocalDataSource
import app.data.data_source.local.unit.teacher.dao.TeacherEntity
import app.domain.model.Discipline
import app.domain.model.Group
import app.domain.model.Room
import app.domain.model.Teacher

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
}