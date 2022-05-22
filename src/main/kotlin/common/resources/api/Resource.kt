package common.resources.api

fun interface Resource<T> {

    fun get(qualifier: ResourceQualifier): T
}