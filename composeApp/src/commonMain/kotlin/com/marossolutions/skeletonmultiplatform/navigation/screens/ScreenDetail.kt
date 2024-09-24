package com.marossolutions.skeletonmultiplatform.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
data class ScreenDetail(
   val icao: String
) : AppScreen
