package common.extensions

fun <T> Iterable<T>.forEachApply(block: T.() -> Unit) {
    forEach { block(it) }
}