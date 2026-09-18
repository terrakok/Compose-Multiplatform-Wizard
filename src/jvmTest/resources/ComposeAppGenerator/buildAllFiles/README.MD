# Multiplatform App

### Android
To run the application on an Android device or emulator:  
 - open the project in Android Studio and run the imported Android run configuration  

To build the application bundle:  
 - run `./gradlew :androidApp:assembleDebug`  
 - find `.apk` file in `androidApp/build/outputs/apk/debug/androidApp-debug.apk`  

### Desktop
Run the desktop application: `./gradlew :desktopApp:run`  
Run the desktop **hot reload** application: `./gradlew :desktopApp:hotRun --auto`  

### iOS
To run the application on an iPhone or simulator:  
 - Open `iosApp/iosApp.xcodeproj` in Xcode and run standard configuration  
 - or use the [Kotlin Multiplatform plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform) for IDEA  

### Web Distribution
Build web distribution: `./gradlew :webApp:composeCompatibilityBrowserDistribution`  
Deploy the `webApp/build/dist/composeWebCompatibility/productionExecutable` directory to a web server  

### Wasm Browser
Run the browser application: `./gradlew :webApp:wasmJsBrowserDevelopmentRun`  

