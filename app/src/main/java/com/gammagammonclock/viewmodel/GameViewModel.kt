package com.gammagammonclock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gammagammonclock.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private var timerJob: kotlinx.coroutines.Job? = null

    fun updateSettings(settings: GameSettings) {
        _gameState.value = _gameState.value.copy(
            settings = settings,
            leftPlayer = _gameState.value.leftPlayer.copy(
                mainTimeRemaining = settings.mainClockMinutes * 60 * 1000L,
                delayTimeRemaining = settings.delaySeconds * 1000L
            ),
            rightPlayer = _gameState.value.rightPlayer.copy(
                mainTimeRemaining = settings.mainClockMinutes * 60 * 1000L,
                delayTimeRemaining = settings.delaySeconds * 1000L
            )
        )
    }

    fun startGame() {
        val settings = _gameState.value.settings
        _gameState.value = _gameState.value.copy(
            gameStatus = GameStatus.RUNNING,
            currentPlayer = null, // No player starts automatically
            leftPlayer = _gameState.value.leftPlayer.copy(
                mainTimeRemaining = settings.mainClockMinutes * 60 * 1000L,
                delayTimeRemaining = settings.delaySeconds * 1000L,
                isActive = false
            ),
            rightPlayer = _gameState.value.rightPlayer.copy(
                mainTimeRemaining = settings.mainClockMinutes * 60 * 1000L,
                delayTimeRemaining = settings.delaySeconds * 1000L,
                isActive = false
            )
        )
        // Don't start timer yet - wait for first player to press their button
    }

    fun resetGame() {
        timerJob?.cancel()
        val settings = _gameState.value.settings
        _gameState.value = _gameState.value.copy(
            gameStatus = GameStatus.NOT_STARTED,
            currentPlayer = null,
            leftPlayer = PlayerState(
                mainTimeRemaining = settings.mainClockMinutes * 60 * 1000L,
                delayTimeRemaining = settings.delaySeconds * 1000L
            ),
            rightPlayer = PlayerState(
                mainTimeRemaining = settings.mainClockMinutes * 60 * 1000L,
                delayTimeRemaining = settings.delaySeconds * 1000L
            )
        )
    }

    fun onPlayerButtonPressed(player: Player) {
        if (_gameState.value.gameStatus != GameStatus.RUNNING) return
        
        val currentPlayer = _gameState.value.currentPlayer
        
        if (currentPlayer == null) {
            // First button press - start the game with the OTHER player
            val settings = _gameState.value.settings
            val otherPlayer = if (player == Player.LEFT) Player.RIGHT else Player.LEFT
            _gameState.value = _gameState.value.copy(
                currentPlayer = otherPlayer,
                leftPlayer = _gameState.value.leftPlayer.copy(
                    isActive = otherPlayer == Player.LEFT,
                    delayTimeRemaining = if (otherPlayer == Player.LEFT) settings.delaySeconds * 1000L else _gameState.value.leftPlayer.delayTimeRemaining
                ),
                rightPlayer = _gameState.value.rightPlayer.copy(
                    isActive = otherPlayer == Player.RIGHT,
                    delayTimeRemaining = if (otherPlayer == Player.RIGHT) settings.delaySeconds * 1000L else _gameState.value.rightPlayer.delayTimeRemaining
                )
            )
            startTimer()
        } else if (currentPlayer == player) {
            // Switch to other player
            val otherPlayer = if (player == Player.LEFT) Player.RIGHT else Player.LEFT
            val settings = _gameState.value.settings
            
            _gameState.value = _gameState.value.copy(
                currentPlayer = otherPlayer,
                leftPlayer = _gameState.value.leftPlayer.copy(
                    isActive = otherPlayer == Player.LEFT,
                    delayTimeRemaining = if (otherPlayer == Player.LEFT) settings.delaySeconds * 1000L else _gameState.value.leftPlayer.delayTimeRemaining
                ),
                rightPlayer = _gameState.value.rightPlayer.copy(
                    isActive = otherPlayer == Player.RIGHT,
                    delayTimeRemaining = if (otherPlayer == Player.RIGHT) settings.delaySeconds * 1000L else _gameState.value.rightPlayer.delayTimeRemaining
                )
            )
            
            startTimer()
        }
    }

    fun updateScore(player: Player, increment: Boolean) {
        val matchLength = _gameState.value.settings.matchLength
        val currentScore = if (player == Player.LEFT) {
            _gameState.value.leftPlayer.score
        } else {
            _gameState.value.rightPlayer.score
        }
        val newScore = if (increment) {
            if (currentScore < matchLength) currentScore + 1 else currentScore
        } else {
            maxOf(0, currentScore - 1)
        }
        _gameState.value = _gameState.value.copy(
            leftPlayer = if (player == Player.LEFT) {
                _gameState.value.leftPlayer.copy(score = newScore)
            } else {
                _gameState.value.leftPlayer
            },
            rightPlayer = if (player == Player.RIGHT) {
                _gameState.value.rightPlayer.copy(score = newScore)
            } else {
                _gameState.value.rightPlayer
            }
        )
    }

    fun pauseGame() {
        if (_gameState.value.gameStatus == GameStatus.RUNNING) {
            timerJob?.cancel()
            _gameState.value = _gameState.value.copy(gameStatus = GameStatus.PAUSED)
        }
    }

    fun resumeGame() {
        if (_gameState.value.gameStatus == GameStatus.PAUSED) {
            _gameState.value = _gameState.value.copy(gameStatus = GameStatus.RUNNING)
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_gameState.value.gameStatus == GameStatus.RUNNING) {
                delay(100) // Update every 100ms
                
                val currentPlayer = _gameState.value.currentPlayer ?: break
                val currentState = _gameState.value
                
                val updatedState = if (currentPlayer == Player.LEFT) {
                    val leftPlayer = currentState.leftPlayer
                    if (leftPlayer.delayTimeRemaining > 0) {
                        // Delay timer is running
                        val newDelayTime = maxOf(0L, leftPlayer.delayTimeRemaining - 100)
                        currentState.copy(
                            leftPlayer = leftPlayer.copy(delayTimeRemaining = newDelayTime)
                        )
                    } else {
                        // Main timer is running
                        val newMainTime = maxOf(0L, leftPlayer.mainTimeRemaining - 100)
                        if (newMainTime == 0L) {
                            // Time's up for left player
                            currentState.copy(
                                gameStatus = GameStatus.FINISHED,
                                gamesWon = currentState.gamesWon + (Player.RIGHT to currentState.gamesWon[Player.RIGHT]!! + 1)
                            )
                        } else {
                            currentState.copy(
                                leftPlayer = leftPlayer.copy(mainTimeRemaining = newMainTime)
                            )
                        }
                    }
                } else {
                    val rightPlayer = currentState.rightPlayer
                    if (rightPlayer.delayTimeRemaining > 0) {
                        // Delay timer is running
                        val newDelayTime = maxOf(0L, rightPlayer.delayTimeRemaining - 100)
                        currentState.copy(
                            rightPlayer = rightPlayer.copy(delayTimeRemaining = newDelayTime)
                        )
                    } else {
                        // Main timer is running
                        val newMainTime = maxOf(0L, rightPlayer.mainTimeRemaining - 100)
                        if (newMainTime == 0L) {
                            // Time's up for right player
                            currentState.copy(
                                gameStatus = GameStatus.FINISHED,
                                gamesWon = currentState.gamesWon + (Player.LEFT to currentState.gamesWon[Player.LEFT]!! + 1)
                            )
                        } else {
                            currentState.copy(
                                rightPlayer = rightPlayer.copy(mainTimeRemaining = newMainTime)
                            )
                        }
                    }
                }
                
                _gameState.value = updatedState
                
                if (updatedState.gameStatus == GameStatus.FINISHED) {
                    break
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
} 