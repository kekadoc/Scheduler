package injector

import common.extensions.await
import data.repository.discipline.DisciplineRepository
import data.repository.group.GroupRepository
import data.repository.room.RoomRepository
import data.repository.teacher.TeachersRepository
import domain.model.*
import kotlinx.coroutines.flow.first
import schedule.plan.AcademicPlan
import schedule.plan.GroupPlan

class Inject(
    private val disciplineRepository: DisciplineRepository,
    private val teachersRepository: TeachersRepository,
    private val roomRepository: RoomRepository,
    private val groupRepository: GroupRepository,
    private val academicPlan: AcademicPlan
) {

    private var teachingId = 1L


    suspend fun injectRooms(): Unit {
        with(roomRepository) {
            addRoom("ауд. 1").await()
            addRoom("ауд. 2").await()
            addRoom("ауд. 7").await()
            addRoom("ауд. 28").await()
            addRoom("ауд. 29").await()
            addRoom("ауд. 32").await()
            addRoom("ауд. 37").await()
            addRoom("ауд. 40").await()
            addRoom("ауд. 43").await()
            addRoom("ауд. 47").await()
            addRoom("ауд. 216").await()
            addRoom("ауд. 211").await()
            addRoom("ауд. 213").await()
            addRoom("спортзал").await()
        }
    }
    suspend fun injectTeachers(): Unit = with(teachersRepository) {
        addTeacher(lastName = "Морозов", firstName = "Е.", middleName = "А.", speciality = "д-р техн.наук, профессор").await()
        addTeacher(lastName = "Морзова", firstName = "А.", middleName = "Р.", speciality = "канд.техн.наук, доцент").await()
        addTeacher(lastName = "Сметанина", firstName = "Е.", middleName = "В.", speciality = "старший преподаватель").await()
        addTeacher(lastName = "Германюк", firstName = "Г.", middleName = "Ю.", speciality = "канд.физ.-мат.наук,доцент").await()
        addTeacher(lastName = "Фокин", firstName = "В.", middleName = "Я.", speciality = "канд.экон.наук, доцент").await()
        addTeacher(lastName = "Русских", firstName = "Т.", middleName = "И.", speciality = "канд.пед.наук, доцент").await()
        addTeacher(lastName = "Сухих", firstName = "И.", middleName = "И.", speciality = "старший преподаватель").await()
        addTeacher(lastName = "Зайниева", firstName = "Т.", middleName = "В.", speciality = "канд.экон.наук, доцент").await()
        addTeacher(lastName = "Ковязин", firstName = "В.", middleName = "А.", speciality = "канд.техн.наук, доцент ").await()
        addTeacher(lastName = "Лабутина", firstName = "Т.", middleName = "В.", speciality = "старший преподаватель ").await()
        addTeacher(lastName = "Шергина", firstName = "М.", middleName = "А.", speciality = " старший преподаватель").await()
        addTeacher(lastName = "Трвников", firstName = "А.", middleName = "В.", speciality = " канд.техн.наук, доцент").await()
        addTeacher(lastName = "Горяева", firstName = "И.", middleName = "А.", speciality = "канд.экон.наук, доцент").await()
        addTeacher(lastName = "Баженова", firstName = "Т.", middleName = "Р.", speciality = "старший преподаватель").await()
        addTeacher(lastName = "Цигвинцева", firstName = "Г.", middleName = "Л.", speciality = "канд.филос.наук, доцент").await()
        addTeacher(lastName = "Куликов", firstName = "Н.", middleName = "М.", speciality = "канд.пед.наук, доцент").await()
        addTeacher(lastName = "Красильников", firstName = "С.", middleName = "М.", speciality = "канд.техн.наук, доцент").await()
        addTeacher(lastName = "Горюшков", firstName = "Г.", middleName = "А.", speciality = "старший преподаватель").await()
    }
    suspend fun injectDisciplines(): Unit {
        disciplineRepository.addDiscipline(
            name = "ФИЗИКА",
            teachers = teachersRepository.findByLastName("Морозов"),
            rooms = roomRepository.findByName("ауд. 28", "ауд. 1")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ИНОСТРАННЫЙ ЯЗЫК",
            teachers = teachersRepository.findByLastName("Сметанина"),
            rooms = roomRepository.findByName("ауд. 37")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ТЕОРЕТИЧЕКАЯ МЕХАНИКА",
            teachers = teachersRepository.findByLastName("Германюк"),
            rooms = roomRepository.findByName("ауд. 47")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ИНФОРМАТИКА",
            teachers = teachersRepository.findByLastName("Лабутина"),
            rooms = roomRepository.findByName("ауд. 32", "ауд. 7")
        ).await()
        disciplineRepository.addDiscipline(
            name = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА",
            teachers = teachersRepository.findByLastName("Русских", "Баженова"),
            rooms = roomRepository.findByName("ауд. 28")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ИСТОРИЯ",
            teachers = teachersRepository.findByLastName("Цигвинцева"),
            rooms = roomRepository.findByName("ауд. 40")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА",
            teachers = teachersRepository.findByLastName("Куликов"),
            rooms = roomRepository.findByName("спортзал")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ИНЖЕНЕРНАЯ ГЕОДЕЗИЯ",
            teachers = teachersRepository.findByLastName("Фокин"),
            rooms = roomRepository.findByName("ауд. 216")
        ).await()
        disciplineRepository.addDiscipline(
            name = "МАТЕМАТИКА",
            teachers = teachersRepository.findByLastName("Морзова"),
            rooms = roomRepository.findByName("ауд. 32", "ауд. 47")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ИНЖЕНЕРНАЯ ГЕОМЕТРИЯ и КОМПЬЮТЕРНАЯ ГРАФИКА",
            teachers = teachersRepository.findByLastName("Красильников"),
            rooms = roomRepository.findByName("ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ОСНОВЫ АЛГОРИТМИЗАЦИИ и ПРОГАРММИРОВАНИЯ",
            teachers = teachersRepository.findByLastName("Лабутина"),
            rooms = roomRepository.findByName("ауд. 7")
        ).await()



        disciplineRepository.addDiscipline(
            name = "ЭКОНОМИКА",
            teachers = teachersRepository.findByLastName("Фокин"),
            rooms = roomRepository.findByName("ауд. 1", "ауд. 47")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ЭЛЕКТРОСНАБЖЕНИЕ с ОСНОВАМИ ЭЛЕКТРОТЕХНИКИ",
            teachers = teachersRepository.findByLastName("Шергина"),
            rooms = roomRepository.findByName("ауд. 213")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ТЕХНИКА ВЫСОКИХ НАПРЯЖЕНИЙ",
            teachers = teachersRepository.findByLastName("Ковязин"),
            rooms = roomRepository.findByName("ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ЭКОЛОГИЯ",
            teachers = teachersRepository.findByLastName("Травников"),
            rooms = roomRepository.findByName("ауд. 47")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ОБЪЕКТНО-ОРИЕНТИРОВАННОЕ ПРОГРАММИРОВАНИЕ",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ОСНОВЫ ИНЖЕНЕРНОЙ ГЕОЛОГИИ и МЕХАНИКА ГРУНТОВ",
            teachers = teachersRepository.findByLastName("Баженова"),
            rooms = roomRepository.findByName("ауд. 216")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ЭКОНОМИКА и БИЗНЕС",
            teachers = teachersRepository.findByLastName("Зайниева"),
            rooms = roomRepository.findByName("ауд. 47", "ауд. 1")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ТЕОРЕТИЧЕСКИЕ ОСНОВЫ ЭЛЕКТРОТЕХНИКИ",
            teachers = teachersRepository.findByLastName("Шергина"),
            rooms = roomRepository.findByName("ауд. 213")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ",
            teachers = teachersRepository.findByLastName("Русских"),
            rooms = roomRepository.findByName("ауд. 28", "ауд. 47")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ВЫЧИСЛИТЕЛЬНЫЕ МАШИНЫ, КОМПЛЕКСЫ, СИСТЕМЫ и СЕТИ",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ОСНОВЫ ОРГАНИЗАЦИИ и УПРАВЛЕНИЯ в СТРОИТЕЛЬСТВЕ",
            teachers = teachersRepository.findByLastName("Фокин"),
            rooms = roomRepository.findByName("ауд. 7", "ауд. 216")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ТЕХНОЛОГИЧЕСКИЕ ПРОЦЕССЫ в СТРОИТЕЛЬСТВЕ",
            teachers = teachersRepository.findByLastName("Баженова"),
            rooms = roomRepository.findByName("ауд. 216")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ДИСКРЕТНАЯ МАТЕМАТИКА и МАТЕМАТИЧЕСКАЯ ЛОГИКА",
            teachers = teachersRepository.findByLastName("Лабутина"),
            rooms = roomRepository.findByName("ауд. 32")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ПРОГРАММИРОВАНИЕ и АЛГОРИТМИЗАЦИЯ",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()
        disciplineRepository.addDiscipline(
            name = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ",
            teachers = teachersRepository.findByLastName("Щербакова"),
            rooms = roomRepository.findByName("ауд. 28", "ауд. 47", "ауд. 7")
        ).await()
        disciplineRepository.addDiscipline(
            name = "МЕТОДЫ СТАТИСТИЧЕСКОГО АНАЛИЗА ДАННЫХ",
            teachers = teachersRepository.findByLastName("Зайниева"),
            rooms = roomRepository.findByName("ауд. 7")
        ).await()


        disciplineRepository.addDiscipline(
            name = "ЗАЩИТА ИНФОРМАЦИИ",
            teachers = teachersRepository.findByLastName("Русских"),
            rooms = roomRepository.findByName("ауд. 7")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ТЕОРИЯ АВТОМАТИЧЕСКОГО УПРАВЛЕНИЯ",
            teachers = teachersRepository.findByLastName("Морозов"),
            rooms = roomRepository.findByName("ауд. 28", "ауд. 36")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ЭЛЕКТРОСНАБЖЕНИЕ",
            teachers = teachersRepository.findByLastName("Ковязина"),
            rooms = roomRepository.findByName("ауд. 212")
        ).await()
        disciplineRepository.addDiscipline(
            name = "СИЛОВАЯ ЭЛЕКТРОНИКА",
            teachers = teachersRepository.findByLastName("Ковязина"),
            rooms = roomRepository.findByName("ауд. 212")
        ).await()
        disciplineRepository.addDiscipline(
            name = "МОДЕЛИРОВАНИЕ СИСТЕМ",
            teachers = teachersRepository.findByLastName("Лабутина"),
            rooms = roomRepository.findByName("ауд. 7")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД",
            teachers = teachersRepository.findByLastName("Горюшков"),
            rooms = roomRepository.findByName("ауд. 1", "ауд. 216")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ЭЛЕКТРИЧЕСКИЕ СТАНЦИИ и ПОДСТАНЦИИ",
            teachers = teachersRepository.findByLastName("Ковязин"),
            rooms = roomRepository.findByName("ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "МИКРОПРОЦЕССОРНЫЕ СРЕДСТВА АВТОМАТИЗАЦИИ и УПРАВЛЕНИЯ",
            teachers = teachersRepository.findByLastName("Ковязин"),
            rooms = roomRepository.findByName("ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ПРЕОБРАЗОВАТЕЛЬНЫЕ УСТРОЙСТВА",
            teachers = teachersRepository.findByLastName("Ковязина"),
            rooms = roomRepository.findByName("ауд. 212")
        ).await()
        disciplineRepository.addDiscipline(
            name = "АДМИНИСТРИРОВНИЕ ОПЕРАЦИОННЫХ СИСТЕМ",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ПРОГРАММИРОВАНИЕ ИНТЕРНЕТ-ПРИЛОЖЕНИЙ",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()
        disciplineRepository.addDiscipline(
            name = "СЕТИ и ТЕЛЕКОММУНИКАЦИИ",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()
        disciplineRepository.addDiscipline(
            name = "УПРАВЛЕНИЕ ПРОЕКТАМИ АВТОМАТИЗИРОВАННЫХ СИСТЕМ УПРАВЛЕНИЯ",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()


        disciplineRepository.addDiscipline(
            name = "АДМИНИСТРИРОВАНИЕ БАЗ ДАННЫХ\n(на примере Oracle)",
            teachers = teachersRepository.findByLastName("Сухих"),
            rooms = roomRepository.findByName("ауд. 29")
        ).await()
        disciplineRepository.addDiscipline(
            name = "АВТОМАТИЗАЦИЯ ТЕХНОЛОГИЧЕСКИХ ПРОЦЕССОВ и ПРОИЗВОДСТВ",
            teachers = teachersRepository.findByLastName("Ковязин"),
            rooms = roomRepository.findByName("ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "УПРАВЛЕНИЕ КАЧЕСТВОМ",
            teachers = teachersRepository.findByLastName("Зайниева"),
            rooms = roomRepository.findByName("ауд. 1", "ауд. 32")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ДИАГНОСТИКА и НАДЕЖНОСТЬ АВТОМАТИЗИРОВАННЫХ СИСТЕМ",
            teachers = teachersRepository.findByLastName("Ковязин"),
            rooms = roomRepository.findByName("ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКА РАБОТА",
            teachers = teachersRepository.findByLastName("Горяева", "Русских"),
            rooms = roomRepository.findByName("ауд. 7")
        ).await()
        disciplineRepository.addDiscipline(
            name = "МЕТРОЛОГИЯ, СТАНДАРТИЗАЦИЯ и СЕРТИФИКАЦИЯ",
            teachers = teachersRepository.findByLastName("Горяева"),
            rooms = roomRepository.findByName("ауд. 43")
        ).await()
        disciplineRepository.addDiscipline(
            name = "АВТОМАТИЗАЦИЯ ТЕХНОЛОГИЧЕСКИХ ПРОЦЕССОВ и ПРОИЗВОДСТВ",
            teachers = teachersRepository.findByLastName("Ковязин"),
            rooms = roomRepository.findByName("ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ ПРОИЗОДСТВ",
            teachers = teachersRepository.findByLastName("Фокин"),
            rooms = roomRepository.findByName("ауд. 1")
        ).await()
        disciplineRepository.addDiscipline(
            name = "РЕЛЕЙНАЯ ЗАЩИТА и АВТОМАТИЗАЦИЯ ЭЛЕКТРОЭНЕРГЕТИЧЕСКИХ СИСТЕМ",
            teachers = teachersRepository.findByLastName("Ковязин"),
            rooms = roomRepository.findByName("ауд. 28", "ауд. 211")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ЭНЕРГОСБЕРЕЖЕНИЕ и ЭНЕРГОАУДИТ",
            teachers = teachersRepository.findByLastName("Горяева"),
            rooms = roomRepository.findByName("ауд. 43")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ПРОЕКТИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ СИСТЕМ ОБРАБОТКИ ИНФОРМАЦИИ и УПРАВЛЕНИЯ",
            teachers = teachersRepository.findByLastName("Русских"),
            rooms = roomRepository.findByName("ауд. 7")
        ).await()
        disciplineRepository.addDiscipline(
            name = "МОДЕЛИРОВАНИЕ СИСТЕМ и ПРОЦЕССОВ",
            teachers = teachersRepository.findByLastName("Лабутина"),
            rooms = roomRepository.findByName("ауд. 28")
        ).await()
        disciplineRepository.addDiscipline(
            name = "МОДЕЛИРОВАНИЕ СИСТЕМ и ПРОЦЕССОВ",
            teachers = teachersRepository.findByLastName("Лабутина"),
            rooms = roomRepository.findByName("ауд. 28")
        ).await()
        disciplineRepository.addDiscipline(
            name = "ОРГАНИЗАЦИЯ и ПЛАНИРОВАНИЕ ПРОИЗВОДСТВ в ЭЛЕТРОЭНЕРГЕТИКЕ и ЭЛЕКТРОТЕХНИКЕ",
            teachers = teachersRepository.findByLastName("Зайниева"),
            rooms = roomRepository.findByName("ауд. 1")
        ).await()
    }
    suspend fun injectGroups(): Unit = with(groupRepository) {
        addGroup("АСУ-21-1б-ЧФ").await()
        addGroup("АТПП-21-1б-ЧФ").await()
        addGroup("ПГС-21-1б-ЧФ").await()
        addGroup("АСУ-20-1б-ЧФ").await()
        addGroup("ПГС-20-1б-ЧФ").await()
        addGroup("АТПП-20-1б-ЧФ").await()
        addGroup("ЭС-20-1б-ЧФ").await()
        addGroup("АСУ-19-1б-ЧФ").await()
        addGroup("АТПП-19-1б-ЧФ").await()
        addGroup("ЭС-19-1б-ЧФ").await()
        addGroup("АСУ-18-1б-ЧФ").await()
        addGroup("АТПП-18-1б-ЧФ").await()
        addGroup("ЭС-18-1б-ЧФ").await()
    }

    suspend fun injectPlan(): Unit {

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
        )
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
        )
        addToAcademicPlan(
            groupName = "ПГС-21-1б-ЧФ",
            plan = listOf(
                teaching("ТЕОРЕТИЧЕКАЯ МЕХАНИКА", practice = 12),
                teaching(disciplineName = "ИНОСТРАННЫЙ ЯЗЫК", practice = 12),
                teaching(disciplineName = "ИНФОРМАТИКА", lecture = 12, laboratory = 12),
                teaching(disciplineName = "ИСТОРИЯ", lecture = 12, practice = 12),
                teaching(disciplineName = "ИНЖЕНЕРНАЯ ГЕОДЕЗИЯ", lecture = 12, practice = 12),
                teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
                teaching(disciplineName = "МАТЕМАТИКА", lecture = 12, practice = 12),
                teaching(disciplineName = "ФИЗИКА", lecture = 12, practice = 12, laboratory = 12),
                teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
            )
        )
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
        )
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
        )
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
        )
        addToAcademicPlan(
            groupName = "ЭС-20-1б-ЧФ",
            plan = listOf(
                teaching(disciplineName = "ЭКОНОМИКА", lecture = 12, practice = 12),
                teaching(disciplineName = "ЭКОЛОГИЯ", lecture = 12),
                teaching(disciplineName = "ТЕОРЕТИЧЕСКИЕ ОСНОВЫ ЭЛЕКТРОТЕХНИКИ", lecture = 12, laboratory = 12),
                teaching(disciplineName = "ИНФОРМАТИКА в ПРИЛОЖЕНИИ к ОТРАСЛИ", lecture = 12, practice = 12),
                teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                teaching(disciplineName = "УЧЕБНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", lecture = 12, practice = 12),
                teaching(disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ", lecture = 12, laboratory = 12),
                teaching(disciplineName = "ТЕХНИКА ВЫСОКИХ НАПРЯЖЕНИЙ", lecture = 12, laboratory = 12),
            )
        )
        addToAcademicPlan(
            groupName = "АСУ-19-1б-ЧФ",
            plan = listOf(
                teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                teaching(disciplineName = "ЗАЩИТА ИНФОРМАЦИИ", lecture = 12, laboratory = 12),
                teaching(disciplineName = "МОДЕЛИРОВАНИЕ СИСТЕМ", lecture = 12, laboratory = 12, practice = 12),
                teaching(disciplineName = "АДМИНИСТРИРОВНИЕ ОПЕРАЦИОННЫХ СИСТЕМ", lecture = 12, laboratory = 12, practice = 12),
                teaching(disciplineName = "ПРОГРАММИРОВАНИЕ ИНТЕРНЕТ-ПРИЛОЖЕНИЙ", lecture = 12, laboratory = 12),
                teaching(disciplineName = "СЕТИ и ТЕЛЕКОММУНИКАЦИИ", lecture = 12, laboratory = 12),
                teaching(disciplineName = "УПРАВЛЕНИЕ ПРОЕКТАМИ АВТОМАТИЗИРОВАННЫХ СИСТЕМ УПРАВЛЕНИЯ", lecture = 12, laboratory = 12),
            )
        )
        addToAcademicPlan(
            groupName = "АТПП-19-1б-ЧФ",
            plan = listOf(
                teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                teaching(disciplineName = "ТЕОРИЯ АВТОМАТИЧЕСКОГО УПРАВЛЕНИЯ", lecture = 12, laboratory = 12, practice = 12),
                teaching(disciplineName = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД", lecture = 12, practice = 12),
                teaching(disciplineName = "МИКРОПРОЦЕССОРНЫЕ СРЕДСТВА АВТОМАТИЗАЦИИ и УПРАВЛЕНИЯ", lecture = 12, laboratory = 12),
                teaching(disciplineName = "ПРЕОБРАЗОВАТЕЛЬНЫЕ УСТРОЙСТВА", lecture = 12, laboratory = 12, practice = 12),
            )
        )
        addToAcademicPlan(
            groupName = "ЭС-19-1б-ЧФ",
            plan = listOf(
                teaching(disciplineName = "ПРИКЛАДНАЯ ФИЗИЧЕСКАЯ КУЛЬТУРА", practice = 12),
                teaching(disciplineName = "ЭЛЕКТРИЧЕСКИЙ ПРИВОД", lecture = 12, practice = 12, laboratory = 12),
                teaching(disciplineName = "ЭЛЕКТРОСНАБЖЕНИЕ", lecture = 12, practice = 12),
                teaching(disciplineName = "СИЛОВАЯ ЭЛЕКТРОНИКА", lecture = 12, laboratory = 12, practice = 12),
                teaching(disciplineName = "ЭЛЕКТРИЧЕСКИЕ СТАНЦИИ и ПОДСТАНЦИИ", lecture = 12, laboratory = 12, practice = 12),
            )
        )
        addToAcademicPlan(
            groupName = "АСУ-18-1б-ЧФ",
            plan = listOf(
                teaching(disciplineName = "АДМИНИСТРИРОВАНИЕ БАЗ ДАННЫХ\n(на примере Oracle)", lecture = 12, laboratory = 12, practice = 12),
                teaching(disciplineName = "МЕТРОЛОГИЯ, СТАНДАРТИЗАЦИЯ и СЕРТИФИКАЦИЯ", lecture = 12, laboratory = 12, practice = 12),
                teaching(disciplineName = "БЕЗОПАСНОСТЬ ЖИЗНЕДЕЯТЕЛЬНОСТИ", lecture = 12, laboratory = 12),
                teaching(disciplineName = "ПРОЕКТИРОВАНИЕ АВТОМАТИЗИРОВАННЫХ СИСТЕМ ОБРАБОТКИ ИНФОРМАЦИИ и УПРАВЛЕНИЯ", lecture = 12, practice = 12, laboratory = 12),
                teaching(disciplineName = "НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", practice = 12),
            )
        )
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
        )
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
    }


    private suspend fun addToAcademicPlan(groupName: String, plan: List<Pair<Discipline, Map<WorkType, AcademicHour>>>) {
        val group = groupRepository.findByName(groupName)
        val groupPlan = GroupPlan(group).apply {
            plan.forEach { (discipline, works) ->
                works.forEach { (workType, hours) ->
                    this.set(discipline, workType, hours)
                }
            }
        }
        academicPlan.set(group, groupPlan)
    }


    private suspend fun teaching(
        disciplineName: String,
        lecture: AcademicHour = 0,
        practice: AcademicHour = 0,
        laboratory: AcademicHour = 0
    ): Pair<Discipline, Map<WorkType, AcademicHour>> {
        val works = mapOf(
            WorkType.LECTURE to lecture,
            WorkType.PRACTICE to practice,
            WorkType.LABORATORY to laboratory,
        )
        val discipline = disciplineRepository.findByName(disciplineName)
        return discipline to works
    }

}

private suspend fun TeachersRepository.findByLastName(vararg lastname: String): List<Teacher> {
    return allTeachers.first().getOrThrow().filter { lastname.contains(it.lastName) }
}
private suspend fun RoomRepository.findByName(vararg names: String): List<Room> {
    return allRooms.first().getOrThrow().filter { names.contains(it.name) }
}
private suspend fun GroupRepository.findByName(name: String): Group {
    return allGroups.first().getOrThrow().first { it.name == name }
}
private suspend fun DisciplineRepository.findByName(name: String): Discipline {
    return allDisciplines.first().getOrThrow().first { it.name == name }
}