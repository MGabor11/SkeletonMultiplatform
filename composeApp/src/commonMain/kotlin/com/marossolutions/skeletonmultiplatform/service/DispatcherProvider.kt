package com.marossolutions.skeletonmultiplatform.service

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    /**
     * Get the main CoroutineDispatcher (Dispatchers.Main).
     */
    val main: CoroutineDispatcher

    /**
     * Get the default CoroutineDispatcher (Dispatchers.Default).
     */
    val default: CoroutineDispatcher

    /**
     * Get the io CoroutineDispatcher (Dispatchers.IO).
     */
    val io: CoroutineDispatcher
}
