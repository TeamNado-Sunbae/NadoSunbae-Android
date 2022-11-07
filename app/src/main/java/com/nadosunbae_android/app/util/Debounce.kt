package com.nadosunbae_android.app.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    timeMillis: Long = 300L,
    coroutineScope: CoroutineScope,
    block: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return {
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(timeMillis)
            block(it)
        }
    }
}