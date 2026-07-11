## 1. 第一批：低風險 Patch/Minor 依賴（獨立、無已知 breaking change）

- [ ] 1.1 升級 Lottie Compose（目前 6.6.6 → 查詢當下最新 6.7.x）：更新 `DependenciesVersions.LOTTIE_COMPOSE`，執行 `./gradlew ktlintCheck test assembleDebug`，並手動確認動畫仍正常播放（受影響：使用 Lottie 的畫面，如 loading/empty state）
- [ ] 1.2 升級 Chucker（目前 4.1.0 → 查詢當下最新 4.x patch）：更新 `DependenciesVersions.CHUCKER`，確認僅影響 debug/QA 建置類型，執行 `./gradlew ktlintCheck test`
- [ ] 1.3 升級 Coil（目前 3.2.0 → 查詢當下最新 3.5.x）：更新 `DependenciesVersions.COIL`，執行 `./gradlew ktlintCheck test assembleDebug`，手動確認圖片載入（含 `core/ui` 的 `HostInterceptor`）正常
- [ ] 1.4 升級 Paging（目前 3.3.6 → 查詢當下最新 3.5.x）：更新 `DependenciesVersions.PAGING`，執行 `./gradlew ktlintCheck test`，確認分頁列表（如 home/search）滾動與載入正常
- [ ] 1.5 升級 DataStore（目前 1.1.1 → 查詢當下最新版）：更新 `DependenciesVersions.DATA_STORE`，執行 `./gradlew ktlintCheck test`，確認 `core/datastore` 偏好設定讀寫與既有 Proto schema 相容
- [ ] 1.6 升級 Protobuf java/kotlin（目前 4.27.3 → 查詢當下最新版）：更新 `DependenciesVersions.PROTO_BUF_JAVA`、`PROTO_BUF_KOTLIN`，確認 `core/datastore` 的 proto 產生程式碼仍可正常編譯

## 2. 第二批：落差較大但仍屬 Minor 的依賴（含相依鏈確認）

- [ ] 2.1 升級 `kotlinx-coroutines-android`（目前 1.3.9，落差極大 → 對齊或高於 `KOTLINX_COROUTINES_TEST` 目前的 1.8.0，查詢當下最新穩定版）：更新 `DependenciesVersions.KOTLIN_COROUTINES`（`Dependencies.kt:75` 使用處），全面重跑 `core/domain`、各 ViewModel 的既有測試，特別檢查 `CoroutineScope`/`Flow` 相關行為未變更
- [ ] 2.2 升級 WorkManager（目前 2.7.1 → 查詢當下最新 2.11.x）：更新 `DependenciesVersions.WORK`，確認背景工作（若有使用）排程行為未變
- [ ] 2.3 升級 Hilt（目前 2.51.1 → 查詢當下最新 2.5x.x）：更新 `DependenciesVersions.HILT`，同步檢查 `HILT_COMPOSE`、`HILT_NAVIGATION_COMPOSE` 是否有對應新版本，執行全模組 `./gradlew build` 確認 DI graph 產生無誤
- [ ] 2.4 升級 kotlinx-serialization（目前 1.6.2 → 查詢當下最新版）：更新 `DependenciesVersions.KOTLIN_SERIALIZATIONS`，確認 `core/network` 的 JSON 序列化/反序列化測試全數通過
- [ ] 2.5 升級 Navigation Compose（目前 2.9.0 → 2.9.8，**注意**：`navigation-compose` 沒有 3.0 版本，別跟全新的 Navigation 3 套件搞混，見任務 3.6）：更新 `DependenciesVersions.NAVIGATION`，手動驗證底部導航五個分頁與 `moviedetail`/`actor` 非底部導航頁面跳轉正常
- [ ] 2.6 升級 Room（2.x 維護版，目前 2.6.1 → 2.8.x，**不含** 3.0 major 版本）：更新 `DependenciesVersions.ROOM`，執行 `core/database` 既有測試，確認既有 schema 與 DAO 查詢正常，若有 schema export 需檢查是否需要新增 migration
- [ ] 2.7 移除 Gson（非升級——已查明 `GsonConverterFactory` 從未註冊到 `Retrofit.Builder`，實際序列化全由 kotlinx.serialization 負責）：
  - 移除 `buildSrc` 中 `Dependencies.googleJson`、`Dependencies.retrofitConverterGson` 兩個常數與其在 `DependenciesProvider.kt`（`androidx()`、`retrofit()`）的引用，並移除 `DependenciesVersions.GOOGLE_GSON`
  - 修正 `core/network/src/main/java/com/shang/network/extension/NetworkExtension.kt` 的 `safeApiCall`：將 `catch (e: JsonParseException)` 改為 `catch (e: SerializationException)`（`kotlinx.serialization.SerializationException`），修正目前解析失敗會誤落入 `UnknownError` 而非 `ParseError` 的既有 bug
  - 同步更新 `core/network/src/test/java/com/shang/network/extension/NetworkExtensionTest.kt` 對應測試案例
  - 執行 `./gradlew ktlintCheck test` 確認全模組編譯與測試通過（移除後需確認沒有其他檔案仍隱性依賴 `com.google.gson.*`）

## 3. 第三批：Major 版本升級（需個別評估，需另行確認 breaking change 細節）

- [ ] 3.1 評估並升級 AGP（目前 8.9.1 → 查詢當下最新 9.x）：先於官方頁面確認相容性矩陣與 Gradle 版本需求，使用 Android Studio 的 AGP Upgrade Assistant 輔助，更新 `buildSrc/build.gradle.kts` 中 `com.android.tools.build:gradle` 版本，全模組 `./gradlew build` 驗證
- [ ] 3.2 評估並升級 Kotlin gradle-plugin / KSP（需與 3.1 AGP 版本搭配，目前 Kotlin 2.0.21 / KSP 2.0.21-1.0.25）：確認目標 AGP 版本官方支援的 Kotlin 版本範圍後，同步更新 `buildSrc/build.gradle.kts` 的 `kotlin("gradle-plugin:...")`、KSP plugin 版本，以及 `DependenciesVersions.KOTLIN_COMPILER`、`COMPILER`（Compose Compiler 對應版本）
- [ ] 3.3 評估並升級 Retrofit（目前 2.11.0 → 查詢當下最新 3.0.x）：查閱官方 migration guide，確認 `core/network` 的 API 介面定義、`RETROFIT_COROUTINE_ADAPTER_VERSION`（Sandwich 相關）是否仍相容，更新 `DependenciesVersions.RETROFIT`，全面重跑 `core/network` 測試
- [ ] 3.4 評估並升級 OkHttp（目前 4.12.0 → 查詢當下最新 5.x，需與 3.3 Retrofit 版本搭配確認相容性）：更新 `DependenciesVersions.OKHTTP`，確認 `ApiKeyInterceptor`、`LanguageInterceptor`、Chucker 攔截器與新 artifact 拆分方式相容
- [ ] 3.5（選用，待使用者決定是否採用）評估 Room 3.0 全面轉換：全面改為 Coroutine API、強制 KSP，需重新設計 DAO 與 migration 策略，建議另立獨立 change 處理，不在本次逐一升級範圍內直接執行
- [ ] 3.6（選用，待使用者決定是否採用）評估遷移到 Navigation 3（`androidx.navigation3.*`，全新套件，非 `navigation-compose` 的版號延續）：評估 `NavDisplay`/entry-based API 與現有 `navigation/` 目錄下各模組 ROUTE 常數、`NavGraphBuilder` 擴充函式寫法的差異與遷移成本，建議另立獨立 change 處理，不在本次逐一升級範圍內直接執行

## 4. 收尾

- [ ] 4.1 每完成一個依賴升級任務後，於 commit message 中標明「升級 <依賴名稱> <舊版本> → <新版本>」，方便個別追蹤與 revert
- [ ] 4.2 全部三批任務完成後，執行一次完整回歸：`./gradlew check`（含 lint、test、ktlint）與 `./gradlew assembleProdRelease`，確認正式版建置與簽名流程未受影響
