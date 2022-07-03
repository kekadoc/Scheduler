package app.data.repository.discipline

import app.domain.model.Discipline
import app.domain.model.Room
import app.domain.model.Teacher
import kotlinx.coroutines.flow.Flow

interface DisciplinesRepository {

    val allDisciplines: Flow<Result<List<Discipline>>>


    fun getDiscipline(id: Long): Flow<Result<Discipline>>

    fun addDiscipline(
        name: String,
        teachers: List<Teacher> = emptyList(),
        rooms: List<Room> = emptyList()
    ): Flow<Result<Discipline>>

    fun deleteDiscipline(id: Long): Flow<Result<Discipline>>

    fun updateDiscipline(discipline: Discipline): Flow<Result<Discipline>>

    fun clear(): Flow<Result<Unit>>
}