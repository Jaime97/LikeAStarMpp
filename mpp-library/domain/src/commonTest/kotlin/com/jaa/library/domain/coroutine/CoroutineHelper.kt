package com.jaa.library.domain.coroutine

expect class CoroutineHelper() {

    @Suppress("UnusedPrivateMember")
    internal fun <T> runTest(block: suspend () -> T)

}