# CLAUDE.md (中文版)

此檔案為 Claude Code (claude.ai/code) 在此專案中工作時的指導文件。

## 專案概述

JetpackMovieCompose 是一個使用 Jetpack Compose 和 TMDB API 建構的現代化 Android 電影應用程式。它展示了多模組架構、Clean Architecture 以及現代 Android 開發實踐。

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

# 只執行單元測試 (標籤)
./gradlew test --tests "*" -Dtags="unit"

# 只執行整合測試
./gradlew test --tests "*" -Dtags="integration"
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

- **`app`**: 主應用程式模組，依賴注入設定，MainActivity
- **`feature/*`**: UI 功能模組 (home, search, moviedetail, collect, history, setting, actor)
- **`core/*`**: 共享的業務邏輯和基礎設施

### 核心模組
- **`core/common`**: 共享工具、基礎類別、調度器、例外處理
- **`core/model`**: 跨模組共享的領域模型
- **`core/domain`**: 實作業務邏輯的用例 (Use Cases)
- **`core/data`**: Repository 實作、資料協調
- **`core/network`**: API 服務、retrofit 設定、網路模型
- **`core/database`**: Room 資料庫、DAOs、實體
- **`core/datastore`**: 使用者偏好設定的 Proto DataStore
- **`core/designsystem`**: 應用程式主題、顏色、字體
- **`core/ui`**: 共享的 UI 元件

### 關鍵模式
- **MVVM**: ViewModels 搭配 StateFlow/Flow 實現響應式 UI
- **Repository 模式**: 資料層抽象化
- **用例模式**: 領域層業務邏輯
- **依賴注入**: 使用 Hilt 進行全應用程式 DI
- **分頁**: Paging 3 處理大型資料集

## 技術棧

### 核心依賴
- **Kotlin**: 主要程式語言
- **Jetpack Compose**: UI 框架
- **Hilt**: 依賴注入
- **Room**: 本地資料庫
- **Proto DataStore**: 設定/偏好儲存
- **Paging 3**: 資料分頁
- **Retrofit + OkHttp**: 網路層
- **Coil**: 圖片載入
- **Navigation Compose**: 導航

### 測試依賴
- **JUnit 5**: 測試框架 (`org.junit.jupiter.api`)
- **Strikt**: Kotlin 斷言庫
- **MockK**: Kotlin 模擬框架
- **Coroutines Test**: 協程測試

## 建置配置

### 建置變體
- **Flavors**: `dev`, `prod`
- **Build Types**: `debug`, `release`, `releaseExternalQA`

### 關鍵配置檔案
- `buildSrc/`: 集中式建置邏輯和依賴管理
- `key.properties`: API 金鑰 (不在版本控制中)
- 不同建置類型的簽名配置

## 開發指導原則

### 程式碼風格
- **語言**: 繁體中文用於註解和文件
- **格式**: 使用 Ktlint 確保一致的格式
- **架構**: 遵循 Clean Architecture 原則
- **測試**: 最低 80% 單元測試覆蓋率
- **命名**: 遵循 Kotlin 慣例 (類別用 PascalCase，函式/變數用 camelCase)

### 測試方法
```kotlin
// 使用 AAA 模式 (Arrange-Act-Assert)
@Test
fun `should return movie list when API call succeeds`() {
    // Arrange - 準備
    val mockData = listOf(/* 測試資料 */)
    every { movieDataSource.getMovies() } returns mockData
    
    // Act - 執行
    val result = repository.getMovies()
    
    // Assert - 驗證
    expectThat(result).isEqualTo(mockData)
}
```

### 檔案結構模式
- ViewModels 使用 StateFlow 進行 UI 狀態管理
- Use Cases 從 ViewModels 接受 CoroutineScope 以確保適當的生命週期管理
- Repository 實作協調網路和本地資料來源
- 所有資料庫操作使用 Flow 實現響應式資料

### API 配置
- 需要在 `key.properties` 中設定 TMDB API 金鑰
- 基礎 URL 配置在 `core/common/BaseHostUrlProvider`
- 語言偏好透過 `LanguageInterceptor` 影響 API 呼叫

### 導航
- 功能模組定義自己的導航函式
- 主導航定義在 `app/src/main/java/com/shang/jetpackmoviecompose/navigation/`

## CI/CD

專案使用 GitHub Actions 進行自動建置和發布：
- 由版本標籤觸發 (v*.*.*)
- 執行測試、建置 APK、簽名並建立 GitHub release
- 需要 secrets: `TMDB_API_KEY`, `KEYSTORE_BASE64` 等

詳細的 CI/CD 設定請參考 `releases/GITHUB_ACTIONS_SETUP.md`。

## 重要注意事項

- API 金鑰儲存在 `key.properties` (排除在版本控制之外)
- 提交程式碼前請使用 `./gradlew ktlintCheck`
- 功能模組應該自包含，具有最小的依賴性
- 推送到主分支前請務必執行測試
- 遵循既有的使用 Hilt 的依賴注入模式
- 對於複雜的偏好設定物件使用 Proto DataStore，而非 SharedPreferences

## 測試最佳實踐

### 測試結構 (根據 Copilot Instructions)
```kotlin
@Test
fun `should create user successfully with valid data`() {
    // Arrange - 準備測試資料
    val userRequest = UserTestDataBuilder()
        .withName("張三")
        .withEmail("test@example.com")
        .build()
    
    val savedUser = userRequest.copy(id = "user-123")
    every { userRepository.save(any()) } returns savedUser
    
    // Act - 執行測試
    val result = userService.createUser(userRequest)
    
    // Assert - 驗證結果
    expectThat(result) {
        get { id }.isEqualTo("user-123")
        get { name }.isEqualTo("張三")
    }
    
    verify { userRepository.save(any()) }
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

### Mock 使用 (使用 MockK)
```kotlin
// 基本 mock 配置
val movieRepository = mockk<MovieRepository>()
every { movieRepository.getMovies() } returns flowOf(movieList)

// 驗證互動
verify { movieRepository.getMovies() }
verify(exactly = 2) { movieRepository.saveMovie(any()) }
```