## 1. 覆蓋率工具導入（buildSrc）

- [ ] 1.1 於 `buildSrc/src/main/kotlin/deps/DependenciesVersions.kt`、`Dependencies.kt`、`DependenciesProvider.kt` 新增 Kover plugin 座標與版本
- [ ] 1.2 於 `buildSrc/src/main/kotlin/plugs/SharedLibraryGradlePlugin.kt` 套用 Kover，設定排除規則（Hilt 產生碼 `*_Factory`/`*_HiltModules`/`Hilt_*`、`*Module.kt`、純介面）
- [ ] 1.3 於 `core/domain`、`core/data` 兩個無 Compose 依賴的模組驗證 Kover 報表可正常產出（觀察模式，不阻擋 build）
- [ ] 1.4 逐步套用到 feature 模組並確認 Compose 相關檔案未被誤計入覆蓋率

## 2. core/domain：補齊 Use Case 測試

- [ ] 2.1 新增 `GetConfigurationUseCaseTest`
- [ ] 2.2 新增 `GetHistoryMovieListUseCaseTest`
- [ ] 2.3 新增 `GetHomeMovieListUseCaseTest`
- [ ] 2.4 新增 `GetMovieDetailUseCaseTest`
- [ ] 2.5 新增 `GetMovieRecommendUseCaseTest`
- [ ] 2.6 移除 `core/domain/src/test/**/ExampleUnitTest.kt`

## 3. core/data：補齊 Repository 測試

- [ ] 3.1 新增 `MovieRepositoryImpTest`（mock network/database/datastore 資料來源，驗證資料協調與例外處理邏輯）
- [ ] 3.2 新增 `UserDataRepositoryImpTest`
- [ ] 3.3 移除 `core/data/src/test/**/ExampleUnitTest.kt`

## 4. feature/*：補齊 ViewModel 測試

- [ ] 4.1 新增 `app` 模組 `MainViewModelTest`，移除對應 `ExampleUnitTest.kt`
- [ ] 4.2 新增 `feature/home` 的 `HomeViewModelTest`、`HomeContentViewModelTest`，移除對應 `ExampleUnitTest.kt`
- [ ] 4.3 新增 `feature/moviedetail` 的 `MovieDetailViewModelTest`，移除對應 `ExampleUnitTest.kt`
- [ ] 4.4 新增 `feature/collect` 的 `CollectViewModelTest`，移除對應 `ExampleUnitTest.kt`
- [ ] 4.5 新增 `feature/history` 的 `HistoryViewModelTest`，移除對應 `ExampleUnitTest.kt`
- [ ] 4.6 新增 `feature/setting` 的 `SettingViewModelTest`，移除對應 `ExampleUnitTest.kt`

## 5. core/datastore：補齊 Mapper / Provider 測試

- [ ] 5.1 新增 `ConfigurationMapperTest`
- [ ] 5.2 新增 `BaseHostUrlProviderImplTest`
- [ ] 5.3 新增 `LanguageProviderImplTest`
- [ ] 5.4 移除 `core/datastore/src/test/**/ExampleUnitTest.kt`

## 6. 覆蓋率門檻驗證與收尾

- [ ] 6.1 執行各模組 Kover 報表，確認 `core/domain`、`core/data`、各 feature ViewModel 的行覆蓋率達到 80%
- [ ] 6.2 將 Kover 門檻由觀察模式切換為阻擋模式（build 失敗於未達門檻時）
- [ ] 6.3 執行 `./gradlew ktlintCheck` 與 `./gradlew test` 確認全數通過
- [ ] 6.4 更新 `CLAUDE.md`（如有必要）補充 Kover 報表產出指令說明
