package common.lifecycle

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.test.Test

class LifecycleTest {

    @Test
    fun test() = runBlocking {
        withTimeout(1000) {
            val lifecycle = MutableLifecycle()
            val j = launch { lifecycle.state.onEach { println("each=$it") }.collect() }
            delay(200)
            val jj = launch(Dispatchers.Default) { lifecycle.moveToState(Lifecycle.State.RESUMED) }
            delay(200)
            j.cancel()
            jj.cancel()
        }
    }


    val sharedFlow = MutableSharedFlow<Int>(replay = Int.MAX_VALUE, onBufferOverflow = BufferOverflow.SUSPEND)

    suspend fun emitSharedFlow(value: Int) {
        //sharedFlow.resetReplayCache()
        sharedFlow.tryEmit(value)
    }
    @Test
    fun test2() = runBlocking {
        emitSharedFlow(0)
        val j = launch {
            sharedFlow
                .onEach { value -> println("onEach first=$value") }
                .collect()
        }
        emitSharedFlow(1)
        emitSharedFlow(2)
        emitSharedFlow(3)
        val jj = launch {
            var i = 0
            println(sharedFlow.replayCache)
            sharedFlow
                .dropWhile {
                    i++ < sharedFlow.replayCache.size - 1
                }
                .onEach { value -> println("onEach second=$value") }
                .collect()
        }
        delay(200)
        sharedFlow.emit(4)
        sharedFlow.emit(5)
        delay(200)
        println("replayCache=${sharedFlow.replayCache}")
        jj.cancel()
        j.cancel()
        //delay(2000)
    }

    @Test
    fun test3() = runBlocking {
        val flow = MutableStateFlow<Int>(0)

        val j = launch { flow.onEach { println("next1=$it") }.collect() }
        delay(200)
        flow.emit(1)
        delay(200)
        flow.emit(2)
        flow.emit(3)
        flow.emit(4)
        flow.emit(5)
        delay(500)
        flow.emit(6)
        flow.emit(7)
        delay(200)
        j.cancel()
    }


}