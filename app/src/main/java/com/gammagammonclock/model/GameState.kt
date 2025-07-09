package com.gammagammonclock.model

data class GameState(
    val settings: GameSettings = GameSettings(),
    val leftPlayer: PlayerState = PlayerState(),
    val rightPlayer: PlayerState = PlayerState(),
    val currentPlayer: Player? = null,
    val gameStatus: GameStatus = GameStatus.NOT_STARTED,
    val currentGameNumber: Int = 1,
    val gamesWon: Map<Player, Int> = mapOf(Player.LEFT to 0, Player.RIGHT to 0)
) 