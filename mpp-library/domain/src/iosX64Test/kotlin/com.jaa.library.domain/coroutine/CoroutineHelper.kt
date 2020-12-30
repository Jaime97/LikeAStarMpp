package com.jaa.library.domain.coroutine

import kotlinx.coroutines.runBlocking

actual class CoroutineHelper {

    @Suppress("UnusedPrivateMember")
    internal actual fun <T> runTest(block: suspend () -> T) {
        runBlocking { block() }
    }
}