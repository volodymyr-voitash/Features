package com.voitash.contact_domain.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface DispatchersProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: MainCoroutineDispatcher
    val unconfined: CoroutineDispatcher
}