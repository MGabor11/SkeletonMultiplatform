package com.marossolutions.skeletonmultiplatform.service

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AndroidApplicationCloseServiceImpl : AndroidApplicationCloseService {

    private val _closeApplicationEvents: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val closeApplicationEvents: SharedFlow<Unit> = _closeApplicationEvents.asSharedFlow()

    override suspend fun closeApplication() {
        _closeApplicationEvents.emit(Unit)
    }
}
