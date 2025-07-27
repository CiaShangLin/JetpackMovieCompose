# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

JetpackMovieCompose is a modern Android movie app built with Jetpack Compose and TMDB API. It demonstrates multi-module architecture, Clean Architecture, and modern Android development practices.

## Build Commands

### Development
```bash
# Build debug version
./gradlew assembleDebug

# Build specific flavor + build type
./gradlew assembleProdRelease
./gradlew assembleDevDebug

# Install debug APK
./gradlew installDebug
```

### Testing
```bash
# Run all unit tests
./gradlew test

# Run tests for specific module
./gradlew :core:network:test

# Run unit tests only (tagged)
./gradlew test --tests "*" -Dtags="unit"

# Run integration tests only
./gradlew test --tests "*" -Dtags="integration"
```

### Code Quality
```bash
# Run ktlint check
./gradlew ktlintCheck

# Auto-format code with ktlint
./gradlew ktlintFormat

# Run all checks (lint, test, ktlint)
./gradlew check
```

### Release
```bash
# Build production release APK
./gradlew assembleProdRelease

# Create tag for automated release (triggers GitHub Actions)
git tag v1.0.0
git push origin v1.0.0
```

## Architecture

### Multi-Module Structure
The project follows a three-layer architecture:

- **`app`**: Main application module, dependency injection setup, MainActivity
- **`feature/*`**: UI features (home, search, moviedetail, collect, history, setting, actor)
- **`core/*`**: Shared business logic and infrastructure

### Core Modules
- **`core/common`**: Shared utilities, base classes, dispatchers, exceptions
- **`core/model`**: Domain models shared across modules
- **`core/domain`**: Use cases implementing business logic
- **`core/data`**: Repository implementations, data coordination
- **`core/network`**: API services, retrofit setup, network models
- **`core/database`**: Room database, DAOs, entities
- **`core/datastore`**: Proto DataStore for user preferences
- **`core/designsystem`**: App themes, colors, typography
- **`core/ui`**: Shared UI components

### Key Patterns
- **MVVM**: ViewModels with StateFlow/Flow for reactive UI
- **Repository Pattern**: Data layer abstraction
- **Use Cases**: Domain layer business logic
- **Dependency Injection**: Hilt for DI throughout the app
- **Paging**: Paging 3 for large data sets

## Technology Stack

### Core Dependencies
- **Kotlin**: Primary language
- **Jetpack Compose**: UI framework
- **Hilt**: Dependency injection
- **Room**: Local database
- **Proto DataStore**: Settings/preferences
- **Paging 3**: Data pagination
- **Retrofit + OkHttp**: Network layer
- **Coil**: Image loading
- **Navigation Compose**: Navigation

### Testing Dependencies
- **JUnit 5**: Test framework (`org.junit.jupiter.api`)
- **Strikt**: Kotlin assertion library
- **MockK**: Kotlin mocking framework
- **Coroutines Test**: For testing coroutines

## Build Configuration

### Build Variants
- **Flavors**: `dev`, `prod`
- **Build Types**: `debug`, `release`, `releaseExternalQA`

### Key Configuration Files
- `buildSrc/`: Centralized build logic and dependencies
- `key.properties`: API keys (not in version control)
- Signing configs for different build types

## Development Guidelines

### Code Style
- **Language**: Traditional Chinese (繁體中文) for comments and documentation
- **Format**: Use Ktlint for consistent formatting
- **Architecture**: Follow Clean Architecture principles
- **Testing**: Minimum 80% unit test coverage
- **Naming**: Follow Kotlin conventions (PascalCase for classes, camelCase for functions/variables)

### Testing Approach
```kotlin
// Use AAA pattern (Arrange-Act-Assert)
@Test
fun `should return movie list when API call succeeds`() {
    // Arrange
    val mockData = listOf(/* test data */)
    every { movieDataSource.getMovies() } returns mockData
    
    // Act
    val result = repository.getMovies()
    
    // Assert
    expectThat(result).isEqualTo(mockData)
}
```

### File Structure Patterns
- ViewModels use StateFlow for UI state management
- Use cases accept CoroutineScope from ViewModels for proper lifecycle management
- Repository implementations coordinate between network and local data sources
- All database operations use Flow for reactive data

### API Configuration
- TMDB API key required in `key.properties`
- Base URL configuration in `core/common/BaseHostUrlProvider`
- Language preference affects API calls via `LanguageInterceptor`

### Navigation
- Feature modules define their own navigation functions
- Main navigation defined in `app/src/main/java/com/shang/jetpackmoviecompose/navigation/`

## CI/CD

The project uses GitHub Actions for automated builds and releases:
- Triggered by version tags (v*.*.*)
- Runs tests, builds APK, signs, and creates GitHub release
- Requires secrets: `TMDB_API_KEY`, `KEYSTORE_BASE64`, etc.

See `releases/GITHUB_ACTIONS_SETUP.md` for detailed CI/CD setup.

## Important Notes

- API keys are stored in `key.properties` (excluded from version control)
- Use `./gradlew ktlintCheck` before committing code
- Feature modules should be self-contained with minimal dependencies
- Always run tests before pushing to main branch
- Follow the established dependency injection patterns using Hilt
- Use Proto DataStore for complex preference objects, not SharedPreferences