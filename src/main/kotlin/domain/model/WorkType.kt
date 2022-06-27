package domain.model

enum class WorkType(val text: String) {
    PRACTICE("Практическая"),
    LABORATORY("Лабораторная"),
    LECTURE("Лекция"),
    UNSPECIFIED("-")
}