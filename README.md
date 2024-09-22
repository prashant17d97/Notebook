# Notebook

This is a Kotlin Multiplatform project that targets Android and iOS, allowing you to write and share code between the two platforms.

## Project Structure

The project is structured as follows:

- **`composeApp`:** This module contains the shared code for both Android and iOS. It uses Kotlin Multiplatform to write code that can be compiled for both platforms.
    - **`commonMain`:** Contains code that is common to both platforms.
    - **`androidMain`:** Contains Android-specific code.
    - **`iosMain`:** Contains iOS-specific code.
- **`androidApp`:** This module contains the Android application. It uses Jetpack Compose for the user interface and depends on the `shared` module for the core logic.
- **`iosApp`:** This module contains the iOS application. It uses SwiftUI for the user interface and depends on the `shared` module for the core logic.


This project demonstrates how to build a cross-platform application using Kotlin Multiplatform, sharing code between Android and iOS while using the native UI frameworks for each platform (Jetpack Compose and SwiftUI).


