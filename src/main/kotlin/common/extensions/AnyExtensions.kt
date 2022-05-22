package common.extensions

fun <T> T?.requireNotNull(): T {
    return requireNotNull(value = this)
}

fun <T> T?.requireNotNull(lazyMessage: () -> Any): T {
    return requireNotNull(value = this, lazyMessage = lazyMessage)
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.cast(): T {
    return this as T
}