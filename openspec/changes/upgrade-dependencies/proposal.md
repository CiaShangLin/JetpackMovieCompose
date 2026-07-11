## Why

專案的核心依賴（AGP、Kotlin、Compose BOM、Hilt、Room、Retrofit/OkHttp、Coil、Navigation、Paging、WorkManager 等）已落後上游穩定版本數個 minor/major release，部分依賴差距懸殊（例如 `kotlinx-coroutines-android` 停留在 1.3.9，而專案其餘部分已使用 Kotlin 2.0.21 / `kotlinx-coroutines-test` 1.8.0，版本落差超過 4 年）。落後的依賴會累積相容性風險、錯過效能與安全性修補，且未來一次性大幅升級的成本與風險遠高於現在逐一處理。使用者希望「一個一個升級」以降低單次變更風險並便於個別驗證，因此需要一份依序排列、附風險評估的升級計畫。

## What Changes

- 盤點 `buildSrc/src/main/kotlin/deps/DependenciesVersions.kt` 中列出的全部依賴版本，對照上游最新穩定版，產出差距與風險分級（低風險 patch/minor、高風險 major/breaking）
- 依風險與相依順序排出**逐一升級**的順序建議（例如：先升危害最小的 patch/minor 版本，再處理有 breaking change 的 major 版本；有相依關係的套件〔如 Kotlin ↔ KSP ↔ Compose Compiler〕綁定同批次處理）
- 針對每個待升級依賴，記錄目前版本、建議目標版本、升級類型（patch/minor/major）、已知 breaking changes 與驗證方式
- **BREAKING**（規劃項目，非本次直接執行）：標示出需要程式碼配合修改的 major 升級，例如：
  - AGP 8.9.1 → 9.x：需檢查 AGP Upgrade Assistant 相容性、Gradle 版本需求
  - Retrofit 2.11.0 → 3.0.0：API 介面可能變動
  - OkHttp 4.12.0 → 5.x：artifact 拆分（JVM / Android）
  - Room 2.6.1 → 3.0（如採用）：全面轉為 Coroutine API、強制 KSP
- 本次變更僅產出**升級分析與逐步執行計畫**，實際版本號修改與程式碼調整將依 tasks.md 逐項在後續個別執行（避免一次性大爆炸式升級）

## Capabilities

### New Capabilities
- `dependency-upgrade-plan`: 定義本專案依賴升級的分析結果、排序原則與逐一升級的執行規範（每個依賴一個獨立任務、升級後需通過 `ktlintCheck`/`test`/`assembleDebug` 才算完成）

### Modified Capabilities
(無，本次不變更既有功能性 spec)

## Impact

- **受影響檔案**：`buildSrc/src/main/kotlin/deps/DependenciesVersions.kt`、`buildSrc/src/main/kotlin/deps/Dependencies.kt`、`buildSrc/build.gradle.kts`（AGP / Kotlin gradle plugin / KSP plugin 版本）
- **受影響模組**：全部套用 `SharedLibraryGradlePlugin` 的模組（`core/*`、`feature/*`）與 `app` 皆會因版本變動需要重新建置驗證；Compose 相關模組（`core/designsystem`、`core/ui`、`feature/*`）對 Compose BOM / Compiler 升級最敏感；`core/network` 對 Retrofit/OkHttp/kotlinx-serialization 升級最敏感；`core/database` 對 Room 升級最敏感
- **不影響**：本次不新增依賴、不變更既有 API 行為（規劃階段），僅盤點與規劃
