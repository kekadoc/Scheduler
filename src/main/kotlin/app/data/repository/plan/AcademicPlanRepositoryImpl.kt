package app.data.repository.plan

import app.data.converter.DataConverter
import app.data.data_source.local.unit.discipline.DisciplineLocalDataSource
import app.data.data_source.local.unit.group.GroupLocalDataSource
import app.data.data_source.local.unit.plan.academic.AcademicPlanLocalDataSource
import app.data.data_source.local.unit.plan.academic.dao.AcademicPlanEntity
import app.data.data_source.local.unit.plan.discipline.DisciplinePlanLocalDataSource
import app.data.data_source.local.unit.plan.group.GroupPlanLocalDataSource
import app.data.data_source.local.unit.room.RoomsLocalDataSource
import app.data.data_source.local.unit.teacher.TeacherLocalDataSource
import app.schedule.plan.AcademicPlan
import app.schedule.plan.GroupPlan
import common.data.all
import common.extensions.catchResult
import common.extensions.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AcademicPlanRepositoryImpl(
    private val teachers: TeacherLocalDataSource,
    private val rooms: RoomsLocalDataSource,
    private val disciplines: DisciplineLocalDataSource,
    private val groups: GroupLocalDataSource,
    private val academicPlans: AcademicPlanLocalDataSource,
    private val groupPlans: GroupPlanLocalDataSource,
    private val disciplinePlans: DisciplinePlanLocalDataSource,
    private val converter: DataConverter
) : AcademicPlanRepository {

    override val allPlans: Flow<Result<List<AcademicPlan>>> = academicPlans.all
        .map(Result.Companion::success)
        .catchResult()
        .map { resultList ->
            resultList.mapCatching { list ->
                list.map { entity -> entity.convert() }
            }
        }

    override fun getPlan(id: Long): Flow<Result<AcademicPlan>> {
        return flowOf { academicPlans.get(id).mapCatching { it.convert() } }
    }

    override fun addPlan(name: String, plans: List<GroupPlan.Builder>): Flow<Result<AcademicPlan>> {
        return flowOf {
            kotlin.runCatching {
                val groups = plans.map { groupPlanBuilder ->
                    val disciplines = groupPlanBuilder.items.map { disciplinePlanBuilder ->
                        disciplinePlans.add(
                            disciplineId = disciplinePlanBuilder.discipline.id,
                            teacherId = disciplinePlanBuilder.teacher.id,
                            roomId = disciplinePlanBuilder.room.id,
                            works = disciplinePlanBuilder.works,
                            fillingType = disciplinePlanBuilder.fillingType
                        ).getOrThrow()
                    }
                    groupPlans.add(
                        groupId = groupPlanBuilder.group.id,
                        weekCount = groupPlanBuilder.weekCount,
                        disciplinePlanIds = disciplines.map { it.id.value }
                    ).getOrThrow()
                }
                academicPlans.add(
                    name = name,
                    groupPlanIds = groups.map { it.id.value }
                ).mapCatching { it.convert() }.getOrThrow()
            }
        }
    }

    override fun deletePlan(id: Long): Flow<Result<AcademicPlan>> {
        return flowOf { academicPlans.delete(id).mapCatching { it.convert() } }
    }

    override fun updatePlan(plan: AcademicPlan): Flow<Result<AcademicPlan>> {
        return flowOf {
            plan.plans.forEach { groupPlan ->
                groupPlan.items.forEach { disciplinePlan ->
                    disciplinePlans.update(id = disciplinePlan.id) {
                        this.fillingType = disciplinePlan.fillingType
                        this.works = disciplinePlan.works
                        this.roomId = disciplinePlan.room.id
                        this.teacherId = disciplinePlan.teacher.id
                    }
                }
                groupPlans.update(groupPlan.id) {
                    this.weekCount = groupPlan.weekCount
                    this.groupId = groupPlan.group.id
                    this.disciplinePlanIds =
                }
            }
            academicPlans.update(plan.id) {
                this.name = plan.name
                this.groupPlanIds = plan.plans.map { it.id }
                this.groupPlanIds = plan.getAll().map {  }
            }.mapCatching { it.convert() }
        }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { academicPlans.clear() }
    }


    private suspend fun AcademicPlanEntity.convert(): AcademicPlan {
        return converter.run {
            this@convert.convert(
                groups = groups,
                disciplines = disciplines,
                teachers = teachers,
                rooms = rooms,
                groupPlans = groupPlans,
                disciplinePlans = disciplinePlans
            )
        }
    }
}