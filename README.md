# GammaGammonClock

A backgammon clock and scoreboard Android app built with Kotlin and Jetpack Compose.

## Features

- **Dual Timer System**: Per-move delay timer (6-20 seconds) and main clock (1-50 minutes)
- **Score Tracking**: Visual score display with increment/decrement buttons
- **Game Configuration**: Settings for number of games (odd numbers 3-25), timer durations, and starting player
- **Modern UI**: Dark theme with orange/blue player colors and intuitive controls

## How to Use

### Settings Tab
1. **Number of Games**: Select from odd numbers 3-25
2. **Delay Timer**: Set per-move delay (6-20 seconds)
3. **Main Clock**: Set main time limit (1-50 minutes)
4. **Starting Player**: Choose which player starts first

### Game Tab
1. **Start Game**: Press START to begin with configured settings
2. **Player Turns**: Press the active player's timer to end your turn
3. **Timer Logic**: 
   - Each turn starts with the delay timer
   - If delay expires, main clock starts counting down
   - Game ends when a player's main clock reaches zero
4. **Score Management**: Use +/- buttons to adjust scores
5. **Reset**: Press RESET to return to initial state

## Technical Details

- **Architecture**: MVVM with ViewModel and StateFlow
- **UI**: Jetpack Compose with Material3
- **State Management**: Reactive state updates with coroutines
- **Timer Implementation**: Coroutine-based timer with 100ms precision

## Building the App

1. Open the project in Android Studio
2. Sync Gradle files
3. Build and run on an Android device or emulator

## Requirements

- Android API 24+ (Android 7.0)
- Kotlin 1.9.10+
- Jetpack Compose BOM 2023.10.01

## Project Structure

```
app/src/main/java/com/gammagammonclock/
├── MainActivity.kt                 # Main activity and app entry point
├── model/                         # Data models
│   ├── Player.kt                  # Player enum (LEFT/RIGHT)
│   ├── GameStatus.kt              # Game state enum
│   ├── GameSettings.kt            # Game configuration
│   ├── PlayerState.kt             # Individual player state
│   └── GameState.kt               # Overall game state
├── viewmodel/                     # ViewModels
│   ├── GameViewModel.kt           # Game logic and timer management
│   └── SettingsViewModel.kt       # Settings state management
└── ui/                           # UI components
    ├── main/                     # Main game screen
    │   ├── MainTab.kt            # Main game tab
    │   ├── PlayerScoreCard.kt    # Player score display
    │   └── TimerDisplay.kt       # Timer display component
    └── settings/                 # Settings screen
        └── SettingsTab.kt        # Settings configuration tab
``` 