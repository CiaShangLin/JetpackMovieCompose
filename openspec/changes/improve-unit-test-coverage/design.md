## Context

專案目前有 22 個測試檔案，其中 15 個是空的 `ExampleUnitTest.kt` 樣板。實質有效的測試僅集中在 `core/network`（5 個）、`core/domain` 的 `GetMovieSearchUseCaseTest`、`feature/search` 的 `SearchViewModelTest`。`CLAUDE.md` 訂下「最低 80% 單元測試覆蓋率」的規範，但專案未套用任何覆蓋率工具，也沒有 CI 上的覆蓋率門檻檢查，這個目標目前無法被驗證。

所有 library 模組（`core/*`、`feature/*`）都套用 `buildSrc` 提供的 `SharedLibraryGradlePlugin` 統一管理建置設定，這是集中導入覆蓋率工具的正確切入點，避免每個模組各自設定造成不一致。

## Goals / Non-Goals

**Goals:**
- 導入覆蓋率量測工具，讓每個模組都能產出覆蓋率報表，並可設定 80% 門檻作為 CI 驗證依據。
- 補齊 P0（UseCase、Repository、ViewModel）與 P1（datastore Mapper/Provider）的單元測試，使核心業務邏輯達到 80% 行覆蓋率。
- 清理無意義的 `ExampleUnitTest.kt` 樣板檔案。
- 建立可複用的測試撰寫慣例（比照 `core/network` 現有風格：JUnit5 + Strikt + MockK + AAA 模式），降低後續補測試的風格分歧。

**Non-Goals:**
- 不涵蓋 `core/database` DAO 層的 Instrumented Test（需要 Robolectric 或 androidTest，工程性質不同，另立子任務）。
- 不涵蓋 Compose UI 層的 Screenshot Test / UI Test。
- 不強制對純資料類別（data class、sealed class UiState、Response model）逐一補測試，除非其中含有自訂邏輯（如自訂 `equals`、mapping 邏輯）。
- 不變更任何 production 程式碼的行為邏輯，僅新增測試與建置設定。

## Decisions

### 1. 覆蓋率工具：採用 Kover 而非 Jacoco
- **選擇 Kover**（`org.jetbrains.kotlinx.kover`）：原生支援 Kotlin Multiplatform/Gradle Kotlin DSL，設定語法簡潔（`koverReport { }` 可直接在 `SharedLibraryGradlePlugin` 內用 Kotlin DSL 設定門檻與排除規則），且能正確處理 Kotlin inline function、Coroutines 產生的合成程式碼，不需要像 Jacoco 那樣額外處理 `kotlin-jvm` 的 bytecode 位移問題。
- **考慮過 Jacoco**：業界更廣泛使用、文件多，但傳統上是 Java bytecode 導向，對 Kotlin 專案（尤其含 `suspend fun`、data class 產生的合成方法）需要額外的 exclude pattern 調整，且與 Gradle Kotlin DSL 整合的設定較繁瑣。由於本專案已全面 Kotlin 化且大量使用 Coroutines/Flow，Kover 的維護成本更低。
- 於 `buildSrc/src/main/kotlin/plugs/SharedLibraryGradlePlugin.kt` 套用 Kover plugin，並設定：
  - 排除 Hilt 產生的程式碼（`*_Factory`、`*_HiltModules`、`Hilt_*`）
  - 排除 DI Module（`*Module.kt`）、Compose UI 檔案（`*Screen.kt`）、純介面
  - 各模組門檻設為 80% line coverage（domain/data 層優先，UI 層可視情況調整或排除）

### 2. 測試撰寫遵循既有 MVVM / Repository / Use Case 模式
- 不引入新的測試框架或 mocking 風格，延用專案既有的 JUnit5 + Strikt + MockK + Coroutines Test 組合，維持與 `core/network` 一致的 AAA 模式。
- ViewModel 測試：驗證 StateFlow 狀態轉換（loading → success/error），使用 `runTest` + `Dispatchers.setMain`。
- UseCase 測試：mock Repository，驗證業務邏輯分支（成功/失敗/邊界條件）。
- Repository 測試：mock 底層 DataSource（network/database/datastore），驗證資料協調與例外處理邏輯。

### 3. 清理樣板檔案的時機
- 在補上該模組第一個實質測試的同一個 PR 中，順手刪除該模組的 `ExampleUnitTest.kt`，而非另開一個大規模清理 PR，避免無意義的大範圍 diff 干擾 review。

## Risks / Trade-offs

- **[風險] Kover 對某些 Compose 產生的合成程式碼可能誤判覆蓋率** → 緩解：先在 `core/domain`、`core/data` 等無 Compose 依賴的模組驗證設定正確性，再逐步套用到 feature 模組，並視情況調整 exclude pattern。
- **[風險] 80% 門檻若設得過嚴，可能阻塞既有 CI（因為現有大量模組覆蓋率趨近 0%）** → 緩解：門檻設定分階段導入，先以「警告不阻擋」模式觀察實際數字，待 P0/P1 測試補齊後再切換為阻擋 CI 的硬性門檻。
- **[風險] ViewModel 測試涉及 Coroutine + StateFlow 非同步狀態，容易寫出 flaky test** → 緩解：統一使用 `kotlinx-coroutines-test` 的 `runTest`/`TestDispatcher`，避免真實 delay。
- **[取捨] core/database DAO 層本次不補（需要 Instrumented Test，執行成本高、CI 時間拉長）** → 之後可另立 change 規劃 Robolectric 或 in-memory Room 測試。

## Migration Plan

1. 在 `SharedLibraryGradlePlugin` 套用 Kover，先以觀察模式（不阻擋 build）驗證各模組可正常產生報表。
2. 依 proposal 的 P0 優先順序逐模組補測試並移除該模組 `ExampleUnitTest.kt`。
3. P0 全數完成後，補 P1（datastore）。
4. 確認核心模組（domain/data/feature ViewModel）達 80% 後，將 Kover 門檻切換為阻擋模式，並視需要接入 CI（`.github/workflows/release-apk.yml` 或新增 PR 檢查 workflow）。
5. 若門檻造成既有 CI 大量失敗且短期無法修復，可先以個別模組為單位設定門檻（而非全域統一 80%），逐步收斂。

## Open Questions

- 是否需要在 CI（GitHub Actions）加入覆蓋率報表上傳/PR 留言機制，或僅在本地 `./gradlew koverHtmlReport` 產出報表即可？（本次 change 先聚焦本地可驗證，CI 整合可視團隊需求另行決定）
- `core/database` DAO 層的 Instrumented Test 是否要排入本次範圍，或明確另立獨立 change？（本設計傾向另立，避免範圍膨脹）
