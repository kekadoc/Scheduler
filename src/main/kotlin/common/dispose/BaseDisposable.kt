package common.dispose

class BaseDisposable(
    private val onDispose: () -> Unit
) : Disposable {

    override var isDisposed: Boolean = false
        private set

    override fun dispose(): Boolean {
        if (isDisposed) return false
        isDisposed = true
        onDispose()
        return true
    }
}