package app.data.repository.plan

import app.data.data_source.local.unit.plan.AcademicPlanLocalDataSource
import app.data.data_source.local.unit.plan.dao.AcademicPlanEntity
import app.schedule.plan.AcademicPlan
import app.schedule.plan.GroupPlan
import common.data.all
import common.extensions.catchResult
import common.extensions.flowOf
import common.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AcademicPlanRepositoryImpl(
    private val localDataSource: AcademicPlanLocalDataSource
) : AcademicPlanRepository {

    override val allPlans: Flow<Result<List<AcademicPlan>>> = localDataSource.all
        .map(Result.Companion::success)
        .catchResult()
        .map { resultList ->
            Logger.log("AcademicPlanRepository allPlans $resultList")
            resultList.mapCatching { list ->
                list.map { entity -> entity.convert() }
            }
        }

    override fun getAll(): Flow<Result<List<AcademicPlan>>> {
        return flowOf { localDataSource.getAll() }.map { result ->
            result.mapCatching { list -> list.map { it.convert() } }
        }
    }

    override fun getPlan(id: Long): Flow<Result<AcademicPlan>> {
        return flowOf { localDataSource.get(id).mapCatching { it.convert() } }
    }

    override fun addPlan(name: String, plans: List<GroupPlan>): Flow<Result<AcademicPlan>> {
        Logger.log("addPlan $name")
        return flowOf {
            localDataSource.add(name, plans).mapCatching { it.convert() }
        }
    }

    override fun deletePlan(id: Long): Flow<Result<AcademicPlan>> {
        return flowOf { localDataSource.delete(id).mapCatching { it.convert() } }
    }

    override fun updatePlan(plan: AcademicPlan): Flow<Result<AcademicPlan>> {
        return flowOf {
            localDataSource.update(plan.id) {
                this.name = plan.name
                this.data = plan.plans
            }.mapCatching { it.convert() }
        }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { localDataSource.clear() }
    }


    private fun AcademicPlanEntity.convert(): AcademicPlan {
        return AcademicPlan(
            id = this.id.value,
            name = this.name,
            plans = this.data
        )
    }
}