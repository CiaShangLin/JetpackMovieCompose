# unit-test-coverage

## Purpose

定義專案單元測試覆蓋率量測機制（Jacoco/Kover 設定與門檻）與各模組最低覆蓋率要求的規範，確保 `core/domain`、`core/data` 與各 feature 模組的 ViewModel 等核心業務邏輯層具備可量化驗證的測試涵蓋，並維持測試撰寫風格一致、移除無效樣板測試。

## Requirements

### Requirement: 覆蓋率量測機制
專案 SHALL 為所有套用 `SharedLibraryGradlePlugin` 的模組（`core/*`、`feature/*`）提供統一的單元測試覆蓋率量測機制，並可產出可讀報表。

#### Scenario: 執行覆蓋率報表產出指令
- **WHEN** 開發者在任一套用 `SharedLibraryGradlePlugin` 的模組執行覆蓋率報表產出指令
- **THEN** 系統 SHALL 產出該模組的行覆蓋率（line coverage）報表，且報表內容需可看出各類別的覆蓋率數字

#### Scenario: 覆蓋率設定排除不需量測的產生碼
- **WHEN** 系統計算覆蓋率
- **THEN** 系統 SHALL 排除 Hilt 產生的合成程式碼（`*_Factory`、`*_HiltModules`、`Hilt_*`）、DI Module（`*Module.kt`）與純介面定義，避免這些非業務邏輯程式碼稀釋覆蓋率數字

### Requirement: 核心業務邏輯層最低覆蓋率門檻
`core/domain` 的 Use Case、`core/data` 的 Repository 實作、以及各 feature 模組的 ViewModel SHALL 達到至少 80% 行覆蓋率。

#### Scenario: Use Case 覆蓋率達標
- **WHEN** 計算 `core/domain` 模組的覆蓋率報表
- **THEN** 該模組所有 Use Case 類別的平均行覆蓋率 SHALL 不低於 80%

#### Scenario: Repository 實作覆蓋率達標
- **WHEN** 計算 `core/data` 模組的覆蓋率報表
- **THEN** `MovieRepositoryImp`、`UserDataRepositoryImp` 的行覆蓋率 SHALL 不低於 80%

#### Scenario: ViewModel 覆蓋率達標
- **WHEN** 計算任一 feature 模組（home、search、moviedetail、collect、history、setting）的覆蓋率報表
- **THEN** 該模組 ViewModel 類別的行覆蓋率 SHALL 不低於 80%

### Requirement: 測試撰寫風格一致性
新增的單元測試 SHALL 採用專案既有的 JUnit5 + Strikt + MockK + Coroutines Test 組合，並遵循 AAA（Arrange/Act/Assert）模式撰寫。

#### Scenario: 新增測試檔案符合既有風格
- **WHEN** 在任一模組新增單元測試檔案
- **THEN** 該測試 SHALL 使用 `org.junit.jupiter.api` 的 JUnit5 標註、Strikt 進行斷言、MockK 進行模擬，且測試內容依序區分 Arrange/Act/Assert 三個階段

### Requirement: 移除無效樣板測試
專案 SHALL 移除已補上實質測試之模組中的空樣板檔案 `ExampleUnitTest.kt`，避免其被誤判為該模組已有測試涵蓋。

#### Scenario: 模組補上第一個實質測試後清理樣板
- **WHEN** 某模組新增了第一個具備實質斷言的單元測試
- **THEN** 該模組的 `ExampleUnitTest.kt` SHALL 於同一變更中被移除
