# Installation Guide for GammaGammonClock

## Prerequisites

### 1. Install Java Development Kit (JDK)
The project requires Java 8 or higher. You can download it from:
- **Oracle JDK**: https://www.oracle.com/java/technologies/downloads/
- **OpenJDK**: https://adoptium.net/

**For Windows:**
1. Download the JDK installer
2. Run the installer and follow the setup wizard
3. Set JAVA_HOME environment variable:
   - Open System Properties → Advanced → Environment Variables
   - Add new System Variable: `JAVA_HOME` = `C:\Program Files\Java\jdk-17` (adjust path as needed)
   - Add `%JAVA_HOME%\bin` to your PATH variable

**Verify Installation:**
```bash
java -version
javac -version
```

### 2. Install Android Studio
Download and install Android Studio from: https://developer.android.com/studio

**Setup Steps:**
1. Run the installer
2. Follow the setup wizard
3. Install Android SDK (API level 24+)
4. Create an Android Virtual Device (AVD) for testing

## Building the Project

### Option 1: Using Android Studio (Recommended)
1. Open Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to the project folder and select it
4. Wait for Gradle sync to complete
5. Click "Run" to build and install on device/emulator

### Option 2: Using Command Line
1. Open terminal/command prompt in the project directory
2. Run: `.\gradlew.bat build` (Windows) or `./gradlew build` (Mac/Linux)
3. Install APK: `.\gradlew.bat installDebug`

## Troubleshooting

### Common Issues:

**1. "JAVA_HOME is not set"**
- Ensure Java is installed and JAVA_HOME is set correctly
- Restart terminal after setting environment variables

**2. "Gradle sync failed"**
- Check internet connection (Gradle needs to download dependencies)
- Try: File → Invalidate Caches and Restart

**3. "Build failed"**
- Ensure Android SDK is installed
- Check that compileSdk and targetSdk versions are compatible

**4. "Device not found"**
- Enable Developer Options on Android device
- Enable USB Debugging
- Install device drivers if needed

## Project Structure
```
GammaGammonClock/
├── app/                          # Main application module
│   ├── src/main/
│   │   ├── java/                # Kotlin source code
│   │   ├── res/                 # Resources (strings, colors, etc.)
│   │   └── AndroidManifest.xml  # App manifest
│   └── build.gradle             # App-level build configuration
├── build.gradle                 # Project-level build configuration
├── settings.gradle              # Project settings
└── gradle/                      # Gradle wrapper files
```

## Features Ready to Test
- ✅ Settings screen with game configuration
- ✅ Main game screen with dual timer system
- ✅ Score tracking with +/- buttons
- ✅ Player button states (light/dark blue)
- ✅ Start/Reset functionality
- ✅ Per-move delay timer (5-12 seconds)
- ✅ Main clock countdown (2-20 minutes)

## Next Steps
1. Install Java and Android Studio
2. Open project in Android Studio
3. Build and run the app
4. Test all features and timer logic
5. Customize colors or add additional features as needed 