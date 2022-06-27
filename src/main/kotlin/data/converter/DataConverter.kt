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

interface DataConverter {

    suspend fun DisciplineEntity.convert(
        teacherLocalDataSource: TeacherLocalDataSource,
        roomsLocalDataSource: RoomsLocalDataSource
    ): Result<Discipline>

    suspend fun TeacherEntity.convert(): Result<Teacher>

    suspend fun RoomEntity.convert(): Result<Room>

    suspend fun GroupEntity.convert(): Result<Group>
}