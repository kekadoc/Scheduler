package data.repository.discipline

import common.data.all
import common.extensions.catchResult
import common.extensions.flowOf
import data.converter.DataConverter
import data.data_source.local.unit.discipline.DisciplineLocalDataSource
import data.data_source.local.unit.discipline.dao.DisciplineEntity
import data.data_source.local.unit.room.RoomsLocalDataSource
import data.data_source.local.unit.teacher.TeacherLocalDataSource
import domain.model.Discipline
import domain.model.Room
import domain.model.Teacher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DisciplineRepositoryImpl(
    private val localDataSource: DisciplineLocalDataSource,
    private val teacherLocalDataSource: TeacherLocalDataSource,
    private val roomsLocalDataSource: RoomsLocalDataSource,
    private val converter: DataConverter
) : DisciplineRepository {

    override val allDisciplines: Flow<Result<List<Discipline>>> = localDataSource.all
        .map(Result.Companion::success)
        .catchResult()
        .map { resultList ->
            resultList.mapCatching { list ->
                list.map { entity -> entity.convert().getOrThrow() }
            }
        }

    override fun getDiscipline(id: Long): Flow<Result<Discipline>> {
        return flowOf { localDataSource.get(id).mapCatching { it.convert().getOrThrow() } }
    }

    override fun addDiscipline(name: String, teachers: List<Teacher>, rooms: List<Room>): Flow<Result<Discipline>> {
        return flowOf {
            localDataSource.add(
                name = name,
                teachers = teachers.map { it.id },
                rooms = rooms.map { it.id }
            ).mapCatching { it.convert().getOrThrow() }
        }
    }

    override fun deleteDiscipline(id: Long): Flow<Result<Discipline>> {
        return flowOf { localDataSource.delete(id).mapCatching { it.convert().getOrThrow() } }
    }

    override fun updateDiscipline(discipline: Discipline): Flow<Result<Discipline>> {
        return flowOf {
            localDataSource.update(discipline.id) {
                this.name = discipline.name
                this.rooms = discipline.rooms.map { it.id }
                this.teachers = discipline.teachers.map { it.id }
            }.mapCatching { it.convert().getOrThrow() }
        }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { localDataSource.clear() }
    }


    private suspend fun DisciplineEntity.convert(): Result<Discipline> {
        return converter.run {
            this@convert.convert(teacherLocalDataSource, roomsLocalDataSource)
        }
    }

}