package com.gammagammonclock.model

data class GameSettings(
    val delaySeconds: Int = 12,
    val mainClockMinutes: Int = 6,
    val matchLength: Int = 3
) {
    companion object {
        val validMatchLengths = (3..25 step 2).toList()
    }
} 