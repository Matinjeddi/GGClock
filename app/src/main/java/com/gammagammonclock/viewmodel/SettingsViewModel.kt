package com.gammagammonclock.viewmodel

import androidx.lifecycle.ViewModel
import com.gammagammonclock.model.GameSettings
import com.gammagammonclock.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
    private val _settings = MutableStateFlow(GameSettings())
    val settings: StateFlow<GameSettings> = _settings.asStateFlow()

    fun updateDelaySeconds(delaySeconds: Int) {
        _settings.value = _settings.value.copy(delaySeconds = delaySeconds)
    }

    fun updateMainClockMinutes(mainClockMinutes: Int) {
        _settings.value = _settings.value.copy(mainClockMinutes = mainClockMinutes)
    }

    fun updateMatchLength(matchLength: Int) {
        _settings.value = _settings.value.copy(matchLength = matchLength)
    }

    fun getSettings(): GameSettings = _settings.value
} 