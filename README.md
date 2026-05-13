# NammaMistri

NammaMistri is an Android application project built with Kotlin and Android Gradle. The project is structured as a standard Android app module under a single Gradle project.

## Project Overview

- Project name: `NammaMistri`
- Android package: `com.nammamistri.app`
- Kotlin + Android Gradle project
- Minimum SDK: 24
- Target SDK: 34
- Compile SDK: 34
- Uses Room, Navigation Component, Glide, Coroutines, and ViewBinding

## Folder Structure

- `app/` - main Android application module
  - `build.gradle.kts` - app module Gradle configuration
  - `src/main/AndroidManifest.xml` - Android manifest and permissions
  - `src/main/kotlin/` - Kotlin source code for the app
  - `src/main/res/` - resources such as layouts, drawables, values, and XML assets
  - `proguard-rules.pro` - release code shrinking rules
- `build.gradle.kts` - root Gradle configuration for plugin declarations and shared tasks
- `settings.gradle.kts` - Gradle project settings and included modules
- `gradle/` - Gradle wrapper configuration
- `gradlew`, `gradlew.bat` - Gradle wrapper launchers for Unix and Windows
- `gradle.properties`, `local.properties` - Gradle property files

## Key Features

- Android application structure with a single app module
- Room database support via `androidx.room`
- Navigation component for in-app screen navigation
- Glide for image loading and caching
- Kotlin Coroutines for asynchronous operations
- ViewBinding enabled for safer view access
- Camera and storage permissions handled in `AndroidManifest.xml`

## Requirements

- Android Studio Bumblebee or newer (recommended)
- Java 17 / JDK 17
- Gradle wrapper included in the project
- Android SDK platforms for API 34

## Setup

1. Open the project in Android Studio.
2. Let Android Studio sync the Gradle files and download dependencies.
3. If `local.properties` is missing or needs updating, add your SDK path:

```properties
sdk.dir=C:\Users\<your-user>\AppData\Local\Android\sdk
```

## Build and Run

From Android Studio:
1. Select the `app` module.
2. Choose a device or emulator.
3. Click `Run`.

From the terminal:

```powershell
./gradlew clean assembleDebug
```

Or on Windows:

```powershell
gradlew.bat clean assembleDebug
```

## Screenshots

The project includes screenshot assets in the `app/` folder. These illustrate the app UI and workflow.

### Screenshot 1: Wall Calculator
![Wall Calculator](app/Screenshot%202026-05-13%20190651.png)
*Wall dimensions input screen with site selection and calculate/save actions.*

### Screenshot 2: Team Management
![Team Management](app/Screenshot%202026-05-13%20190711.png)
*Team tab showing site summary and buttons to add workers.*

### Screenshot 3: Photo Capture
![Photo Capture](app/Screenshot%202026-05-13%20190734.png)
*Photos tab with site selector and option to take site photos.*

### Screenshot 4: Market Rates
![Market Rates](app/Screenshot%202026-05-13%20190745.png)
*Rates screen listing current material prices and update fields.*

### Screenshot 5: Calculation Results
![Calculation Results](app/Screenshot%202026-05-13%20190834.png)
*Result summary screen showing bricks, cement, sand, and estimated cost.*

### App Branding
![Launch/Brand Image](app/nama_mistri_img.jpeg)
*Project logo illustration for Namma-Mistri.*

## Testing

Run unit tests from the terminal:

```powershell
./gradlew testDebugUnitTest
```

## Notes

- `app/src/main/AndroidManifest.xml` includes storage and camera permissions.
- The app uses `ksp` for annotation processing with Room and Glide.
- Release builds enable code shrinking and resource shrinking.

---

