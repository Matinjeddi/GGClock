package com.gammagammonclock.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gammagammonclock.model.Player
import com.gammagammonclock.viewmodel.GameViewModel
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun MainTab(
    gameViewModel: GameViewModel = viewModel()
) {
    val gameState by gameViewModel.gameState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // Timers and controls row (fills most of the screen)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimerDisplay(
                leftPlayer = gameState.leftPlayer,
                rightPlayer = gameState.rightPlayer,
                currentPlayer = gameState.currentPlayer,
                gameStatus = gameState.gameStatus,
                onStartGame = { gameViewModel.startGame() },
                onPauseGame = { gameViewModel.pauseGame() },
                onResumeGame = { gameViewModel.resumeGame() },
                onResetGame = { gameViewModel.resetGame() },
                onPlayerButtonPressed = { player -> gameViewModel.onPlayerButtonPressed(player) },
                matchLength = gameState.settings.matchLength,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
        }
        // Centered, larger score board below timers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left player score
            Button(
                onClick = { gameViewModel.updateScore(Player.LEFT, false) },
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "-",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF2196F3),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 48.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gameState.leftPlayer.score.toString(),
                    color = Color.White,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { gameViewModel.updateScore(Player.LEFT, true) },
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "+",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
            Spacer(modifier = Modifier.width(48.dp))
            // Right player score
            Button(
                onClick = { gameViewModel.updateScore(Player.RIGHT, false) },
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "-",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFF9800),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 48.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gameState.rightPlayer.score.toString(),
                    color = Color.White,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { gameViewModel.updateScore(Player.RIGHT, true) },
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "+",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
        }
        // Game status info at the very bottom
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // No game over message
        }
    }
} 