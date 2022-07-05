package app.ui.common

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap

fun <T> mutableStateListOf(list: List<T>) = SnapshotStateList<T>().apply { addAll(list) }

fun <K, V> mutableStateMapOf(map: Map<K, V>) = SnapshotStateMap<K, V>().apply { putAll(map) }