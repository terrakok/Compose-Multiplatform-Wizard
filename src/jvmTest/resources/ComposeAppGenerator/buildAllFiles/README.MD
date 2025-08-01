# Compose Multiplatform Application

## Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)  
 - install JDK 17 or higher on your machine  
 - add `local.properties` file to the project root and set a path to Android SDK there  

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration  

To build the application bundle:  
 - run `./gradlew :composeApp:assembleDebug`  
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`  

Run android UI tests on the connected device: `./gradlew :composeApp:connectedDebugAndroidTest`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`  
Run desktop UI tests: `./gradlew :composeApp:jvmTest`  

### iOS
To run the application on iPhone device/simulator:  
 - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration  
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio  

Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`  

### Web Distribution
Build web distribution: `./gradlew :composeApp:composeCompatibilityBrowserDistribution`  
Deploy a dir `composeApp/build/dist/composeWebCompatibility/productionExecutable` to a web server  

### Wasm Browser
Run the browser application: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue`  
Run browser UI tests: `./gradlew :composeApp:wasmJsBrowserTest`  

