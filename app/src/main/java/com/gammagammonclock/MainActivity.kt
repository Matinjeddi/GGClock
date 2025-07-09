package com.gammagammonclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gammagammonclock.model.GameSettings
import com.gammagammonclock.ui.main.MainTab
import com.gammagammonclock.ui.settings.SettingsTab
import com.gammagammonclock.viewmodel.GameViewModel
import com.gammagammonclock.viewmodel.SettingsViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.unit.dp
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GammaGammonClockTheme {
                GammaGammonClockApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GammaGammonClockApp() {
    val gameViewModel: GameViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()
    var selectedTab by remember { mutableStateOf(0) }
    val view = LocalView.current
    SideEffect {
        val window = (view.context as? ComponentActivity)?.window ?: return@SideEffect
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, view).let { controller ->
            controller.isAppearanceLightStatusBars = false
            controller.hide(android.view.WindowInsets.Type.statusBars())
        }
    }
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { selectedTab = 0 }) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Game",
                        tint = if (selectedTab == 0) Color.White else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = { selectedTab = 1 }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = if (selectedTab == 1) Color.White else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> MainTab(gameViewModel)
                1 -> SettingsTab(
                    onSettingsChanged = { settings ->
                        gameViewModel.updateSettings(settings)
                    },
                    settingsViewModel
                )
            }
        }
    }
}

@Composable
fun GammaGammonClockTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            primary = Color(0xFF4CAF50),
            secondary = Color(0xFFF44336)
        ),
        content = content
    )
} 