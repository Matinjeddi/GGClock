package com.gammagammonclock.model

data class PlayerState(
    val score: Int = 0,
    val mainTimeRemaining: Long = 0L, // in milliseconds
    val delayTimeRemaining: Long = 0L, // in milliseconds
    val isActive: Boolean = false
) 