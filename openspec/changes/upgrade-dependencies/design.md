## Context

`buildSrc/src/main/kotlin/deps/DependenciesVersions.kt` 是專案唯一的依賴版本來源，所有 `core/*`、`feature/*` 模組透過 `SharedLibraryGradlePlugin` 與 `DependenciesProvider` 的擴充函式取得依賴，因此版本升級的變更面集中、可控。

以下為現況版本與（截至查詢時）上游最新穩定版的比對（Context7 在本環境未配置，改以官方 Release Notes / Maven Central 搜尋確認；實際執行升級時請以 `openspec/changes/upgrade-dependencies/tasks.md` 執行當下的官方頁面版本為準，因版本會持續推進）：

| 依賴 | 目前版本 | 最新穩定版（查詢時） | 差距等級 | 備註 |
|---|---|---|---|---|
| Android Gradle Plugin (AGP) | 8.9.1 | 9.2.0 | **Major** | 9.x 起強制 KSP（kapt 進入維護模式）、需 Gradle 8.11+，需跑 Upgrade Assistant |
| Kotlin (gradle-plugin / compiler) | 2.0.21 | 2.4.0 | **Major/Minor** | AGP 9.2 才完全穩定支援 Kotlin 2.1.x，建議與 AGP 升級節奏搭配，不要一次跳到 2.4 |
| KSP | 2.0.21-1.0.25 | 需對齊所升級之 Kotlin 版本 | 綁定 | 必須與 Kotlin 版本成對升級 |
| Compose BOM | 2025.05.00 | 2026.04.01（新近另有 2026.06.01） | Minor（BOM 內各 artifact 已多次 minor 更新） | v2 testing API 成為預設，v1 API 已標記淘汰，需檢查測試程式碼 |
| Material3 / Compose 核心 artifacts | 1.3.2 / 1.8.3 | 隨 BOM 對齊 | Minor | 由 BOM 統一管理，不需手動指定個別版本號 |
| Hilt (`hilt-android` / compiler) | 2.51.1 | 2.59.2 | Minor | 需留意 Hilt Gradle Plugin 對 Gradle 9 相容性議題（近期已修） |
| Room | 2.6.1 | 2.8.4（2.x 維護版）或 3.0.0（新主版本） | **Minor 可先做 / Major 需評估** | Room 3.0 全面轉 Coroutine API 且強制 KSP，屬於架構級變更，建議先升 2.8.4，3.0 另立獨立任務評估 |
| Paging | 3.3.6 | 3.5.0 | Minor | |
| Navigation Compose | 2.9.0 | 2.9.8 | Minor | `androidx.navigation:navigation-compose` 本身**沒有** 3.0 版本；「Navigation 3」是 Google 另外發的新套件（`androidx.navigation3.*`），採全新的 entry-based API（`NavDisplay` 取代 `NavHost`），近期才轉為 stable，屬於**架構級遷移**而非版本號升級，不能直接改版本號套用。是否遷移到 Navigation 3 待使用者於第三批 major 項目中另行評估 |
| Retrofit | 2.11.0 | 3.0.0 | **Major** | 2.x → 3.0 API 介面可能異動，需查閱 migration guide |
| OkHttp | 4.12.0 | 5.0.x | **Major** | 5.x 起拆分 JVM / Android artifact，Retrofit 3.0 對應的 OkHttp 版本需一併確認相容性 |
| Coil | 3.2.0 | 3.5.0 | Minor | 已在 Coil 3.x 主線，屬同大版本內升級，風險低 |
| kotlinx-coroutines-android | **1.3.9** | 對齊 `KOTLINX_COROUTINES_TEST`（目前 1.8.0）之後的最新穩定版 | **極高風險** | 現況版本落後專案其餘 coroutines 相關設定超過 4 年，屬於本次分析中風險最高、應優先處理的項目 |
| kotlinx-serialization | 1.6.2 | 依官方頁面查詢當下最新 1.7.x/1.8.x 為準 | Minor | |
| DataStore | 1.1.1 | 依官方頁面查詢當下最新版為準 | Minor | Proto DataStore 用於偏好設定，需驗證 schema 相容性 |
| WorkManager | 2.7.1 | 2.11.1 | **Minor 但落差大** | 落後多個 minor 版本，累積修補多，建議提前處理 |
| Chucker | 4.1.0 | 4.x 最新 patch | Patch/Minor | 僅開發階段使用，風險低 |
| Lottie Compose | 6.6.6 | 6.7.1 | Patch | 風險低 |
| Protobuf (java/kotlin) | 4.27.3 | 依官方頁面查詢當下最新版為準 | Minor | |
| Gson | 2.11.0 | （建議移除，非升級） | — | 已查明：Retrofit 實際只註冊 `kotlinx.serialization` 的 `asConverterFactory`（`core/network/di/NetworkModule.kt:88`），`GsonConverterFactory` 從未被加入 `Retrofit.Builder`；Gson 唯一的實際用途是 `core/network/extension/NetworkExtension.kt` 的 `safeApiCall` 捕捉 `com.google.gson.JsonParseException`。但因實際解析走的是 kotlinx.serialization，解析失敗會丟出 `kotlinx.serialization.SerializationException`（非 `JsonParseException`），該 catch 分支實質上**永遠不會命中**，錯誤會落入最後的 `catch (e: Exception)` 被歸類為 `UnknownError` 而非 `ParseError`——這是一個既有 bug。建議：移除 `Dependencies.googleJson`、`Dependencies.retrofitConverterGson` 兩個依賴，並將 `NetworkExtension.kt`／對應測試中的 `catch (e: JsonParseException)` 改為 `catch (e: SerializationException)` |

## Goals / Non-Goals

**Goals:**
- 產出一份可被逐一執行的依賴升級順序與風險分級，讓使用者能一個一個安全地升級
- 明確標示哪些屬於低風險 patch/minor（可直接升）、哪些屬於高風險 major（需額外評估與程式碼調整）
- 找出目前設定中已存在的隱藏風險（如 `kotlinx-coroutines-android` 版本落後）並標示為高優先項目
- 提供每個升級項目的驗證方式（`ktlintCheck` / `test` / `assembleDebug` / 手動 smoke test）

**Non-Goals:**
- 本次不在這個 change 內一次性修改所有版本號；每個依賴的實際版本變更留待使用者依 tasks.md 逐一在後續 session/PR 執行
- 不處理 Room 3.0、Retrofit 3.0、OkHttp 5.x、AGP 9.x 這類需要程式碼配合修改的 major 升級的實作細節（僅標示與規劃，實作應在各自獨立的後續 change 中處理）
- 不引入 Context7 MCP 設定（環境未提供，本次以官方文件/Release Notes 搜尋替代）

## Decisions

1. **升級順序：低風險優先，依相依關係分批**
   - 第一批（獨立、低風險 patch/minor）：Lottie Compose、Chucker、Coil、Paging、DataStore、Protobuf
   - 第二批（有明顯落差但仍是 minor，建議提前處理以降低技術債累積）：`kotlinx-coroutines-android`（1.3.9 → 對齊專案其他 coroutines 版本，風險評級雖標示高但升級動作本身是 minor bump，只是落差大）、WorkManager、Hilt、kotlinx-serialization、Navigation Compose、Room（2.x 內）
   - 第三批（major，需個別評估與程式碼修改，各自成獨立任務）：AGP 8→9、Kotlin 2.0→2.x（需與 AGP 搭配）、Retrofit 2→3、OkHttp 4→5、Room 2→3（可選）
   - 理由：先清除大量低風險項目建立信心與驗證流程，再處理有相依鏈（Kotlin/KSP/AGP/Compose Compiler 需同步）的項目，最後才動 API 介面可能改變的 major 版本，降低單次變更的除錯範圍

2. **版本來源集中於 buildSrc，每次只改一個依賴對應的常數**
   - 遵循專案既有規範：`DependenciesVersions` → `Dependencies` → `DependenciesProvider`
   - 每個依賴升級為一個獨立 commit / PR，方便單獨 revert

3. **不在本次 change 一次做完所有升級**
   - 因使用者明確表示「一個一個升級」，本 change 的產出是**分析與計畫**（tasks.md 中每個依賴是一個獨立任務項目，供後續逐一勾選執行），而非一次性程式碼變更

## Risks / Trade-offs

- [風險] AGP 9.x 與 Kotlin 版本搭配複雜，過早升級 Kotlin 可能與 AGP 8.9.1 不相容 → 緩解：依 design 中「先 AGP 後 Kotlin」或「兩者同批次」處理，執行前查閱 AGP 官方相容性矩陣
- [風險] Room 2.6.1 → 3.0 屬於架構級改動（全面 Coroutine API、強制 KSP）→ 緩解：先停留在 Room 2.8.x 維護版，Room 3.0 獨立評估，不納入本次逐一升級範圍
- [風險] Retrofit 3.0 / OkHttp 5.x 為 major 版本，可能有 API 破壞性變更影響 `core/network` 的攔截器（`ApiKeyInterceptor`、`LanguageInterceptor`）→ 緩解：升級後需針對 `core/network` 既有測試（攔截器、`NetworkResponse`、`MovieDataSourceImp`）全數重跑
- [風險] `kotlinx-coroutines-android` 版本落差過大（1.3.9 → 現代版本），可能一次升級要跨越多個行為變更 → 緩解：升級後需針對所有使用 `CoroutineScope`、`Flow` 的 Use Case 與 ViewModel 進行迴歸測試，優先在小範圍模組（如 `core/domain`）驗證
- [風險] 查詢到的版本號可能因時間推移而過期（依賴生態更新快）→ 緩解：tasks.md 中每個任務要求「執行當下重新確認官方最新版本」，不直接寫死本文件中的版本號作為最終依據

## Migration Plan

- 無資料庫 schema 或執行期 migration；此變更僅涉及建置期依賴版本
- 若後續執行 Room major 升級，需另外設計 Room migration 策略（不在本次範圍）

## Open Questions

- 是否要跟進 Room 3.0 / Retrofit 3.0 / OkHttp 5.x 這類 major 升級，或維持在對應的 2.x/4.x 維護分支？待使用者在逐一升級到該項目時再決定
- 是否要遷移到 Navigation 3（`androidx.navigation3.*`）？這是全新套件與 API，非現有 `navigation-compose` 的版本延續，需獨立評估遷移成本，待使用者決定
- 本環境未配置 Context7 MCP，若使用者希望之後改用 Context7 查詢函式庫文件，需另外設定 MCP server
