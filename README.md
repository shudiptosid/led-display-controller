# LED Display Controller Android App

An Android application built with Kotlin and Jetpack Compose to control LED displays via MQTT protocol.

## Features

- 🎨 Modern UI built with Jetpack Compose and Material Design 3
- 📡 MQTT connectivity for real-time LED control
- 🔌 Connect/Disconnect functionality
- 💡 Turn LED ON/OFF commands
- 📊 Real-time connection and LED status monitoring
- 🔄 Reactive state management with Kotlin Flow

## Project Structure

```
app/
├── src/main/
│   ├── java/com/leddisplay/controller/
│   │   ├── MainActivity.kt              # Main UI entry point
│   │   ├── data/
│   │   │   └── MqttManager.kt          # MQTT connection handler
│   │   ├── viewmodel/
│   │   │   └── LEDControlViewModel.kt  # ViewModel for state management
│   │   └── ui/theme/                   # Compose theme files
│   │       ├── Color.kt
│   │       ├── Theme.kt
│   │       └── Type.kt
│   ├── res/
│   │   └── values/
│   │       ├── strings.xml
│   │       └── colors.xml
│   └── AndroidManifest.xml
├── build.gradle.kts                     # App-level build configuration
└── proguard-rules.pro                   # ProGuard rules
```

## Technologies Used

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Async**: Kotlin Coroutines & Flow
- **MQTT Client**: Eclipse Paho
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Dependencies

- AndroidX Core KTX
- AndroidX AppCompat
- Jetpack Compose (Material3)
- Lifecycle & ViewModel
- Eclipse Paho MQTT Client
- Kotlin Coroutines

## Build Instructions

### Prerequisites

1. Android Studio (Arctic Fox or newer)
2. JDK 8 or higher
3. Android SDK (API level 34)

### Building the App

1. **Using Android Studio**:

   - Open the project in Android Studio
   - Wait for Gradle sync to complete
   - Click **Build > Build Bundle(s) / APK(s) > Build APK(s)**

2. **Using Command Line**:

   ```bash
   # Windows
   gradlew.bat assembleDebug

   # Linux/Mac
   ./gradlew assembleDebug
   ```

3. The APK will be generated at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

## Configuration

The app connects to a public MQTT broker by default. To change the MQTT configuration, edit the following constants in `MqttManager.kt`:

```kotlin
private const val BROKER_URL = "tcp://broker.hivemq.com:1883"
private const val CLIENT_ID = "LEDControllerApp"
private const val TOPIC_LED_STATUS = "led/status"
```

## MQTT Topics

- **Control Topic**: `led/control` - Publishes ON/OFF commands
- **Status Topic**: `led/status` - Subscribes to LED status updates

## Permissions

The app requires the following permissions:

- `INTERNET` - For MQTT connectivity
- `ACCESS_NETWORK_STATE` - To check network availability
- `WAKE_LOCK` - To maintain MQTT connection

## Installation

1. Enable **Developer Options** on your Android device
2. Enable **USB Debugging**
3. Connect your device via USB
4. Install the APK:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Usage

1. Launch the **LED Display Controller** app
2. Tap **Connect** to establish MQTT connection
3. Once connected, use **Turn ON LED** or **Turn OFF LED** buttons
4. The LED status will be displayed in real-time
5. Tap **Disconnect** when finished

## Build Output

✅ **Latest Build**:

- File: `app-debug.apk`
- Size: ~8.87 MB
- Build Status: **SUCCESS**

## Future Enhancements

- [ ] Custom MQTT broker configuration UI
- [ ] Multiple LED device support
- [ ] Color picker for RGB LEDs
- [ ] Animation patterns
- [ ] Connection history
- [ ] Offline mode with retry logic

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Third-Party Licenses

This project uses several open-source libraries. See [LICENSES.md](LICENSES.md) for detailed information about:

- AndroidX Libraries (Apache 2.0)
- Kotlin & Coroutines (Apache 2.0)
- Eclipse Paho MQTT Client (EPL 1.0 / EDL 1.0)

## Author

Built with ❤️ using Kotlin and Jetpack Compose
