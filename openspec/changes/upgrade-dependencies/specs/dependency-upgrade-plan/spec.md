## ADDED Requirements

### Requirement: 依賴版本盤點與差距分析
系統（本專案之建置設定）SHALL 針對 `buildSrc/src/main/kotlin/deps/DependenciesVersions.kt` 中列出的每一項依賴，記錄目前版本、上游最新穩定版本、版本差距等級（patch / minor / major）。

#### Scenario: 產出依賴差距清單
- **WHEN** 對照 `DependenciesVersions.kt` 與各依賴官方最新穩定版
- **THEN** 產出包含「目前版本、最新版本、差距等級、風險備註」四個欄位的清單（見 design.md 版本比對表）

### Requirement: 逐一升級順序規劃
系統 SHALL 依照風險等級與相依關係（如 Kotlin/KSP/AGP/Compose Compiler 需搭配同步）將升級項目分批排序，且每一批中的每個依賴 SHALL 可被獨立升級、獨立驗證、獨立提交，不強制與其他依賴同批次變更。

#### Scenario: 使用者依序執行單一依賴升級
- **WHEN** 使用者依 tasks.md 選擇其中一個依賴進行升級
- **THEN** 該任務只需修改該依賴對應的版本常數（`DependenciesVersions` → `Dependencies` → `DependenciesProvider`），不強制連動修改其他未排入同批次的依賴

#### Scenario: 有相依關係的依賴需同批次處理
- **WHEN** 使用者要升級 Kotlin 版本
- **THEN** 任務清單 SHALL 同時提示需檢查並可能同步升級 KSP 版本與 Compose Compiler 對應版本，避免版本不相容導致建置失敗

### Requirement: 升級驗證方式
每一項依賴升級任務 SHALL 明確定義驗證步驟，至少包含 `./gradlew ktlintCheck`、`./gradlew test`、`./gradlew assembleDebug`（或受影響 module 的對應建置指令）。

#### Scenario: 驗證未通過時的處理
- **WHEN** 升級某依賴後 `./gradlew test` 或 `./gradlew ktlintCheck` 失敗
- **THEN** 該次升級 SHALL 被視為未完成，需修正相容性問題或回退版本後才可標記任務完成

### Requirement: 高風險項目標示
差距達到 major 版本、或存在已知 breaking change（例如 Retrofit 2→3、OkHttp 4→5、AGP 8→9、Room 2→3、`kotlinx-coroutines-android` 版本落差過大）的依賴 SHALL 在計畫中明確標示為高風險項目，並與低風險 patch/minor 項目分開排序，優先安排在後段執行。

#### Scenario: 高風險依賴獨立成任務
- **WHEN** tasks.md 列出 major 版本升級項目
- **THEN** 該項目 SHALL 是獨立任務，且任務描述中需包含「需額外評估程式碼相容性」的提示，不與其他 patch/minor 任務合併
