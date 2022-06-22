package domain.model

sealed class PlanFillingType {
    object Evenly : PlanFillingType()
    data class Limitation(val limitInCycle: Int) : PlanFillingType()
}
