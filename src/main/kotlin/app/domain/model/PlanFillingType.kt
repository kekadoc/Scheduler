package app.domain.model

@kotlinx.serialization.Serializable
sealed class PlanFillingType {
    @kotlinx.serialization.Serializable
    object Evenly : PlanFillingType()
    @kotlinx.serialization.Serializable
    data class Limitation(val limitInCycle: Int) : PlanFillingType()
}
