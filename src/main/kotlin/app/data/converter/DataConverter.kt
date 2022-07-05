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

interface DataConverter {

    suspend fun DisciplineEntity.convert(
        teachers: TeacherLocalDataSource,
        rooms: RoomsLocalDataSource
    ): Discipline

    suspend fun TeacherEntity.convert(): Teacher

    suspend fun RoomEntity.convert(): Room

    suspend fun GroupEntity.convert(): Group
}