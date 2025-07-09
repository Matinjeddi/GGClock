package com.gammagammonclock.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gammagammonclock.R
import com.gammagammonclock.model.GameSettings
import com.gammagammonclock.model.Player
import com.gammagammonclock.viewmodel.SettingsViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTab(
    onSettingsChanged: (GameSettings) -> Unit,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val settings by settingsViewModel.settings.collectAsState()
    
    LaunchedEffect(settings) {
        onSettingsChanged(settings)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Delay Timer
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.delay_timer),
                    style = MaterialTheme.typography.titleMedium
                )
                
                Text(
                    text = "${settings.delaySeconds} seconds",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Slider(
                    value = settings.delaySeconds.toFloat(),
                    onValueChange = { settingsViewModel.updateDelaySeconds(it.roundToInt()) },
                    valueRange = 6f..20f,
                    steps = 14
                )
            }
        }

        // Time Bank
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Time Bank",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Text(
                    text = "${settings.mainClockMinutes} minutes",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Slider(
                    value = settings.mainClockMinutes.toFloat(),
                    onValueChange = { settingsViewModel.updateMainClockMinutes(it.roundToInt()) },
                    valueRange = 1f..50f,
                    steps = 49
                )
            }
        }

        // Match Length
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Match Length",
                    style = MaterialTheme.typography.titleMedium
                )
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = settings.matchLength.toString(),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        GameSettings.validMatchLengths.forEach { length ->
                            DropdownMenuItem(
                                text = { Text(length.toString()) },
                                onClick = {
                                    settingsViewModel.updateMatchLength(length)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
} 