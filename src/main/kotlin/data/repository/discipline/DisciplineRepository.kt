package data.repository.discipline

import domain.model.Discipline
import domain.model.Room
import domain.model.Teacher
import kotlinx.coroutines.flow.Flow

interface DisciplineRepository {

    val allDisciplines: Flow<Result<List<Discipline>>>


    fun getDiscipline(id: Long): Flow<Result<Discipline>>

    fun addDiscipline(
        name: String,
        teachers: List<Teacher> = emptyList(),
        rooms: List<Room> = emptyList()
    ): Flow<Result<Discipline>>

    fun deleteDiscipline(id: Long): Flow<Result<Discipline>>

    fun updateDiscipline(discipline: Discipline): Flow<Result<Discipline>>
}