package com.shang.jetpackmoviecompose

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import kotlin.coroutines.coroutineContext

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        CoroutineScope(Job()).launch {
            coroutineContext[Job]
            Job()
        }
    }

    @Test
    fun test()= runBlocking<Unit> {
        val scope = CoroutineScope(Job())
        scope.async {
            println("123")
        }.join()

    }
}