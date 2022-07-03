package common.data

import common.dispose.BaseDisposable
import common.dispose.Disposable

abstract class AbstractDataSource<Key, Data> : DataSource<Key, Data> {

    private val listeners: MutableSet<DataListener<Key, Data>> = mutableSetOf()

    override fun addListener(listener: DataListener<Key, Data>): Disposable {
        if (listeners.contains(listener)) throw IllegalStateException("Listener already exist")
        listeners.add(listener)
        return BaseDisposable { listeners.remove(listener) }
    }

    protected suspend fun onCreate(key: Key, data: Data) {
        listeners.forEach { it.onCreate(key, data) }
    }
    protected suspend fun onUpdate(key: Key, data: Data) {
        listeners.forEach { it.onUpdate(key, data) }
    }
    protected suspend fun onDelete(key: Key, data: Data) {
        listeners.forEach { it.onDelete(key, data) }
    }
    protected suspend fun onClear() {
        listeners.forEach { it.onClear() }
    }
}