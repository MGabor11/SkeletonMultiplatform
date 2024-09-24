package com.marossolutions.skeletonmultiplatform.service

import kotlinx.coroutines.flow.SharedFlow

interface AndroidApplicationCloseService : ApplicationCloseService {
    val closeApplicationEvents: SharedFlow<Unit>
}
