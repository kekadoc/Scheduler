package common.extensions

fun <T> Iterable<T>.forEachApply(block: T.() -> Unit) {
    forEach { block(it) }
}
inline fun <T, C : Collection<T>> C.onEmpty(block: () -> Unit): C {
    if (isEmpty()) block()
    return this
}
inline fun <K, V, M : Map<K, V>> M.onEmpty(block: () -> Unit): M {
    if (isEmpty()) block()
    return this
}

fun <T> Collection<Collection<T>>.same(): Collection<T> {
    val first = firstOrNull() ?: return emptyList()
    return first.filter { item -> all { it.contains(item) } }
}