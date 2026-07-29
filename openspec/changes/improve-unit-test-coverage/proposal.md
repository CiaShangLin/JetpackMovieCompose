## Why

目前專案僅有 22 個測試檔案，其中 15 個是 Android Studio 自動產生的空樣板 `ExampleUnitTest.kt`（無任何實質斷言），真正有系統性測試涵蓋的只有 `core/network`（5 個測試）、`core/domain` 的 `GetMovieSearchUseCase`（1 個）、`feature/search` 的 `SearchViewModel`（1 個）。其餘 7 個 ViewModel 中有 6 個、6 個 UseCase 中有 5 個、`core/data` 的 2 個 Repository 實作、`core/datastore` 的 Mapper / Provider 全數缺乏單元測試，屬於業務邏輯與資料協調的核心風險區塊卻完全未受保護。

此外，專案目前**沒有設定任何覆蓋率量測工具**（未套用 Jacoco / Kover），`CLAUDE.md` 訂下的「最低 80% 單元測試覆蓋率」目前無法量化驗證，也無從得知實際覆蓋率數字。必須先補上覆蓋率量測機制，才能讓「補齊到 80%」這個目標可被驗收，而非僅憑「有無測試檔案」做粗略估算。

## What Changes

- 在 `buildSrc/SharedLibraryGradlePlugin` 統一套用 Jacoco（或 Kover）覆蓋率外掛，讓所有套用該 plugin 的 library 模組（core/*、feature/*）自動具備覆蓋率報表產出能力，並設定 80% 門檻。
- 清理 15 個空的 `ExampleUnitTest.kt` 樣板檔案，避免其被誤判為「該模組已有測試」。
- 依優先順序補齊單元測試（採 JUnit5 + Strikt + MockK + Coroutines Test，比照 `core/network` 現有風格與 AAA 模式）：
  - **P0（核心業務邏輯）**：`core/domain` 剩餘 5 個 UseCase（`GetConfigurationUseCase`、`GetHistoryMovieListUseCase`、`GetHomeMovieListUseCase`、`GetMovieDetailUseCase`、`GetMovieRecommendUseCase`）；`core/data` 的 `MovieRepositoryImp`、`UserDataRepositoryImp`；7 個缺測試的 ViewModel（`MainViewModel`、`HomeViewModel`、`HomeContentViewModel`、`MovieDetailViewModel`、`CollectViewModel`、`HistoryViewModel`、`SettingViewModel`）。
  - **P1（資料轉換/邊界層）**：`core/datastore` 的 `ConfigurationMapper`、`BaseHostUrlProviderImpl`、`LanguageProviderImpl`。
  - **P2（可視情況延後）**：`core/database` DAO 層（需 Instrumented/Robolectric 測試，另立子任務規劃）；`core/common`、`core/model`、`core/designsystem`、`core/ui` 等以介面/資料類別為主、邏輯量小的模組。

## Capabilities

### New Capabilities
- `unit-test-coverage`：定義專案單元測試覆蓋率量測機制（Jacoco/Kover 設定與門檻）與各模組最低覆蓋率要求的規範。

### Modified Capabilities
（無既有 spec 的需求變更，本次為新增測試覆蓋率相關規範）

## Impact

- **buildSrc**：`buildSrc/src/main/kotlin/plugs/SharedLibraryGradlePlugin.kt` 需新增 Jacoco/Kover 套用與門檻設定；若新增外掛依賴，需同步更新 `buildSrc/src/main/kotlin/deps/DependenciesVersions.kt`、`Dependencies.kt`、`DependenciesProvider.kt`。
- **core/domain**：新增 5 個 UseCase 測試檔。
- **core/data**：新增 `MovieRepositoryImp`、`UserDataRepositoryImp` 測試檔（`core/data` 目前實質 0 測試）。
- **core/datastore**：新增 `ConfigurationMapper`、`BaseHostUrlProviderImpl`、`LanguageProviderImpl` 測試檔。
- **feature/home、feature/moviedetail、feature/collect、feature/history、feature/setting、app**：各自新增對應 ViewModel 測試檔。
- **所有模組的 `src/test/`**：移除空的 `ExampleUnitTest.kt` 樣板檔案。
- 不影響任何既有 API 行為或 production 程式碼邏輯，純粹新增測試與建置設定。
