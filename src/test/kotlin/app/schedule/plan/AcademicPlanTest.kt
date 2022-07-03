package app.schedule.plan

import app.domain.model.*
import app.schedule.builder.BuilderUtils
import app.schedule.builder.ScheduleBuilder
import app.schedule.plan.AcademicPlan.Companion.addPlan
import app.schedule.rule.Rules
import app.schedule.rule.room.RoomRule
import app.schedule.rule.student.StudentGroupRule
import app.schedule.rule.teacher.TeacherRule
import kotlin.math.min
import kotlin.test.Test

class AcademicPlanTest {

    private object Teachers {
        val teacher_1 = Teacher(
            id = 1,
            lastName = "Иванов",
            firstName = "Иван",
            middleName = "Иванович",
            speciality = "учитель"
        )
        val teacher_2 = Teacher(
            id = 2,
            lastName = "Сидорова",
            firstName = "Анна",
            middleName = "Генадьевна",
            speciality = "учитель"
        )
        val teacher_3 = Teacher(
            id = 3,
            lastName = "Свиридова",
            firstName = "Зинаида",
            middleName = "Петровна",
            speciality = "учитель"
        )
        val teacher_4 = Teacher(
            id = 4,
            lastName = "Захаров",
            firstName = "Илья",
            middleName = "Влидимирович",
            speciality = "учитель"
        )
        val teacher_5 = Teacher(
            id = 4,
            lastName = "Тереньтев",
            firstName = "Петр",
            middleName = "Михайлович",
            speciality = "учитель"
        )
    }

    private object Groups {
        val ASU_19 = Group(id = 1L, name = "АСУ-19")
        val ASU_18 = Group(id = 2L, name = "АСУ-18")
        val PGS_19 = Group(id = 5L, name = "ПГС-19")
        val PGS_18 = Group(id = 6L, name = "ПГС-18")
        val ES_19 = Group(id = 9L, name = "ЭС-19")
        val ES_18 = Group(id = 10L, name = "ЭС-18")
        val ATPP_19 = Group(id = 13L, name = "АТПП-19")
        val ATPP_18 = Group(id = 14L, name = "АТПП-18")
    }

    private object Disciplines {
        val russian = Discipline(id = 0, name = "Русский", teachers = emptyList(), rooms = emptyList())
        val biology = Discipline(id = 1, name = "Биология", teachers = emptyList(), rooms = emptyList())
        val math = Discipline(id = 2, name = "Математика", teachers = emptyList(), rooms = emptyList())
        val mathBig = Discipline(id = 3, name = "Высшая математика", teachers = emptyList(), rooms = emptyList())
        val physic = Discipline(id = 4, name = "Физика", teachers = emptyList(), rooms = emptyList())
        val english = Discipline(id = 5, name = "Английский", teachers = emptyList(), rooms = emptyList())
        val sport_1 = Discipline(id = 6, name = "Физкультура", teachers = emptyList(), rooms = emptyList())
        val sport_2 = Discipline(id = 6, name = "Физкультура", teachers = emptyList(), rooms = emptyList())
    }

    private object Teachings {
        val russian_le = Teaching(id = 1, discipline = Disciplines.russian, type = WorkType.LECTURE, teacher = Teacher.Empty, room = Room.Empty)
        val russian_la = Teaching(id = 2, discipline = Disciplines.russian, type = WorkType.LABORATORY, teacher = Teacher.Empty, room = Room.Empty)
        val russian_pr = Teaching(id = 3, discipline = Disciplines.russian, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
        val biology_le = Teaching(id = 4, discipline = Disciplines.biology, type = WorkType.LECTURE, teacher = Teacher.Empty, room = Room.Empty)
        val biology_la = Teaching(id = 5, discipline = Disciplines.biology, type = WorkType.LABORATORY, teacher = Teacher.Empty, room = Room.Empty)
        val biology_pr = Teaching(id = 6, discipline = Disciplines.biology, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
        val math_le = Teaching(id = 7, discipline = Disciplines.math, type = WorkType.LECTURE, teacher = Teacher.Empty, room = Room.Empty)
        val math_la = Teaching(id = 8, discipline = Disciplines.math, type = WorkType.LABORATORY, teacher = Teacher.Empty, room = Room.Empty)
        val math_pr = Teaching(id = 9, discipline = Disciplines.math, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
        val mathBig_le = Teaching(id = 10, discipline = Disciplines.mathBig, type = WorkType.LECTURE, teacher = Teacher.Empty, room = Room.Empty)
        val mathBig_la = Teaching(id = 11, discipline = Disciplines.mathBig, type = WorkType.LABORATORY, teacher = Teacher.Empty, room = Room.Empty)
        val mathBig_pr = Teaching(id = 12, discipline = Disciplines.mathBig, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
        val physic_le = Teaching(id = 13, discipline = Disciplines.physic, type = WorkType.LECTURE, teacher = Teacher.Empty, room = Room.Empty)
        val physic_la = Teaching(id = 14, discipline = Disciplines.physic, type = WorkType.LABORATORY, teacher = Teacher.Empty, room = Room.Empty)
        val physic_pr = Teaching(id = 15, discipline = Disciplines.physic, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
        val english_le = Teaching(id = 16, discipline = Disciplines.english, type = WorkType.LECTURE, teacher = Teacher.Empty, room = Room.Empty)
        val english_la = Teaching(id = 17, discipline = Disciplines.english, type = WorkType.LABORATORY, teacher = Teacher.Empty, room = Room.Empty)
        val english_pr = Teaching(id = 18, discipline = Disciplines.english, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
        val sport_1 = Teaching(id = 19, discipline = Disciplines.sport_1, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
        val sport_2 = Teaching(id = 20, discipline = Disciplines.sport_2, type = WorkType.PRACTICE, teacher = Teacher.Empty, room = Room.Empty)
    }

    private object Rooms {
        val k_1 = Room(id = 1L, name = "1 каб.")
        val k_2 = Room(id = 2L, name = "2 каб.")
        val k_3 = Room(id = 3L, name = "3 каб.")
        val k_4 = Room(id = 4L, name = "4 каб.")
        val k_5 = Room(id = 5L, name = "5 каб.")
    }

    private val weeksCount = 18

    private fun GroupPlan.set(teaching: Teaching, hours: AcademicHour) {
        set(teaching.discipline, teaching.type, hours)
    }

    private val plan = AcademicPlan().apply {
        addPlan(Groups.ASU_19, weeksCount) {
            set(Teachings.russian_le, 6)
            set(Teachings.russian_pr, 6)
            set(Teachings.sport_1, 10)
            set(Teachings.biology_le, 8)
            set(Teachings.biology_la, 8)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
        }
        addPlan(Groups.ASU_18, weeksCount) {
            set(Teachings.english_le, 8)
            set(Teachings.english_pr, 8)
            set(Teachings.sport_2, 10)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
            set(Teachings.physic_le, 14)
            set(Teachings.physic_la, 14)
        }

        addPlan(Groups.PGS_19, weeksCount) {
            set(Teachings.russian_le, 6)
            set(Teachings.russian_pr, 6)
            set(Teachings.sport_1, 10)
            set(Teachings.biology_le, 8)
            set(Teachings.biology_la, 8)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
        }
        addPlan(Groups.PGS_18, weeksCount) {
            set(Teachings.english_le, 8)
            set(Teachings.english_pr, 8)
            set(Teachings.sport_2, 10)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
            set(Teachings.physic_le, 14)
            set(Teachings.physic_la, 14)
        }

        addPlan(Groups.ES_19, weeksCount) {
            set(Teachings.russian_le, 6)
            set(Teachings.russian_pr, 6)
            set(Teachings.sport_1, 10)
            set(Teachings.biology_le, 8)
            set(Teachings.biology_la, 8)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
        }
        addPlan(Groups.ES_18, weeksCount) {
            set(Teachings.english_le, 8)
            set(Teachings.english_pr, 8)
            set(Teachings.sport_2, 10)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
            set(Teachings.physic_le, 14)
            set(Teachings.physic_la, 14)
        }

        addPlan(Groups.ATPP_19, weeksCount) {
            set(Teachings.russian_le, 6)
            set(Teachings.russian_pr, 6)
            set(Teachings.sport_1, 10)
            set(Teachings.biology_le, 8)
            set(Teachings.biology_la, 8)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
        }
        addPlan(Groups.ATPP_18, weeksCount) {
            set(Teachings.english_le, 8)
            set(Teachings.english_pr, 8)
            set(Teachings.sport_2, 10)
            set(Teachings.mathBig_le, 12)
            set(Teachings.mathBig_pr, 12)
            set(Teachings.physic_le, 14)
            set(Teachings.physic_la, 14)
        }
    }

    private val roomRule = RoomRule().apply {
        get(Rooms.k_1)
        get(Rooms.k_2)
        get(Rooms.k_3)
        get(Rooms.k_4)
        get(Rooms.k_5)
    }
    /*private val teachingRule = TeachingRule().apply {
        add(Teachings.russian_le, TeachingRule.Option(room = Rooms.k_1, teacher = Teachers.teacher_1))
        add(Teachings.russian_la, TeachingRule.Option(room = Rooms.k_1, teacher = Teachers.teacher_1))
        add(Teachings.russian_pr, TeachingRule.Option(room = Rooms.k_1, teacher = Teachers.teacher_1))

        add(Teachings.biology_le, TeachingRule.Option(room = Rooms.k_2, teacher = Teachers.teacher_2))
        add(Teachings.biology_la, TeachingRule.Option(room = Rooms.k_2, teacher = Teachers.teacher_2))
        add(Teachings.biology_pr, TeachingRule.Option(room = Rooms.k_2, teacher = Teachers.teacher_2))

        add(Teachings.math_le, TeachingRule.Option(room = Rooms.k_3, teacher = Teachers.teacher_3))
        add(Teachings.math_la, TeachingRule.Option(room = Rooms.k_3, teacher = Teachers.teacher_3))
        add(Teachings.math_pr, TeachingRule.Option(room = Rooms.k_3, teacher = Teachers.teacher_3))

        add(Teachings.mathBig_le, TeachingRule.Option(room = Rooms.k_3, teacher = Teachers.teacher_3))
        add(Teachings.mathBig_la, TeachingRule.Option(room = Rooms.k_3, teacher = Teachers.teacher_3))
        add(Teachings.mathBig_pr, TeachingRule.Option(room = Rooms.k_3, teacher = Teachers.teacher_3))

        add(Teachings.physic_le, TeachingRule.Option(room = Rooms.k_4, teacher = Teachers.teacher_4))
        add(Teachings.physic_la, TeachingRule.Option(room = Rooms.k_4, teacher = Teachers.teacher_4))
        add(Teachings.physic_pr, TeachingRule.Option(room = Rooms.k_4, teacher = Teachers.teacher_4))

        add(Teachings.english_le, TeachingRule.Option(room = Rooms.k_1, teacher = Teachers.teacher_1))
        add(Teachings.english_la, TeachingRule.Option(room = Rooms.k_1, teacher = Teachers.teacher_1))
        add(Teachings.english_pr, TeachingRule.Option(room = Rooms.k_1, teacher = Teachers.teacher_1))

        add(Teachings.sport_1, TeachingRule.Option(room = Rooms.k_5, teacher = Teachers.teacher_5))
        add(Teachings.sport_2, TeachingRule.Option(room = Rooms.k_5, teacher = Teachers.teacher_5))
    }*/
    private val teacherRule = TeacherRule().apply {

    }
    private val groupRule = StudentGroupRule()

    private val rules = Rules(
        availableDays = DayOfWeek.values().take(6).toSet(),
        teacherRule = teacherRule,
        groupRule = groupRule,
        roomRule = roomRule,
        //teachingRule = teachingRule
    )

    @Test
    fun test() {

        val groups = setOf(
            Groups.ASU_18,
            Groups.ASU_19,
            Groups.ES_18,
            Groups.ES_19,
            Groups.PGS_18,
            Groups.PGS_19,
            Groups.ATPP_18,
            Groups.ATPP_19
        )
        val builder = ScheduleBuilder(maxLessonsInDay = 6, groups = groups)

        BuilderUtils.build(
            builder = builder,
            plan = plan,
            rules = rules
        )
        logBuilder(builder)
    }

    private fun logBuilder(builder: ScheduleBuilder) {
        val all = builder.getAll()
        println(all)
        all.forEach { group, groupSchedule ->
            println(group.name)
            val first = groupSchedule.getWeek(WeekType.FIRST)
            val second = groupSchedule.getWeek(WeekType.SECOND)
            DayOfWeek.values().forEach { dayOfWeek ->
                println("__$dayOfWeek")
                val f = first.getDay(dayOfWeek)
                val s = second.getDay(dayOfWeek)
                val size = min(f.getAll().size, s.getAll().size)
                repeat(size) { index ->
                    println("____$index ${lessonToLog(f.get(index))}\n____  ${lessonToLog(s.get(index))}")
                }
            }
        }
    }

    private fun lessonToLog(lesson: Lesson?): String {
        lesson ?: return "Null"
        return "${lesson.name} - ${lesson.teacher.lastName} - ${lesson.room.name}"
    }


}