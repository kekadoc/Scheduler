package app.data.repository.plan

import app.schedule.plan.AcademicPlan
import app.schedule.plan.GroupPlan
import kotlinx.coroutines.flow.Flow

interface AcademicPlanRepository {

    val allPlans: Flow<Result<List<AcademicPlan>>>

    fun getAll(): Flow<Result<List<AcademicPlan>>>

    fun getPlan(id: Long): Flow<Result<AcademicPlan>>

    fun addPlan(name: String, plans: List<GroupPlan>): Flow<Result<AcademicPlan>>

    fun deletePlan(id: Long): Flow<Result<AcademicPlan>>

    fun updatePlan(plan: AcademicPlan): Flow<Result<AcademicPlan>>

    fun clear(): Flow<Result<Unit>>
}