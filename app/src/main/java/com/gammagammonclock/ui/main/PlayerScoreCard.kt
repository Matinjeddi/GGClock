package com.gammagammonclock.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gammagammonclock.model.Player
import com.gammagammonclock.model.PlayerState

@Composable
fun PlayerScoreCard(
    player: Player,
    playerState: PlayerState,
    onScoreIncrement: () -> Unit,
    onScoreDecrement: () -> Unit,
    onPlayerButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (player == Player.LEFT) Color(0xFF4CAF50) else Color(0xFFF44336)
    val buttonColor = if (playerState.isActive) Color(0xFF1976D2) else Color(0xFF81C784)
    
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Player button
        Button(
            onClick = onPlayerButtonPressed,
            modifier = Modifier
                .width(120.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = if (player == Player.LEFT) "Player 1" else "Player 2",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Score section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Increment button
            Button(
                onClick = onScoreIncrement,
                modifier = Modifier.size(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "+",
                    color = backgroundColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Score display
            Box(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = playerState.score.toString(),
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Decrement button
            Button(
                onClick = onScoreDecrement,
                modifier = Modifier.size(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "-",
                    color = backgroundColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        // Spacer to balance layout
        Spacer(modifier = Modifier.height(60.dp))
    }
} 