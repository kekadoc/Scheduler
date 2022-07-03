package injector.impl

import app.data.repository.discipline.DisciplinesRepository
import app.data.repository.group.GroupsRepository
import app.data.repository.room.RoomsRepository
import app.data.repository.space.SpacesRepository
import app.data.repository.teacher.TeachersRepository
import app.domain.model.*
import app.schedule.plan.AcademicPlan
import app.schedule.plan.GroupPlan
import common.logger.Logger
import injector.DataInjector
import injector.DataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class BasePnipuInjector : DataInjector {

    override val id: Long = 777L

    override val type: DataInjector.Type = DataInjector.Type.EVERY_LAUNCH


    override suspend fun getSpace(spaces: SpacesRepository): Space {
        return spaces.addSpace("ПНИПУ").first().getOrThrow()
    }

    override suspend fun inject(data: DataRepository) {
        val (teachers, rooms, disciplines, groups, academicPlan) = data
        teachers.clear().collect()
        rooms.clear().collect()
        disciplines.clear().collect()
        groups.clear().collect()

        Logger.log("inject")
        injectRooms(rooms)
        Logger.log("injectRooms")
        injectTeachers(teachers)
        Logger.log("injectTeachers")
        injectGroups(groups)
        Logger.log("injectGroups")
        injectDisciplines(disciplines, teachers, rooms)
        Logger.log("injectDisciplines")
        injectPlan(academicPlan = academicPlan.plan, groupsRepository = groups, disciplinesRepository = disciplines)
        Logger.log("injectPlan")
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
            addTeacher(lastName = "Ковязин", firstName = "В.", middleName = "А.", speciality = "канд.техн.наук, доцент "),
            addTeacher(lastName = "Лабутина", firstName = "Т.", middleName = "В.", speciality = "старший преподаватель "),
            addTeacher(lastName = "Шергина", firstName = "М.", middleName = "А.", speciality = " старший преподаватель"),
            addTeacher(lastName = "Трвников", firstName = "А.", middleName = "В.", speciality = " канд.техн.наук, доцент"),
            addTeacher(lastName = "Горяева", firstName = "И.", middleName = "А.", speciality = "канд.экон.наук, доцент"),
            addTeacher(lastName = "Баженова", firstName = "Т.", middleName = "Р.", speciality = "старший преподаватель"),
            addTeacher(lastName = "Цигвинцева", firstName = "Г.", middleName = "Л.", speciality = "канд.филос.наук, доцент"),
            addTeacher(lastName = "Куликов", firstName = "Н.", middleName = "М.", speciality = "канд.пед.наук, доцент"),
            addTeacher(lastName = "Красильников", firstName = "С.", middleName = "М.", speciality = "канд.техн.наук, доцент"),
            addTeacher(lastName = "Горюшков", firstName = "Г.", middleName = "А.", speciality = "старший преподаватель")
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

    suspend fun injectPlan(
        academicPlan: AcademicPlan,
        groupsRepository: GroupsRepository,
        disciplinesRepository: DisciplinesRepository
    ) {
        Logger.log("injectPlan START $academicPlan")
        with(academicPlan) {
            with(groupsRepository) {
                with(disciplinesRepository) {
                    coroutineScope {
                        var i = 0
                        listOf(
                            addToAcademicPlan(
                                groupName = "АСУ-21-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ФИЗИКА", lecture = 12, practice = 12, laboratory = 12),
                                    teaching(disciplineName = "ИНОСТРАННЫЙ ЯЗЫК", practice = 12),
                                    teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                                    teaching(disciplineName = "ИСТОРИЯ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ИНФОРМАТИКА", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(disciplineName = "МАТЕМАТИКА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ОСНОВЫ АЛГОРИТМИЗАЦИИ и ПРОГАРММИРОВАНИЯ", lecture = 12),
                                    teaching(disciplineName = "ОСНОВЫ АЛГОРИТМИЗАЦИИ и ПРОГАРММИРОВАНИЯ", laboratory = 12)
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "АТПП-21-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ИНОСТРАННЫЙ ЯЗЫК", practice = 12),
                                    teaching(disciplineName = "ИНФОРМАТИКА", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ИСТОРИЯ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(disciplineName = "МАТЕМАТИКА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                                    teaching(disciplineName = "ФИЗИКА", lecture = 12, practice = 12, laboratory = 12),
                                    teaching(disciplineName = "ИНЖЕНЕРНАЯ ГЕОМЕТРИЯ и КОМПЬЮТЕРНАЯ ГРАФИКА", laboratory = 12)
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "ПГС-21-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ТЕОРЕТИЧЕКАЯ МЕХАНИКА", practice = 12),
                                    teaching(disciplineName = "ИНОСТРАННЫЙ ЯЗЫК", practice = 12),
                                    teaching(disciplineName = "ИНФОРМАТИКА", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ИСТОРИЯ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ИНЖЕНЕРНАЯ ГЕОДЕЗИЯ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                                    teaching(disciplineName = "МАТЕМАТИКА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ФИЗИКА", lecture = 12, practice = 12, laboratory = 12),
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "АСУ-20-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ЭКОНОМИКА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                                    teaching(disciplineName = "МЕТОДЫ СТАТИСТИЧЕСКОГО АНАЛИЗА ДАННЫХ", laboratory = 12),
                                    teaching(disciplineName = "ЭКОЛОГИЯ", lecture = 12),
                                    teaching(disciplineName = "ОБЪЕКТНО-ОРИЕНТИРОВАННОЕ ПРОГРАММИРОВАНИЕ", lecture = 12, laboratory = 12, practice = 12),
                                    teaching(disciplineName = "ЭКОНОМИКА и БИЗНЕС", practice = 12),
                                    teaching(disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ", lecture = 12),
                                    teaching(disciplineName = "ДИСКРЕТНАЯ МАТЕМАТИКА и МАТЕМАТИЧЕСКАЯ ЛОГИКА", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ", lecture = 12, laboratory = 12),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "ПГС-20-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ЭКОНОМИКА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ЭКОЛОГИЯ", lecture = 12),
                                    teaching(disciplineName = "ЭЛЕКТРОСНАБЖЕНИЕ с ОСНОВАМИ ЭЛЕКТРОТЕХНИКИ", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ОСНОВЫ ИНЖЕНЕРНОЙ ГЕОЛОГИИ и МЕХАНИКА ГРУНТОВ", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ЭКОНОМИКА и БИЗНЕС", practice = 12),
                                    teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ОСНОВЫ ОРГАНИЗАЦИИ и УПРАВЛЕНИЯ в СТРОИТЕЛЬСТВЕ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ТЕХНОЛОГИЧЕСКИЕ ПРОЦЕССЫ в СТРОИТЕЛЬСТВЕ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "АТПП-20-1б-ЧФ",
                                plan = listOf(
                                    teaching("ЭКОНОМИКА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ЭКОЛОГИЯ", lecture = 12),
                                    teaching(disciplineName = "ЭКОНОМИКА и БИЗНЕС", practice = 12),
                                    teaching(disciplineName = "ТЕОРЕТИЧЕСКИЕ ОСНОВЫ ЭЛЕКТРОТЕХНИКИ", lecture = 12, laboratory = 12, practice = 12),
                                    teaching(disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ВЫЧИСЛИТЕЛЬНЫЕ МАШИНЫ, КОМПЛЕКСЫ, СИСТЕМЫ и СЕТИ", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ПРОГРАММИРОВАНИЕ и АЛГОРИТМИЗАЦИЯ", lecture = 12, laboratory = 12),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "ЭС-20-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ЭКОНОМИКА", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ЭКОЛОГИЯ", lecture = 12),
                                    teaching(
                                        disciplineName = "ТЕОРЕТИЧЕСКИЕ ОСНОВЫ ЭЛЕКТРОТЕХНИКИ",
                                        lecture = 12,
                                        laboratory = 12
                                    ),
                                    teaching(
                                        disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ",
                                        lecture = 12,
                                        practice = 12
                                    ),
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(
                                        disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА",
                                        lecture = 12,
                                        practice = 12
                                    ),
                                    teaching(
                                        disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                                        lecture = 12,
                                        laboratory = 12
                                    ),
                                    teaching(
                                        disciplineName = "ТЕХНИКА ВЫСОКИХ НАПРЯЖЕНИЙ",
                                        lecture = 12,
                                        laboratory = 12
                                    ),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "АСУ-19-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(disciplineName = "ЗАЩИТА ИНФОРМАЦИИ", lecture = 12, laboratory = 12),
                                    teaching(
                                        disciplineName = "МОДЕЛИРОВАНИЕ СИСТЕМ",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                    teaching(
                                        disciplineName = "АДМИНИСТРИРОВНИЕ ОПЕРАЦИОННЫХ СИСТЕМ",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                    teaching(
                                        disciplineName = "ПРОГРАММИРОВАНИЕ ИНТЕРНЕТ-ПРИЛОЖЕНИЙ",
                                        lecture = 12,
                                        laboratory = 12
                                    ),
                                    teaching(disciplineName = "СЕТИ и ТЕЛЕКОММУНИКАЦИИ", lecture = 12, laboratory = 12),
                                    teaching(
                                        disciplineName = "УПРАВЛЕНИЕ ПРОЕКТАМИ АВТОМАТИЗИРОВАННЫХ СИСТЕМ УПРАВЛЕНИЯ",
                                        lecture = 12,
                                        laboratory = 12
                                    ),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "АТПП-19-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(
                                        disciplineName = "ТЕОРИЯ АВТОМАТИЧЕСКОГО УПРАВЛЕНИЯ",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                    teaching(disciplineName = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД", lecture = 12, practice = 12),
                                    teaching(
                                        disciplineName = "МИКРОПРОЦЕССОРНЫЕ СРЕДСТВА АВТОМАТИЗАЦИИ и УПРАВЛЕНИЯ",
                                        lecture = 12,
                                        laboratory = 12
                                    ),
                                    teaching(
                                        disciplineName = "ПРЕОБРАЗОВАТЕЛЬНЫЕ УСТРОЙСТВА",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "ЭС-19-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                                    teaching(
                                        disciplineName = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД",
                                        lecture = 12,
                                        practice = 12,
                                        laboratory = 12
                                    ),
                                    teaching(disciplineName = "ЭЛЕКТРОСНАБЖЕНИЕ", lecture = 12, practice = 12),
                                    teaching(
                                        disciplineName = "СИЛОВАЯ ЭЛЕКТРОНИКА",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                    teaching(
                                        disciplineName = "ЭЛЕКТРИЧЕСКИЕ СТАНЦИИ и ПОДСТАНЦИИ",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "АСУ-18-1б-ЧФ",
                                plan = listOf(
                                    teaching(
                                        disciplineName = "АДМИНИСТРИРОВАНИЕ БАЗ ДАННЫХ\n(на примере Oracle)",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                    teaching(
                                        disciplineName = "МЕТРОЛОГИЯ, СТАНДАРТИЗАЦИЯ и СЕРТИФИКАЦИЯ",
                                        lecture = 12,
                                        laboratory = 12,
                                        practice = 12
                                    ),
                                    teaching(
                                        disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
                                        lecture = 12,
                                        laboratory = 12
                                    ),
                                    teaching(
                                        disciplineName = "ПРОЕКТИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ СИСТЕМ ОБРАБОТКИ ИНФОРМАЦИИ и УПРАВЛЕНИЯ",
                                        lecture = 12,
                                        practice = 12,
                                        laboratory = 12
                                    ),
                                    teaching(disciplineName = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "АТПП-18-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "АВТОМАТИЗАЦИЯ ТЕХНОЛОГИЧЕСКИХ ПРОЦЕССОВ и ПРОИЗВОДСТВ", lecture = 12, practice = 12, laboratory = 12),
                                    teaching(disciplineName = "ДИАГНОСТИКА и НАДЕЖНОСТЬ АВТОМАТИЗИРОВАННЫХ СИСТЕМ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                                    teaching(disciplineName = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ ПРОИЗОДСТВ", lecture = 12, practice = 12),
                                    teaching(disciplineName = "ЭНЕРГОСБЕРЕЖЕНИЕ и ЭНЕРГОАУДИТ", lecture = 12, practice = 12, laboratory = 12),
                                    teaching(disciplineName = "МОДЕЛИРОВАНИЕ СИСТЕМ и ПРОЦЕССОВ", lecture = 12, practice = 12),
                                )
                            ),
                            addToAcademicPlan(
                                groupName = "ЭС-18-1б-ЧФ",
                                plan = listOf(
                                    teaching(disciplineName = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                                    teaching(disciplineName = "УПРАВЛЕНИЕ КАЧЕСТВОМ", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "РЕЛЕЙНАЯ ЗАЩИТА и АВТОМАТИЗАЦИЯ ЭЛЕКТРОЭНЕРГЕТИЧЕСКИХ СИСТЕМ", lecture = 12, laboratory = 12, practice = 12),
                                    teaching(disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ", lecture = 12, laboratory = 12),
                                    teaching(disciplineName = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ ПРОИЗВОДСТВ в ЭЛЕТРОЭНЕРГЕТИКЕ и ЭЛЕКТРОТЕХНИКЕ", lecture = 12, practice = 12),
                                )
                            )
                        ).apply { Logger.log("COUNT=${count()}") }.merge().onEach { Logger.log("Each $i") }.collect { Logger.log("COLLECT ${i++}") }
                        Logger.log("COMPLETE")
                    }
                }
            }
        }
        Logger.log("injectPlan FINISH $academicPlan ${academicPlan.getAll()}")
    }

    context (GroupsRepository, AcademicPlan)
    private fun addToAcademicPlan(groupName: String, plan: List<Deferred<Pair<Discipline, Map<WorkType, AcademicHour>>>>): Flow<Unit> {
        return flow {
            Logger.log("___addToAcademicPlan groupName=$groupName")
            val group = findGroupByName(groupName)
            Logger.log("___addToAcademicPlan plan=${plan.count()}")
            val plans = plan.awaitAll()
            Logger.log("___addToAcademicPlan plans=${plans.count()}")
            val groupPlan = GroupPlan(group).apply {
                plans.forEach { (discipline, works) ->
                    works.forEach { (workType, hours) ->
                        this.set(discipline, workType, hours)
                    }
                }
            }
            set(group, groupPlan)
            emit(Unit)
        }
    }


    context (DisciplinesRepository, CoroutineScope)
    private fun teaching(
        disciplineName: String,
        lecture: AcademicHour = 0,
        practice: AcademicHour = 0,
        laboratory: AcademicHour = 0
    ): Deferred<Pair<Discipline, Map<WorkType, AcademicHour>>> {
        return async {
            Logger.log("teaching 0 $disciplineName")
            val works = mapOf(
                WorkType.LECTURE to lecture,
                WorkType.PRACTICE to practice,
                WorkType.LABORATORY to laboratory,
            )
            val discipline = findDisciplineByName(disciplineName)
            Logger.log("teaching 1 $disciplineName")
            discipline to works
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
    Logger.log("findDisciplineByName $name")
    return allDisciplines.first()
        .onSuccess { Logger.log("findDisciplineByName success $it") }
        .onFailure { Logger.log("findDisciplineByName failed $it") }
        .getOrThrow().first { it.name == name }
}