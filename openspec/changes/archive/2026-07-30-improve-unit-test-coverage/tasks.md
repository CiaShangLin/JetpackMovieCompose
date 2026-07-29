## 1. 覆蓋率工具導入（buildSrc）

- [x] 1.1 於 `buildSrc/src/main/kotlin/deps/DependenciesVersions.kt`（`KOVER` 版本常數）、`buildSrc/build.gradle.kts`（classpath 依賴）、`plugs/BuildPlugins.kt`（`KOVER` plugin id）新增 Kover 座標與版本
- [x] 1.2 於 `buildSrc/src/main/kotlin/plugs/SharedLibraryGradlePlugin.kt` 套用 Kover，設定排除規則（Hilt 產生碼 `*_Factory`/`*_HiltModules`/`Hilt_*`、`*Module`、`BuildConfig`），並以 `gradle.properties` 的 `kover.verify.enabled`（預設 false）控制門檻是否阻擋 build
- [x] 1.3 於 `core/domain`、`core/data` 驗證 `koverHtmlReport`／`koverVerify` 可正常執行且不阻擋 build（觀察模式）
- [x] 1.4 於 `feature/home`（含 Compose）驗證 Kover 報表可正常產出

## 2. core/domain：補齊 Use Case 測試

- [x] 2.1 新增 `GetConfigurationUseCaseTest`
- [x] 2.2 新增 `GetHistoryMovieListUseCaseTest`
- [x] 2.3 新增 `GetHomeMovieListUseCaseTest`
- [x] 2.4 新增 `GetMovieDetailUseCaseTest`
- [x] 2.5 新增 `GetMovieRecommendUseCaseTest`
- [x] 2.6 移除 `core/domain/src/test/**/ExampleUnitTest.kt`（core/domain 行覆蓋率達 100%）

## 3. core/data：補齊 Repository 測試

- [x] 3.1 新增 `MovieRepositoryImpTest`（mock network/database 資料來源，驗證資料協調與例外處理邏輯，行覆蓋率 94.7%）
- [x] 3.2 新增 `UserDataRepositoryImpTest`（行覆蓋率 100%）
- [x] 3.3 移除 `core/data/src/test/**/ExampleUnitTest.kt`

## 4. feature/*：補齊 ViewModel 測試

- [x] 4.1 新增 `app` 模組 `MainViewModelTest`（行覆蓋率 100%；補上 `app/build.gradle.kts` 的 JUnit5 `testOptions` 與 Kover 套用，因 app 非 library 模組不會經 `SharedLibraryGradlePlugin` 自動取得），移除對應 `ExampleUnitTest.kt`
- [x] 4.2 新增 `feature/home` 的 `HomeViewModelTest`（行覆蓋率 100%）、`HomeContentViewModelTest`（行覆蓋率 88.9%），移除對應 `ExampleUnitTest.kt`
- [x] 4.3 新增 `feature/moviedetail` 的 `MovieDetailViewModelTest`（行覆蓋率 97.8%），移除對應 `ExampleUnitTest.kt`
- [x] 4.4 新增 `feature/collect` 的 `CollectViewModelTest`（行覆蓋率 100%），移除對應 `ExampleUnitTest.kt`
- [x] 4.5 新增 `feature/history` 的 `HistoryViewModelTest`（行覆蓋率 100%），移除對應 `ExampleUnitTest.kt`
- [x] 4.6 新增 `feature/setting` 的 `SettingViewModelTest`（行覆蓋率 100%），移除對應 `ExampleUnitTest.kt`

## 5. core/datastore：補齊 Mapper / Provider 測試

- [x] 5.1 新增 `ConfigurationMapperTest`（行覆蓋率 100%）
- [x] 5.2 新增 `BaseHostUrlProviderImplTest`（行覆蓋率 100%）
- [x] 5.3 新增 `LanguageProviderImplTest`（行覆蓋率 92.3%）
- [x] 5.4 移除 `core/datastore/src/test/**/ExampleUnitTest.kt`

## 6. 覆蓋率門檻驗證與收尾

- [x] 6.1 執行各模組 Kover 報表，確認 `core/domain`（100%）、`core/data`（MovieRepositoryImp 94.7%／UserDataRepositoryImp 100%）、`core/datastore`（ConfigurationMapper 100%／BaseHostUrlProviderImpl 100%／LanguageProviderImpl 92.3%）、各 feature ViewModel（MainViewModel 100%、HomeViewModel 100%、HomeContentViewModel 88.9%、MovieDetailViewModel 97.8%、CollectViewModel 100%、HistoryViewModel 100%、SettingViewModel 100%）的行覆蓋率均達 80% 以上
- [x] 6.2 嘗試將 `gradle.properties` 的 `kover.verify.enabled` 切換為 `true`（阻擋模式），實測發現 Kover 門檻是以「整個模組」彙總計算，會被同模組中未涵蓋的 Compose UI／DAO／DI 等程式碼拖累而全面 FAIL（例如 `core:common` 彙總覆蓋率 0%），故依 design.md 風險段落的因應方案：暫時恢復為 `false`（觀察模式），全域統一 80% 門檻的阻擋模式留待後續 change 規劃「以個別模組/類別為單位設定門檻」後再啟用
- [x] 6.3 執行 `./gradlew ktlintFormat`（專案未定義 `ktlintCheck` 任務，實際對應指令為 `ktlintFormat`）與 `./gradlew test`，全數通過
- [x] 6.4 `CLAUDE.md` 暫不更動；Kover 報表產出指令（`./gradlew :<module>:koverHtmlReport`）與 `kover.verify.enabled` 開關已於本次新增的 `gradle.properties` 註解與 design.md 中說明，待未來全域啟用阻擋模式時再回頭補充至 CLAUDE.md
