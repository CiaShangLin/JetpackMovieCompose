# CLAUDE.md

此檔案為 Claude Code (claude.ai/code) 在此專案中工作時的指導文件。

## 專案概述

JetpackMovieCompose 是一個使用 Jetpack Compose 和 TMDB API 建構的現代化 Android 電影應用程式。它展示了多模組架構、Clean Architecture 以及現代 Android 開發實踐。

- **Application ID**: `com.shang.jetpackmoviecompose`
- **版本**: 2.0.0 (versionCode 2)
- **SDK**: minSdk 23 / targetSdk 36 / compileSdk 36
- **JDK**: 17
- **Kotlin / Compose Compiler**: 2.0.21（使用 Compose Compiler Gradle Plugin）

## 建置指令

### 開發環境
```bash
# 建置 debug 版本
./gradlew assembleDebug

# 建置特定 flavor + build type
./gradlew assembleProdRelease
./gradlew assembleDevDebug

# 安裝 debug APK
./gradlew installDebug
```

### 測試
```bash
# 執行所有單元測試
./gradlew test

# 執行特定模組的測試
./gradlew :core:network:test
```

### 程式碼品質
```bash
# 執行 ktlint 檢查
./gradlew ktlintCheck

# 使用 ktlint 自動格式化程式碼
./gradlew ktlintFormat

# 執行所有檢查 (lint, test, ktlint)
./gradlew check
```

### 發布
```bash
# 建置正式版 APK
./gradlew assembleProdRelease

# 建立標籤觸發自動發布 (觸發 GitHub Actions)
git tag v1.0.0
git push origin v1.0.0
```

## 架構設計

### 多模組結構
專案遵循三層架構：

- **`app`**: 主應用程式模組，依賴注入設定、MainActivity、主導航
- **`feature/*`**: UI 功能模組 (home, search, moviedetail, collect, history, setting, actor)
- **`core/*`**: 共享的業務邏輯和基礎設施

### 核心模組
- **`core/common`**: 共享工具、基礎類別、調度器、例外處理、`BaseHostUrlProvider` 介面
- **`core/model`**: 跨模組共享的領域模型
- **`core/domain`**: 實作業務邏輯的用例 (Use Cases)，如 `GetHomeMovieListUseCase`、`GetMovieDetailUseCase`、`GetMovieRecommendUseCase`、`GetHistoryMovieListUseCase`、`GetConfigurationUseCase`
- **`core/data`**: Repository 實作、資料協調
- **`core/network`**: API 服務、Retrofit 設定、網路模型、攔截器 (`ApiKeyInterceptor`、`LanguageInterceptor`)
- **`core/database`**: Room 資料庫、DAOs、實體
- **`core/datastore`**: 使用者偏好設定的 Proto DataStore、`BaseHostUrlProviderImpl`
- **`core/designsystem`**: 應用程式主題、顏色、字體
- **`core/ui`**: 共享的 UI 元件、Coil 設定 (`HostInterceptor`)

各模組的 namespace 遵循 `com.shang.<module>` 命名（例如 `com.shang.network`、`com.shang.home`）。

### 關鍵模式
- **MVVM**: ViewModels 搭配 StateFlow/Flow 實現響應式 UI
- **Repository 模式**: 資料層抽象化
- **用例模式**: 領域層業務邏輯
- **依賴注入**: 使用 Hilt 進行全應用程式 DI
- **分頁**: Paging 3 處理大型資料集

### Feature 模組內部結構
每個 feature 模組遵循相同結構（以 `feature/home` 為例）：
```
feature/home/src/main/java/com/shang/home/
├── navigation/HomeNavigation.kt   # 定義 ROUTE 常數與 NavGraphBuilder 擴充函式
└── ui/
    ├── HomeScreen.kt              # Compose UI
    ├── HomeViewModel.kt           # ViewModel (StateFlow)
    └── HomeUiState.kt             # UI 狀態定義
```

## 技術棧

### 核心依賴
- **Kotlin**: 主要程式語言（搭配 KSP 做註解處理，不使用 kapt）
- **Jetpack Compose**: UI 框架（Compose BOM 管理版本）
- **Hilt**: 依賴注入（含 hilt-navigation-compose）
- **Room**: 本地資料庫
- **Proto DataStore**: 設定/偏好儲存（protobuf-java / protobuf-kotlin）
- **Paging 3**: 資料分頁
- **Retrofit + OkHttp**: 網路層
- **Sandwich**: 網路回應包裝與錯誤處理
- **Chucker**: 開發階段的網路請求偵錯工具
- **Kotlin Serialization**: JSON 序列化（另有 Gson 用於部分場景）
- **Coil 3**: 圖片載入
- **Navigation Compose**: 導航
- **Lottie Compose**: 動畫
- **WorkManager**: 背景工作

### 測試依賴
- **JUnit 5**: 測試框架 (`org.junit.jupiter.api`)
- **Strikt**: Kotlin 斷言庫
- **MockK**: Kotlin 模擬框架
- **Coroutines Test**: 協程測試

## 建置配置

### buildSrc 集中式建置邏輯
所有建置邏輯集中在 `buildSrc/src/main/kotlin/`：

- **`build/`**: `BuildConfig`（SDK 版本、App ID、版本號）、`BuildCreator`（build type 建立）、`BuildTypes`、`BuildDimensions`
- **`deps/`**: `DependenciesVersions`（所有依賴版本）、`Dependencies`、`DependenciesProvider`（如 `androidx()`、`hilt()`、`dataModule()` 等擴充函式）、`TestDependencies`、`TestBuildConfig`
- **`flavors/`**: `BuildFlavor`（Dev / Prod）、`FlavorType`
- **`plugs/`**: `BuildPlugins`（插件 ID 常數）、`SharedLibraryGradlePlugin`
- **`signing/`**: `BuildSigning`、`SigningTypes`

**`SharedLibraryGradlePlugin`** 是關鍵：所有 library 模組（core/*、feature/*）套用此自訂插件即可統一取得 SDK 版本、build types、flavors、簽名配置、JUnit 5 (`useJUnitPlatform()`)、ktlint 與 Kotlin Serialization 設定。新增模組時應套用此插件而非手動重複配置。

新增依賴時：先在 `DependenciesVersions` 定義版本，於 `Dependencies` 定義座標，再到 `DependenciesProvider` 提供擴充函式供模組呼叫。

### 建置變體
- **Flavors**: `dev`, `prod`（dimension: `APP`）
- **Build Types**: `debug`, `release`（啟用 minify + proguard）, `releaseExternalQA`
- **簽名**: 三種 build type 各有簽名配置；keystore 檔案包含 `debug.keystore`、`JetpackMovieCompose.jsk`、`qa_release_keystore.jks`

### 關鍵配置檔案
- `buildSrc/`: 集中式建置邏輯和依賴管理（見上節）
- `key.properties`: API 金鑰（不在版本控制中），至少需要 `TMDB_API_KEY`
- `settings.gradle`: 模組註冊（新增模組時記得 include）

## 開發指導原則

### 程式碼風格
- **語言**: 繁體中文用於註解和文件
- **格式**: 使用 Ktlint 確保一致的格式（由 `SharedLibraryGradlePlugin` 統一套用）
- **架構**: 遵循 Clean Architecture 原則
- **測試**: 最低 80% 單元測試覆蓋率
- **命名**: 遵循 Kotlin 慣例（類別用 PascalCase，函式/變數用 camelCase）

### 檔案結構模式
- ViewModels 使用 StateFlow 進行 UI 狀態管理，UI 狀態定義在獨立的 `*UiState.kt`
- Use Cases 從 ViewModels 接受 CoroutineScope 以確保適當的生命週期管理
- Repository 實作協調網路和本地資料來源
- 所有資料庫操作使用 Flow 實現響應式資料

### API 配置
- 需要在 `key.properties` 中設定 TMDB API 金鑰（`TMDB_API_KEY`）
- API 金鑰透過 `core/network` 的 `ApiKeyInterceptor` 附加到請求
- 基礎 URL 介面定義在 `core/common/BaseHostUrlProvider`，實作在 `core/datastore/provider/BaseHostUrlProviderImpl`
- 語言偏好透過 `LanguageInterceptor` 影響 API 呼叫（`app/utils/LanguageSettingUtils` 處理應用程式語言設定）

### 導航
- 各 feature 模組在 `navigation/` 目錄定義自己的 ROUTE 常數與 `NavGraphBuilder` 擴充函式
- 主導航（底部導航列）定義在 `app/src/main/java/com/shang/jetpackmoviecompose/navigation/MainNavItem.kt`，包含五個分頁：HOME、COLLECT、SEARCH、HISTORY、SETTING
- `moviedetail` 與 `actor` 為非底部導航的目的地頁面

## 測試最佳實踐

JUnit 5 已由 `SharedLibraryGradlePlugin` 透過 `useJUnitPlatform()` 統一啟用。目前完整的測試範例集中在 `core/network`（攔截器、`NetworkResponse`、`MovieDataSourceImp` 等測試），可作為撰寫新測試的參考。

### 測試結構（AAA 模式）
```kotlin
@Test
fun `should return movie list when API call succeeds`() {
    // Arrange - 準備測試資料
    val mockData = listOf(/* 測試資料 */)
    every { movieDataSource.getMovies() } returns mockData

    // Act - 執行測試
    val result = repository.getMovies()

    // Assert - 驗證結果
    expectThat(result).isEqualTo(mockData)
}
```

### 使用 Strikt 進行斷言
```kotlin
// 基本斷言
expectThat(result).isEqualTo(expected)
expectThat(result).isNotNull()

// 集合斷言
expectThat(movieList)
    .hasSize(3)
    .all { get { title }.isNotEmpty() }

// 例外斷言
expectThrows<NetworkException> {
    movieService.getMoviesWithInvalidKey()
}
```

### Mock 使用（使用 MockK）
```kotlin
// 基本 mock 配置
val movieRepository = mockk<MovieRepository>()
every { movieRepository.getMovies() } returns flowOf(movieList)

// 驗證互動
verify { movieRepository.getMovies() }
verify(exactly = 2) { movieRepository.saveMovie(any()) }
```

## CI/CD

專案使用 GitHub Actions 進行自動建置和發布（`.github/workflows/release-apk.yml`）：

- **觸發條件**: 版本標籤 (`v*.*.*`) 或手動觸發 (workflow_dispatch)
- **流程**: 單元測試（含測試報告上傳）→ 建置 APK → 簽名 → 建立 GitHub Release
- **必要 secrets**: `TMDB_API_KEY`、`KEYSTORE_BASE64` 等
- CI 環境使用 JDK 17 (temurin) 並啟用 Gradle 快取

詳細的 CI/CD 設定請參考 `releases/GITHUB_ACTIONS_SETUP.md`。

## 重要注意事項

- API 金鑰儲存在 `key.properties`（排除在版本控制之外）
- 提交程式碼前請使用 `./gradlew ktlintCheck`
- 功能模組應該自包含，具有最小的依賴性
- 推送到主分支前請務必執行測試
- 遵循既有的使用 Hilt 的依賴注入模式
- 對於複雜的偏好設定物件使用 Proto DataStore，而非 SharedPreferences
- 新增 core/feature 模組時：在 `settings.gradle` 註冊、套用 `SharedLibraryGradlePlugin`、並在 `DependenciesProvider` 新增對應的模組擴充函式
- 新增依賴時走 buildSrc 流程（`DependenciesVersions` → `Dependencies` → `DependenciesProvider`），不要直接在模組的 build.gradle.kts 寫死座標
