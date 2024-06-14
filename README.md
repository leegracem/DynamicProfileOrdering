# Dynamic Profile Ordering App

This project is an Android application designed to showcase user profiles with customizable field ordering. It leverages remote APIs for user data and configuration, providing users with an intuitive interface to navigate through each profile.

## Architecture

The project follows MVVM architecture pattern, here’s a breakdown of the components:

- **Model**: Defines structure of user profiles (User.kt) and configuration settings (Config.kt) .
  
- **View**: THe main interface, MainActivity.kt, displays user profiles using RecyclerView/UserAdapter.kt which dynamically adapts to the configured profile field order that's fetched from the server.

- **ViewModel**: UserViewModel.kt manages the data handling for the UI, retrieving user data and config from repository.

- **Repository**: UserRepository.kt handles data operations - fetches data from API endpoints using Retrofit.

- **Dependency Injection**: Uses Hilt for dependency injection to provide and manage dependencies across the app modules.

## Libraries Used

- **Retrofit**
- **Glide**
- **Coroutines**
- **Hilt**

## Usage

To run the application:
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the project on an emulator or physical device.
