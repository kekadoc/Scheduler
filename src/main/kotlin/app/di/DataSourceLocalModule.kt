package app.di

import app.data.data_source.local.unit.plan.academic.AcademicPlanLocalDataSource
import app.data.data_source.local.unit.plan.academic.AcademicPlanLocalDataSourceImpl
import app.data.data_source.local.unit.group.GroupLocalDataSource
import app.data.data_source.local.unit.group.GroupLocalDataSourceImpl
import app.data.data_source.local.unit.room.RoomsLocalDataSource
import app.data.data_source.local.unit.room.RoomsLocalDataSourceImpl
import app.data.data_source.local.unit.teacher.TeacherLocalDataSource
import app.data.data_source.local.unit.teacher.TeacherLocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {

    single<TeacherLocalDataSource> {
        TeacherLocalDataSourceImpl()
    }

    single<AcademicPlanLocalDataSource> {
        AcademicPlanLocalDataSourceImpl()
    }

    single<GroupLocalDataSource> {
        GroupLocalDataSourceImpl()
    }

    single<RoomsLocalDataSource> {
        RoomsLocalDataSourceImpl()
    }
}