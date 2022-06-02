package di

import data.data_source.local.unit.academic_subject.AcademicSubjectLocalDataSource
import data.data_source.local.unit.academic_subject.AcademicSubjectLocalDataSourceImpl
import data.data_source.local.unit.space.SpaceLocalDataSource
import data.data_source.local.unit.space.SpaceLocalDataSourceImpl
import data.data_source.local.unit.space.preferences.SpacePreferences
import data.data_source.local.unit.space.preferences.SpacePreferencesImpl
import data.data_source.local.unit.student_group.StudentGroupLocalDataSource
import data.data_source.local.unit.student_group.StudentGroupLocalDataSourceImpl
import data.data_source.local.unit.study_room.StudyRoomsLocalDataSource
import data.data_source.local.unit.study_room.StudyRoomsLocalDataSourceImpl
import data.data_source.local.unit.teacher.TeacherLocalDataSource
import data.data_source.local.unit.teacher.TeacherLocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {

    single<SpacePreferences> {
        SpacePreferencesImpl()
    }

    single<SpaceLocalDataSource> {
        SpaceLocalDataSourceImpl(
            preferences = get()
        )
    }

    single<TeacherLocalDataSource> {
        TeacherLocalDataSourceImpl()
    }

    single<AcademicSubjectLocalDataSource> {
        AcademicSubjectLocalDataSourceImpl()
    }

    single<StudentGroupLocalDataSource> {
        StudentGroupLocalDataSourceImpl()
    }

    single<StudyRoomsLocalDataSource> {
        StudyRoomsLocalDataSourceImpl()
    }
}