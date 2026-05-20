# Developer Guide

## 1. Getting Started
### Requirements
- Android Studio Flamingo or newer
- JDK 17+
- Android SDK 33+
- Gradle 8.x

### Build Commands
- Debug APK: ./gradlew assembleDebug
- Release AAB: ./gradlew bundleRelease

---

## 2. Project Structure
```
app/
 ├── discovery/        # BLE, NSD, WiFi Direct, UDP
 ├── transfer/         # File transfer logic
 ├── server/           # Ktor embedded server
 ├── security/         # Encrypted storage
 ├── ui/               # Compose UI
 ├── viewmodel/        # MVVM state management
 └── service/          # Foreground service
```

---

## 3. Running the App
1. Install the debug APK on two devices.
2. Ensure both devices are on the same LAN.
3. Open the app → Discovery begins automatically.
4. Select a device → Pair → Transfer files.

---

## 4. Adding New Features
### Adding a new discovery protocol
- Create a new class under `discovery/`
- Implement a `Flow<DeviceCandidate>`
- Register it in the DiscoveryManager

### Adding a new route to Ktor
- Add a new file under `server/routes/`
- Register it in `ServerModule.kt`

---

## 5. Debugging Tips
- Use `adb logcat | grep LocalShare`
- Enable verbose Ktor logging
- Use Android Studio Network Inspector

---

## 6. Release Checklist
- Run `./gradlew clean bundleRelease`
- Verify ProGuard rules
- Test on at least 3 devices
