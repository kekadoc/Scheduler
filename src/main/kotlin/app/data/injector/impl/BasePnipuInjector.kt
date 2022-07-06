package app.data.injector.impl

import app.data.injector.DataInjector
import app.data.injector.DataRepository
import app.data.repository.discipline.DisciplinesRepository
import app.data.repository.group.GroupsRepository
import app.data.repository.plan.AcademicPlanRepository
import app.data.repository.room.RoomsRepository
import app.data.repository.space.SpacesRepository
import app.data.repository.teacher.TeachersRepository
import app.domain.model.*
import app.schedule.plan.AcademicPlan
import app.schedule.plan.DisciplinePlan
import app.schedule.plan.GroupPlan
import common.logger.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

object BasePnipuInjector : DataInjector {

    override val id: Long = 777L

    override val type: DataInjector.Type = DataInjector.Type.ONLY_ONCE


    override suspend fun getSpace(spaces: SpacesRepository): Space {
        return spaces.addSpace("ПНИПУ").first().getOrThrow()
    }

    override suspend fun inject(data: DataRepository) {
        val (teachers, rooms, disciplines, groups, academicPlan) = data

        merge(
            teachers.clear(),
            rooms.clear(),
            disciplines.clear(),
            groups.clear(),
            academicPlan.clear()
        ).collect()

        injectRooms(rooms)
        injectTeachers(teachers)
        injectGroups(groups)
        injectDisciplines(disciplines, teachers, rooms)
        with(academicPlan) {
            with(groups) {
                with(disciplines) {
                    injectPlan()
                }
            }
        }
    }


    suspend fun injectGroups(groupsRepository: GroupsRepository): Unit = with(groupsRepository) {
        merge(
            addGroup("АСУ-21-1б-ЧФ"),
            addGroup("АТПП-21-1б-ЧФ"),
            addGroup("ПГС-21-1б-ЧФ"),
            addGroup("АСУ-20-1б-ЧФ"),
            addGroup("ПГС-20-1б-ЧФ"),
            addGroup("АТПП-20-1б-ЧФ"),
            addGroup("ЭС-20-1б-ЧФ"),
            addGroup("АСУ-19-1б-ЧФ"),
            addGroup("АТПП-19-1б-ЧФ"),
            addGroup("ЭС-19-1б-ЧФ"),
            addGroup("АСУ-18-1б-ЧФ"),
            addGroup("АТПП-18-1б-ЧФ"),
            addGroup("ЭС-18-1б-ЧФ")
        ).collect()
    }

    suspend fun injectRooms(roomsRepository: RoomsRepository): Unit {
        with(roomsRepository) {
            merge(
                addRoom("ауд. 1"),
                addRoom("ауд. 2"),
                addRoom("ауд. 7"),
                addRoom("ауд. 28"),
                addRoom("ауд. 29"),
                addRoom("ауд. 32"),
                addRoom("ауд. 37"),
                addRoom("ауд. 40"),
                addRoom("ауд. 43"),
                addRoom("ауд. 47"),
                addRoom("ауд. 216"),
                addRoom("ауд. 211"),
                addRoom("ауд. 212"),
                addRoom("ауд. 213"),
                addRoom("спортзал")
            ).collect()
        }
    }

    suspend fun injectTeachers(teachersRepository: TeachersRepository): Unit = with(teachersRepository) {
        merge(
            addTeacher(lastName = "Морозов", firstName = "Е.", middleName = "А.", speciality = "д-р техн.наук, профессор"),
            addTeacher(lastName = "Морзова", firstName = "А.", middleName = "Р.", speciality = "канд.техн.наук, доцент"),
            addTeacher(lastName = "Сметанина", firstName = "Е.", middleName = "В.", speciality = "старший преподаватель"),
            addTeacher(lastName = "Германюк", firstName = "Г.", middleName = "Ю.", speciality = "канд.физ.-мат.наук,доцент"),
            addTeacher(lastName = "Фокин", firstName = "В.", middleName = "Я.", speciality = "канд.экон.наук, доцент"),
            addTeacher(lastName = "Русских", firstName = "Т.", middleName = "И.", speciality = "канд.пед.наук, доцент"),
            addTeacher(lastName = "Сухих", firstName = "И.", middleName = "И.", speciality = "старший преподаватель"),
            addTeacher(lastName = "Зайниева", firstName = "Т.", middleName = "В.", speciality = "канд.экон.наук, доцент"),
            addTeacher(lastName = "Ковязин", firstName = "В.", middleName = "А.", speciality = "канд.техн.наук, доцент"),
            addTeacher(lastName = "Ковязина", firstName = "И.", middleName = "И.", speciality = "старший преподаватель"),
            addTeacher(lastName = "Лабутина", firstName = "Т.", middleName = "В.", speciality = "старший преподаватель"),
            addTeacher(lastName = "Шергина", firstName = "М.", middleName = "А.", speciality = " старший преподаватель"),
            addTeacher(lastName = "Травников", firstName = "А.", middleName = "В.", speciality = " канд.техн.наук, доцент"),
            addTeacher(lastName = "Горяева", firstName = "И.", middleName = "А.", speciality = "канд.экон.наук, доцент"),
            addTeacher(lastName = "Баженова", firstName = "Т.", middleName = "Р.", speciality = "старший преподаватель"),
            addTeacher(lastName = "Цигвинцева", firstName = "Г.", middleName = "Л.", speciality = "канд.филос.наук, доцент"),
            addTeacher(lastName = "Куликов", firstName = "Н.", middleName = "М.", speciality = "канд.пед.наук, доцент"),
            addTeacher(lastName = "Красильников", firstName = "С.", middleName = "М.", speciality = "канд.техн.наук, доцент"),
            addTeacher(lastName = "Горюшков", firstName = "Г.", middleName = "А.", speciality = "старший преподаватель"),
            addTeacher(lastName = "Щербакова", firstName = "Е.", middleName = "В.", speciality = "канд.психол.наук, доцент")
        ).collect()

    }

    suspend fun injectDisciplines(
        disciplinesRepository: DisciplinesRepository,
        teachersRepository: TeachersRepository,
        roomsRepository: RoomsRepository
    ): Unit = with(disciplinesRepository) {
        merge(
            addDiscipline(
                name = "ФИЗИКА",
                teachers = teachersRepository.findTeacherByLastName("Морозов"),
                rooms = roomsRepository.findRoomByName("ауд. 28", "ауд. 1")
            ),
            addDiscipline(
                name = "ИНОСТРАННЫЙ ЯЗЫК",
                teachers = teachersRepository.findTeacherByLastName("Сметанина"),
                rooms = roomsRepository.findRoomByName("ауд. 37")
            ),
            addDiscipline(
                name = "ТЕОРЕТИЧЕКАЯ МЕХАНИКА",
                teachers = teachersRepository.findTeacherByLastName("Германюк"),
                rooms = roomsRepository.findRoomByName("ауд. 47")
            ),
            addDiscipline(
                name = "ИНФОРМАТИКА",
                teachers = teachersRepository.findTeacherByLastName("Лабутина"),
                rooms = roomsRepository.findRoomByName("ауд. 32", "ауд. 7")
            ),
            addDiscipline(
                name = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА",
                teachers = teachersRepository.findTeacherByLastName("Русских", "Баженова"),
                rooms = roomsRepository.findRoomByName("ауд. 28")
            ),
            addDiscipline(
                name = "ИСТОРИЯ",
                teachers = teachersRepository.findTeacherByLastName("Цигвинцева"),
                rooms = roomsRepository.findRoomByName("ауд. 40")
            ),
            addDiscipline(
                name = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА",
                teachers = teachersRepository.findTeacherByLastName("Куликов"),
                rooms = roomsRepository.findRoomByName("спортзал")
            ),
            addDiscipline(
                name = "ИНЖЕНЕРНАЯ ГЕОДЕЗИЯ",
                teachers = teachersRepository.findTeacherByLastName("Фокин"),
                rooms = roomsRepository.findRoomByName("ауд. 216")
            ),
            addDiscipline(
                name = "МАТЕМАТИКА",
                teachers = teachersRepository.findTeacherByLastName("Морзова"),
                rooms = roomsRepository.findRoomByName("ауд. 32", "ауд. 47")
            ),
            addDiscipline(
                name = "ИНЖЕНЕРНАЯ ГЕОМЕТРИЯ и КОМПЬЮТЕРНАЯ ГРАФИКА",
                teachers = teachersRepository.findTeacherByLastName("Красильников"),
                rooms = roomsRepository.findRoomByName("ауд. 211")
            ),
            addDiscipline(
                name = "ОСНОВЫ АЛГОРИТМИЗАЦИИ и ПРОГАРММИРОВАНИЯ",
                teachers = teachersRepository.findTeacherByLastName("Лабутина"),
                rooms = roomsRepository.findRoomByName("ауд. 7")
            ),


            addDiscipline(
                name = "ЭКОНОМИКА",
                teachers = teachersRepository.findTeacherByLastName("Фокин"),
                rooms = roomsRepository.findRoomByName("ауд. 1", "ауд. 47")
            ),
            addDiscipline(
                name = "ЭЛЕКТРОСНАБЖЕНИЕ с ОСНОВАМИ ЭЛЕКТРОТЕХНИКИ",
                teachers = teachersRepository.findTeacherByLastName("Шергина"),
                rooms = roomsRepository.findRoomByName("ауд. 213")
            ),
            addDiscipline(
                name = "ТЕХНИКА ВЫСОКИХ НАПРЯЖЕНИЙ",
                teachers = teachersRepository.findTeacherByLastName("Ковязин"),
                rooms = roomsRepository.findRoomByName("ауд. 211")
            ),
            addDiscipline(
                name = "ЭКОЛОГИЯ",
                teachers = teachersRepository.findTeacherByLastName("Травников"),
                rooms = roomsRepository.findRoomByName("ауд. 47")
            ),
            addDiscipline(
                name = "ОБЪЕКТНО-ОРИЕНТИРОВАННОЕ ПРОГРАММИРОВАНИЕ",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),
            addDiscipline(
                name = "ОСНОВЫ ИНЖЕНЕРНОЙ ГЕОЛОГИИ и МЕХАНИКА ГРУНТОВ",
                teachers = teachersRepository.findTeacherByLastName("Баженова"),
                rooms = roomsRepository.findRoomByName("ауд. 216")
            ),
            addDiscipline(
                name = "ЭКОНОМИКА и БИЗНЕС",
                teachers = teachersRepository.findTeacherByLastName("Зайниева"),
                rooms = roomsRepository.findRoomByName("ауд. 47", "ауд. 1")
            ),
            addDiscipline(
                name = "ТЕОРЕТИЧЕСКИЕ ОСНОВЫ ЭЛЕКТРОТЕХНИКИ",
                teachers = teachersRepository.findTeacherByLastName("Шергина"),
                rooms = roomsRepository.findRoomByName("ауд. 213")
            ),
            addDiscipline(
                name = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ",
                teachers = teachersRepository.findTeacherByLastName("Русских"),
                rooms = roomsRepository.findRoomByName("ауд. 28", "ауд. 47")
            ),
            addDiscipline(
                name = "ВЫЧИСЛИТЕЛЬНЫЕ МАШИНЫ, КОМПЛЕКСЫ, СИСТЕМЫ и СЕТИ",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),
            addDiscipline(
                name = "ОСНОВЫ ОРГАНИЗАЦИИ и УПРАВЛЕНИЯ в СТРОИТЕЛЬСТВЕ",
                teachers = teachersRepository.findTeacherByLastName("Фокин"),
                rooms = roomsRepository.findRoomByName("ауд. 7", "ауд. 216")
            ),
            addDiscipline(
                name = "ТЕХНОЛОГИЧЕСКИЕ ПРОЦЕССЫ в СТРОИТЕЛЬСТВЕ",
                teachers = teachersRepository.findTeacherByLastName("Баженова"),
                rooms = roomsRepository.findRoomByName("ауд. 216")
            ),
            addDiscipline(
                name = "ДИСКРЕТНАЯ МАТЕМАТИКА и МАТЕМАТИЧЕСКАЯ ЛОГИКА",
                teachers = teachersRepository.findTeacherByLastName("Лабутина"),
                rooms = roomsRepository.findRoomByName("ауд. 32")
            ),
            addDiscipline(
                name = "ПРОГРАММИРОВАНИЕ и АЛГОРИТМИЗАЦИЯ",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),
            addDiscipline(
                name = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                teachers = teachersRepository.findTeacherByLastName("Щербакова"),
                rooms = roomsRepository.findRoomByName("ауд. 28", "ауд. 47", "ауд. 7")
            ),
            addDiscipline(
                name = "МЕТОДЫ СТАТИСТИЧЕСКОГО АНАЛИЗА ДАННЫХ",
                teachers = teachersRepository.findTeacherByLastName("Зайниева"),
                rooms = roomsRepository.findRoomByName("ауд. 7")
            ),


            addDiscipline(
                name = "ЗАЩИТА ИНФОРМАЦИИ",
                teachers = teachersRepository.findTeacherByLastName("Русских"),
                rooms = roomsRepository.findRoomByName("ауд. 7")
            ),
            addDiscipline(
                name = "ТЕОРИЯ АВТОМАТИЧЕСКОГО УПРАВЛЕНИЯ",
                teachers = teachersRepository.findTeacherByLastName("Морозов"),
                rooms = roomsRepository.findRoomByName("ауд. 28", "ауд. 36")
            ),
            addDiscipline(
                name = "ЭЛЕКТРОСНАБЖЕНИЕ",
                teachers = teachersRepository.findTeacherByLastName("Ковязина"),
                rooms = roomsRepository.findRoomByName("ауд. 212")
            ),
            addDiscipline(
                name = "СИЛОВАЯ ЭЛЕКТРОНИКА",
                teachers = teachersRepository.findTeacherByLastName("Ковязина"),
                rooms = roomsRepository.findRoomByName("ауд. 212")
            ),
            addDiscipline(
                name = "МОДЕЛИРОВАНИЕ СИСТЕМ",
                teachers = teachersRepository.findTeacherByLastName("Лабутина"),
                rooms = roomsRepository.findRoomByName("ауд. 7")
            ),
            addDiscipline(
                name = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД",
                teachers = teachersRepository.findTeacherByLastName("Горюшков"),
                rooms = roomsRepository.findRoomByName("ауд. 1", "ауд. 216")
            ),
            addDiscipline(
                name = "ЭЛЕКТРИЧЕСКИЕ СТАНЦИИ и ПОДСТАНЦИИ",
                teachers = teachersRepository.findTeacherByLastName("Ковязин"),
                rooms = roomsRepository.findRoomByName("ауд. 211")
            ),
            addDiscipline(
                name = "МИКРОПРОЦЕССОРНЫЕ СРЕДСТВА АВТОМАТИЗАЦИИ и УПРАВЛЕНИЯ",
                teachers = teachersRepository.findTeacherByLastName("Ковязин"),
                rooms = roomsRepository.findRoomByName("ауд. 211")
            ),
            addDiscipline(
                name = "ПРЕОБРАЗОВАТЕЛЬНЫЕ УСТРОЙСТВА",
                teachers = teachersRepository.findTeacherByLastName("Ковязина"),
                rooms = roomsRepository.findRoomByName("ауд. 212")
            ),
            addDiscipline(
                name = "АДМИНИСТРИРОВНИЕ ОПЕРАЦИОННЫХ СИСТЕМ",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),
            addDiscipline(
                name = "ПРОГРАММИРОВАНИЕ ИНТЕРНЕТ-ПРИЛОЖЕНИЙ",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),
            addDiscipline(
                name = "СЕТИ и ТЕЛЕКОММУНИКАЦИИ",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),
            addDiscipline(
                name = "УПРАВЛЕНИЕ ПРОЕКТАМИ АВТОМАТИЗИРОВАННЫХ СИСТЕМ УПРАВЛЕНИЯ",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),


            addDiscipline(
                name = "АДМИНИСТРИРОВАНИЕ БАЗ ДАННЫХ\n(на примере Oracle)",
                teachers = teachersRepository.findTeacherByLastName("Сухих"),
                rooms = roomsRepository.findRoomByName("ауд. 29")
            ),
            addDiscipline(
                name = "АВТОМАТИЗАЦИЯ ТЕХНОЛОГИЧЕСКИХ ПРОЦЕССОВ и ПРОИЗВОДСТВ",
                teachers = teachersRepository.findTeacherByLastName("Ковязин"),
                rooms = roomsRepository.findRoomByName("ауд. 211")
            ),
            addDiscipline(
                name = "УПРАВЛЕНИЕ КАЧЕСТВОМ",
                teachers = teachersRepository.findTeacherByLastName("Зайниева"),
                rooms = roomsRepository.findRoomByName("ауд. 1", "ауд. 32")
            ),
            addDiscipline(
                name = "ДИАГНОСТИКА и НАДЕЖНОСТЬ АВТОМАТИЗИРОВАННЫХ СИСТЕМ",
                teachers = teachersRepository.findTeacherByLastName("Ковязин"),
                rooms = roomsRepository.findRoomByName("ауд. 211")
            ),
            addDiscipline(
                name = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА",
                teachers = teachersRepository.findTeacherByLastName("Горяева", "Русских"),
                rooms = roomsRepository.findRoomByName("ауд. 7")
            ),
            addDiscipline(
                name = "МЕТРОЛОГИЯ, СТАНДАРТИЗАЦИЯ и СЕРТИФИКАЦИЯ",
                teachers = teachersRepository.findTeacherByLastName("Горяева"),
                rooms = roomsRepository.findRoomByName("ауд. 43")
            ),
            addDiscipline(
                name = "АВТОМАТИЗАЦИЯ ТЕХНОЛОГИЧЕСКИХ ПРОЦЕССОВ и ПРОИЗВОДСТВ",
                teachers = teachersRepository.findTeacherByLastName("Ковязин"),
                rooms = roomsRepository.findRoomByName("ауд. 211")
            ),
            addDiscipline(
                name = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ ПРОИЗОДСТВ",
                teachers = teachersRepository.findTeacherByLastName("Фокин"),
                rooms = roomsRepository.findRoomByName("ауд. 1")
            ),
            addDiscipline(
                name = "РЕЛЕЙНАЯ ЗАЩИТА и АВТОМАТИЗАЦИЯ ЭЛЕКТРОЭНЕРГЕТИЧЕСКИХ СИСТЕМ",
                teachers = teachersRepository.findTeacherByLastName("Ковязин"),
                rooms = roomsRepository.findRoomByName("ауд. 28", "ауд. 211")
            ),
            addDiscipline(
                name = "ЭНЕРГОСБЕРЕЖЕНИЕ и ЭНЕРГОАУДИТ",
                teachers = teachersRepository.findTeacherByLastName("Горяева"),
                rooms = roomsRepository.findRoomByName("ауд. 43")
            ),
            addDiscipline(
                name = "ПРОЕКТИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ СИСТЕМ ОБРАБОТКИ ИНФОРМАЦИИ и УПРАВЛЕНИЯ",
                teachers = teachersRepository.findTeacherByLastName("Русских"),
                rooms = roomsRepository.findRoomByName("ауд. 7")
            ),
            addDiscipline(
                name = "МОДЕЛИРОВАНИЕ СИСТЕМ и ПРОЦЕССОВ",
                teachers = teachersRepository.findTeacherByLastName("Лабутина"),
                rooms = roomsRepository.findRoomByName("ауд. 28")
            ),
            addDiscipline(
                name = "МОДЕЛИРОВАНИЕ СИСТЕМ и ПРОЦЕССОВ",
                teachers = teachersRepository.findTeacherByLastName("Лабутина"),
                rooms = roomsRepository.findRoomByName("ауд. 28")
            ),
            addDiscipline(
                name = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ ПРОИЗВОДСТВ в ЭЛЕТРОЭНЕРГЕТИКЕ и ЭЛЕКТРОТЕХНИКЕ",
                teachers = teachersRepository.findTeacherByLastName("Зайниева"),
                rooms = roomsRepository.findRoomByName("ауд. 1")
            ),
        ).collect()

    }

    context(AcademicPlanRepository, GroupsRepository, DisciplinesRepository)
    suspend fun injectPlan() {
        coroutineScope {
            val a = launch { allPlans.collect { Logger.log("injectPlan $it") } }
            val MOCK_HOURS = 35
            addPlan(
                name = "2021",
                plans = listOf(
                    groupPlan(
                        groupName = "АСУ-21-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ФИЗИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИНОСТРАННЫЙ ЯЗЫК", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИСТОРИЯ", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИНФОРМАТИКА", lecture = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "МАТЕМАТИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ОСНОВЫ АЛГОРИТМИЗАЦИИ и ПРОГАРММИРОВАНИЯ", lecture = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ОСНОВЫ АЛГОРИТМИЗАЦИИ и ПРОГАРММИРОВАНИЯ", laboratory = MOCK_HOURS)
                        )
                    ),
                    groupPlan(
                        groupName = "АТПП-21-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ИНОСТРАННЫЙ ЯЗЫК", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИНФОРМАТИКА", lecture = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИСТОРИЯ", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "МАТЕМАТИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ФИЗИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ИНЖЕНЕРНАЯ ГЕОМЕТРИЯ и КОМПЬЮТЕРНАЯ ГРАФИКА",
                                laboratory = MOCK_HOURS
                            )
                        )
                    ),
                    groupPlan(
                        groupName = "ПГС-21-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ТЕОРЕТИЧЕКАЯ МЕХАНИКА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИНОСТРАННЫЙ ЯЗЫК", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИНФОРМАТИКА", lecture = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИСТОРИЯ", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИНЖЕНЕРНАЯ ГЕОДЕЗИЯ", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "МАТЕМАТИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ФИЗИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                        )
                    ),
                    groupPlan(
                        groupName = "АСУ-20-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ЭКОНОМИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "МЕТОДЫ СТАТИСТИЧЕСКОГО АНАЛИЗА ДАННЫХ", laboratory = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ЭКОЛОГИЯ", lecture = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ОБЪЕКТНО-ОРИЕНТИРОВАННОЕ ПРОГРАММИРОВАНИЕ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ЭКОНОМИКА и БИЗНЕС", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ", lecture = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ДИСКРЕТНАЯ МАТЕМАТИКА и МАТЕМАТИЧЕСКАЯ ЛОГИКА",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                        )
                    ),
                    groupPlan(
                        groupName = "ПГС-20-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ЭКОНОМИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ЭКОЛОГИЯ", lecture = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ЭЛЕКТРОСНАБЖЕНИЕ с ОСНОВАМИ ЭЛЕКТРОТЕХНИКИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ОСНОВЫ ИНЖЕНЕРНОЙ ГЕОЛОГИИ и МЕХАНИКА ГРУНТОВ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ЭКОНОМИКА и БИЗНЕС", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ОСНОВЫ ОРГАНИЗАЦИИ и УПРАВЛЕНИЯ в СТРОИТЕЛЬСТВЕ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ТЕХНОЛОГИЧЕСКИЕ ПРОЦЕССЫ в СТРОИТЕЛЬСТВЕ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                        )
                    ),
                    groupPlan(
                        groupName = "АТПП-20-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan("ЭКОНОМИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ЭКОЛОГИЯ", lecture = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ЭКОНОМИКА и БИЗНЕС", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ТЕОРЕТИЧЕСКИЕ ОСНОВЫ ЭЛЕКТРОТЕХНИКИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ВЫЧИСЛИТЕЛЬНЫЕ МАШИНЫ, КОМПЛЕКСЫ, СИСТЕМЫ и СЕТИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ПРОГРАММИРОВАНИЕ и АЛГОРИТМИЗАЦИЯ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                        )
                    ),
                    groupPlan(
                        groupName = "ЭС-20-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ЭКОНОМИКА", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ЭКОЛОГИЯ", lecture = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ТЕОРЕТИЧЕСКИЕ ОСНОВЫ ЭЛЕКТРОТЕХНИКИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ТЕХНИКА ВЫСОКИХ НАПРЯЖЕНИЙ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                        )
                    ),
                    groupPlan(
                        groupName = "АСУ-19-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "ЗАЩИТА ИНФОРМАЦИИ", lecture = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "МОДЕЛИРОВАНИЕ СИСТЕМ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "АДМИНИСТРИРОВНИЕ ОПЕРАЦИОННЫХ СИСТЕМ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ПРОГРАММИРОВАНИЕ ИНТЕРНЕТ-ПРИЛОЖЕНИЙ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "СЕТИ и ТЕЛЕКОММУНИКАЦИИ", lecture = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "УПРАВЛЕНИЕ ПРОЕКТАМИ АВТОМАТИЗИРОВАННЫХ СИСТЕМ УПРАВЛЕНИЯ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                        )
                    ),
                    groupPlan(
                        groupName = "АТПП-19-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ТЕОРИЯ АВТОМАТИЧЕСКОГО УПРАВЛЕНИЯ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "МИКРОПРОЦЕССОРНЫЕ СРЕДСТВА АВТОМАТИЗАЦИИ и УПРАВЛЕНИЯ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ПРЕОБРАЗОВАТЕЛЬНЫЕ УСТРОЙСТВА",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                        )
                    ),
                    groupPlan(
                        groupName = "ЭС-19-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "ЭЛЕКТРОСНАБЖЕНИЕ", lecture = MOCK_HOURS, practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "СИЛОВАЯ ЭЛЕКТРОНИКА",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ЭЛЕКТРИЧЕСКИЕ СТАНЦИИ и ПОДСТАНЦИИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                        )
                    ),
                    groupPlan(
                        groupName = "АСУ-18-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(
                                disciplineName = "АДМИНИСТРИРОВАНИЕ БАЗ ДАННЫХ\n(на примере Oracle)",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "МЕТРОЛОГИЯ, СТАНДАРТИЗАЦИЯ и СЕРТИФИКАЦИЯ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ПРОЕКТИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ СИСТЕМ ОБРАБОТКИ ИНФОРМАЦИИ и УПРАВЛЕНИЯ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = MOCK_HOURS),
                        )
                    ),
                    groupPlan(
                        groupName = "АТПП-18-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(
                                disciplineName = "АВТОМАТИЗАЦИЯ ТЕХНОЛОГИЧЕСКИХ ПРОЦЕССОВ и ПРОИЗВОДСТВ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ДИАГНОСТИКА и НАДЕЖНОСТЬ АВТОМАТИЗИРОВАННЫХ СИСТЕМ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(disciplineName = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ ПРОИЗОДСТВ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ЭНЕРГОСБЕРЕЖЕНИЕ и ЭНЕРГОАУДИТ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "МОДЕЛИРОВАНИЕ СИСТЕМ и ПРОЦЕССОВ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                        )
                    ),
                    groupPlan(
                        groupName = "ЭС-18-1б-ЧФ",
                        disciplines = listOf(
                            disciplinePlan(disciplineName = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = MOCK_HOURS),
                            disciplinePlan(disciplineName = "УПРАВЛЕНИЕ КАЧЕСТВОМ", lecture = MOCK_HOURS, laboratory = MOCK_HOURS),
                            disciplinePlan(
                                disciplineName = "РЕЛЕЙНАЯ ЗАЩИТА и АВТОМАТИЗАЦИЯ ЭЛЕКТРОЭНЕРГЕТИЧЕСКИХ СИСТЕМ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                                lecture = MOCK_HOURS,
                                laboratory = MOCK_HOURS
                            ),
                            disciplinePlan(
                                disciplineName = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ ПРОИЗВОДСТВ в ЭЛЕТРОЭНЕРГЕТИКЕ и ЭЛЕКТРОТЕХНИКЕ",
                                lecture = MOCK_HOURS,
                                practice = MOCK_HOURS
                            ),
                        )
                    )
                ).awaitAll()
            ).onEach {
                it.onSuccess { validatePlan(it) }
                Logger.log("AddPlanResult $it")
            }.collect()
            delay(100)
            a.cancel()
        }
    }

    private fun validatePlan(academicPlan: AcademicPlan) {
        academicPlan.plans.forEach { groupPlan ->
            groupPlan.items.forEach { disciplinePlan ->
                if (disciplinePlan.teacher.isEmpty) {
                    throw IllegalStateException("Пустой учитель для $disciplinePlan")
                }
                if (disciplinePlan.room.isEmpty) {
                    throw IllegalStateException("Пустой кабинет для $disciplinePlan")
                }
            }
        }
    }

    context (GroupsRepository, CoroutineScope)
    private fun groupPlan(
        groupName: String,
        disciplines: List<Deferred<DisciplinePlan>>
    ): Deferred<GroupPlan> {
        return async {
            val items = disciplines.awaitAll()
            val group = findGroupByName(groupName)
            GroupPlan(group = group, items = items)
        }
    }

    context (DisciplinesRepository, CoroutineScope)
    private fun disciplinePlan(
        disciplineName: String,
        lecture: AcademicHour = 0,
        practice: AcademicHour = 0,
        laboratory: AcademicHour = 0
    ): Deferred<DisciplinePlan> {
        return async {
            val works = mapOf(
                WorkType.LECTURE to lecture,
                WorkType.PRACTICE to practice,
                WorkType.LABORATORY to laboratory,
            )
            val discipline = findDisciplineByName(disciplineName)
            DisciplinePlan(discipline = discipline, works = works)
        }
    }

}

private suspend fun TeachersRepository.findTeacherByLastName(vararg lastname: String): List<Teacher> {
    return allTeachers.first().getOrThrow().filter { lastname.contains(it.lastName) }
}
private suspend fun RoomsRepository.findRoomByName(vararg names: String): List<Room> {
    return allRooms.first().getOrThrow().filter { names.contains(it.name) }
}
private suspend fun GroupsRepository.findGroupByName(name: String): Group {
    return allGroups.first().getOrThrow().first { it.name == name }
}
private suspend fun DisciplinesRepository.findDisciplineByName(name: String): Discipline {
    return allDisciplines.first().getOrThrow().first { it.name == name }
}