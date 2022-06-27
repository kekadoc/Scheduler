package data.converter

import data.data_source.local.unit.discipline.dao.DisciplineEntity
import data.data_source.local.unit.group.dao.GroupEntity
import data.data_source.local.unit.room.RoomsLocalDataSource
import data.data_source.local.unit.room.dao.RoomEntity
import data.data_source.local.unit.teacher.TeacherLocalDataSource
import data.data_source.local.unit.teacher.dao.TeacherEntity
import domain.model.Discipline
import domain.model.Group
import domain.model.Room
import domain.model.Teacher

class DataConverterImpl : DataConverter {

    override suspend fun DisciplineEntity.convert(
        teacherLocalDataSource: TeacherLocalDataSource,
        roomsLocalDataSource: RoomsLocalDataSource
    ): Result<Discipline> {
        return kotlin.runCatching {
            val teachers: List<Teacher> = this.teachers.map { id ->
                teacherLocalDataSource.get(id).getOrThrow().convert().getOrThrow()
            }
            val rooms: List<Room> = this.rooms.map { id ->
                roomsLocalDataSource.get(id).getOrThrow().convert().getOrThrow()
            }
            return@runCatching Discipline(
                id = this.id.value,
                name = this.name,
                teachers = teachers,
                rooms = rooms
            )
        }

    }

    override suspend fun TeacherEntity.convert(): Result<Teacher> {
        return kotlin.runCatching {
            Teacher(
                id = this.id.value,
                lastName = this.lastName,
                firstName = this.firstName,
                middleName = this.middleName,
                speciality = this.speciality
            )
        }
    }

    override suspend fun RoomEntity.convert(): Result<Room> {
        return kotlin.runCatching {
            Room(
                id = this.id.value,
                name = this.name
            )
        }
    }

    override suspend fun GroupEntity.convert(): Result<Group> {
        return kotlin.runCatching {
            Group(
                id = this.id.value,
                name = this.name
            )
        }
    }

}