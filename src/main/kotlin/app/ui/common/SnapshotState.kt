package app.ui.common

import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T> mutableStateListOf(list: List<T>) = SnapshotStateList<T>().apply { addAll(list) }