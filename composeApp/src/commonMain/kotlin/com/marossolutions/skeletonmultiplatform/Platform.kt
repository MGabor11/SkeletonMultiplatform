package com.marossolutions.skeletonmultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform