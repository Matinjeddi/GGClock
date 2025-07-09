# Gradle Download Troubleshooting

## Issue: Gradle Distribution Download Failed

**Error:** `Could not create parent directory for lock file`

## Solutions (Try in Order):

### 1. Clear Gradle Cache (Recommended)
1. Close Android Studio
2. Delete the Gradle cache folder:
   ```
   C:\Users\[YourUsername]\.gradle\wrapper\dists
   ```
3. Restart Android Studio
4. Let it download Gradle again

### 2. Run as Administrator
1. Right-click on Android Studio
2. Select "Run as administrator"
3. Try building the project again

### 3. Change Gradle Home Directory
1. In Android Studio: File → Settings → Build, Execution, Deployment → Gradle
2. Change "Gradle JDK" to a different JDK version
3. Or change "Gradle user home" to a different directory

### 4. Manual Gradle Download
If automatic download fails:
1. Download Gradle 8.4 manually from: https://gradle.org/releases/
2. Extract to: `C:\Users\[YourUsername]\.gradle\wrapper\dists\gradle-8.4-bin\`
3. Restart Android Studio

### 5. Disable Antivirus Temporarily
Some antivirus software blocks Gradle downloads:
1. Temporarily disable antivirus
2. Try downloading Gradle
3. Re-enable antivirus after download

### 6. Use Different Network
- Try connecting to a different network
- Use mobile hotspot if available
- Check if corporate firewall is blocking the download

### 7. Alternative: Use Android Studio's Built-in Gradle
1. In Android Studio: File → Settings → Build, Execution, Deployment → Gradle
2. Select "Use Gradle from: 'gradle-wrapper.properties' file"
3. Or try "Use Gradle from: 'Specified location'" and point to a local Gradle installation

## Quick Fix Commands (Run in PowerShell as Administrator):

```powershell
# Clear Gradle cache
Remove-Item -Recurse -Force "$env:USERPROFILE\.gradle\wrapper\dists" -ErrorAction SilentlyContinue

# Create fresh directory
New-Item -ItemType Directory -Path "$env:USERPROFILE\.gradle\wrapper\dists" -Force
```

## Verify Installation:
After successful download, you should see:
- Gradle files in: `C:\Users\[YourUsername]\.gradle\wrapper\dists\gradle-8.4-bin\`
- No more download errors in Android Studio

## If All Else Fails:
1. Use Android Studio's built-in Gradle
2. Download Gradle manually and extract to the cache directory
3. Contact your system administrator if on a corporate network 