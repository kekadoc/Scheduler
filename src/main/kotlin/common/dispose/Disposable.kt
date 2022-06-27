package common.dispose

interface Disposable {

    val isDisposed: Boolean

    fun dispose(): Boolean
}