package com.gammagammonclock.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gammagammonclock.model.Player
import com.gammagammonclock.model.PlayerState
import com.gammagammonclock.model.GameStatus
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf

@Composable
fun TimerDisplay(
    leftPlayer: PlayerState,
    rightPlayer: PlayerState,
    currentPlayer: Player?,
    gameStatus: GameStatus,
    onStartGame: () -> Unit,
    onPauseGame: () -> Unit,
    onResumeGame: () -> Unit,
    onResetGame: () -> Unit,
    onPlayerButtonPressed: (Player) -> Unit,
    matchLength: Int,
    modifier: Modifier = Modifier
) {
    val showResetDialog = remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Timer cards and control icons
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Player Timer
            PlayerTimerCard(
                player = Player.LEFT,
                playerState = leftPlayer,
                isActive = currentPlayer == Player.LEFT,
                onClick = { onPlayerButtonPressed(Player.LEFT) },
                modifier = Modifier.weight(1f)
            )
            
            // Control icons in the middle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Start/Resume button
                if (gameStatus == GameStatus.NOT_STARTED) {
                    IconButton(
                        onClick = onStartGame,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFF4CAF50),
                                shape = RoundedCornerShape(24.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                } else if (gameStatus == GameStatus.PAUSED) {
                    IconButton(
                        onClick = onResumeGame,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFF4CAF50),
                                shape = RoundedCornerShape(24.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Resume",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                } else if (gameStatus == GameStatus.RUNNING) {
                    IconButton(
                        onClick = onPauseGame,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFFFF9800),
                                shape = RoundedCornerShape(24.dp)
                            )
                    ) {
                        Text(
                            text = "Pause",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                // Reset button
                IconButton(
                    onClick = { showResetDialog.value = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFF44336),
                            shape = RoundedCornerShape(24.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                // Only display match length below reset button
                Text(
                    text = matchLength.toString(),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            // Right Player Timer
            PlayerTimerCard(
                player = Player.RIGHT,
                playerState = rightPlayer,
                isActive = currentPlayer == Player.RIGHT,
                onClick = { onPlayerButtonPressed(Player.RIGHT) },
                modifier = Modifier.weight(1f)
            )
        }
    }
    if (showResetDialog.value) {
        AlertDialog(
            onDismissRequest = { showResetDialog.value = false },
            title = { Text("Are you sure you want to reset?") },
            confirmButton = {
                TextButton(onClick = {
                    showResetDialog.value = false
                    onResetGame()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
private fun PlayerTimerCard(
    player: Player,
    playerState: PlayerState,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val playerColor = if (player == Player.LEFT) Color(0xFF2196F3) else Color(0xFFFF9800)
    val backgroundColor = if (isActive) Color(0xFF2E2E2E) else Color(0xFF1E1E1E)
    val borderColor = if (isActive) playerColor else Color.Transparent
    
    Card(
        modifier = modifier
            .height(300.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(
            width = if (isActive) 2.dp else 0.dp,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main time and delay digits in a row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Main time display
                val mainMinutes = playerState.mainTimeRemaining / (60 * 1000)
                val mainSeconds = (playerState.mainTimeRemaining % (60 * 1000)) / 1000
                Text(
                    text = String.format("%02d:%02d", mainMinutes, mainSeconds),
                    color = Color.White,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                )
                // Delay digits (if running)
                val delaySeconds = playerState.delayTimeRemaining / 1000
                if (delaySeconds > 0) {
                    Spacer(modifier = Modifier.width(16.dp))
                    val delayColor = if (player == Player.RIGHT) Color(0xFFFF9800) else Color(0xFF2196F3)
                    Text(
                        text = String.format("%02d", delaySeconds),
                        color = delayColor,
                        fontSize = 46.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            // Active indicator
            // (Removed as per user request)
            // if (isActive) {
            //     Spacer(modifier = Modifier.height(8.dp))
            //     Text(
            //         text = "ACTIVE",
            //         color = playerColor,
            //         fontSize = 16.sp,
            //         fontWeight = FontWeight.Bold
            //     )
            // }
        }
    }
} 