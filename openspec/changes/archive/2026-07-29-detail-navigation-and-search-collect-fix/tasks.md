## 1. core/domain：新增 GetMovieSearchUseCase

- [x] 1.1 在 `core/domain/src/main/kotlin/com/shang/domain/usecase/` 新增 `GetMovieSearchUseCase.kt`，仿照 `GetHomeMovieListUseCase` 的寫法：`operator fun invoke(query: String, scope: CoroutineScope): Flow<PagingData<MovieCardResult>>`，內部對 `movieRepository.getMovieSearchPager(query)` 依序套用 `flowOn(ioDispatcher)`、`cachedIn(scope)`，再與 `movieRepository.getCollectedMovieIds().map { it.toSet() }.flowOn(ioDispatcher)` 用 `combine` 組合，`map { movie -> movie.copy(isCollect = collectIds.contains(movie.id)) }`。
- [x] 1.2 為 `GetMovieSearchUseCase` 撰寫單元測試（`core/domain` 測試目錄，JUnit 5 + MockK + Strikt + Coroutines Test），驗證：(a) 搜尋結果的 `isCollect` 會依 `getCollectedMovieIds()` 正確標記；(b) 收藏狀態變動只更新對應項目、不觸發整頁重新載入的 combine 邏輯正確性；覆蓋率達最低 80%。

## 2. feature/search：串接 UseCase 與收藏切換

- [x] 2.1 修改 `SearchViewModel.kt`：注入 `GetMovieSearchUseCase`，將 `movieSearchPager` 改為呼叫 `getMovieSearchUseCase(query, viewModelScope)`，取代原本直接呼叫 `movieRepository.getMovieSearchPager(query).cachedIn(viewModelScope)` 的寫法。
- [x] 2.2 在 `SearchViewModel.kt` 新增 `toggleMovieCollectStatus(data: MovieCardData)` 方法，比照 `HomeContentViewModel` / `HistoryViewModel` 現有寫法呼叫 `movieRepository.insertMovieCollect` / `deleteMovieCollect`。
- [x] 2.3 修改 `SearchScreen.kt`：在 `SearchResultScreen` 的 `MovieCard(...)` 呼叫處補上 `onCollectClick = { viewModel.toggleMovieCollectStatus(it) }`。
- [x] 2.4 為 `SearchViewModel` 新增/更新單元測試，涵蓋 `toggleMovieCollectStatus` 的加入/移除收藏兩種情境；覆蓋率達最低 80%。

## 3. feature/collect、feature/history、feature/search：補齊 onMovieClick 導航

- [x] 3.1 修改 `feature/collect/.../navigation/CollectNavigation.kt`：`collectScreen()` 新增必要參數 `onMovieClick: (Int) -> Unit`，並傳入 `CollectScreen(onMovieClick = onMovieClick)`。
- [x] 3.2 修改 `feature/collect/.../ui/CollectScreen.kt`：新增 `onMovieClick` 參數，一路傳到 `CollectSuccessScreen` → `MovieCard(onMovieClick = { onMovieClick(it.movieCardId) })`。
- [x] 3.3 修改 `feature/history/.../navigation/HistoryNavigation.kt`：`historyScreen()` 新增必要參數 `onMovieClick: (Int) -> Unit`，並傳入 `HistoryScreen(onMovieClick = onMovieClick)`。
- [x] 3.4 修改 `feature/history/.../ui/HistoryScreen.kt`：新增 `onMovieClick` 參數，一路傳到 `HistoryMovieScreen` → `MovieCard(onMovieClick = { onMovieClick(it.movieCardId) })`。
- [x] 3.5 修改 `feature/search/.../navigation/SearchNavigation.kt`：`searchScreen()` 新增必要參數 `onMovieClick: (Int) -> Unit`，並傳入 `SearchScreen(onMovieClick = onMovieClick)`。
- [x] 3.6 修改 `feature/search/.../ui/SearchScreen.kt`：新增 `onMovieClick` 參數，一路傳到 `SearchResultScreen` → `MovieCard(onMovieClick = { onMovieClick(it.movieCardId) })`（與任務 2.3 的 `onCollectClick` 一併補上）。

## 4. app：串接 NavHost

- [x] 4.1 修改 `app/src/main/java/com/shang/jetpackmoviecompose/ui/MainActivity.kt`：`collectScreen()`、`historyScreen()`、`searchScreen()` 呼叫處補上 `onMovieClick = { movieId -> navController.navigateToMovieDetail(movieId) }`，與 `homeScreen(...)` 現有寫法一致。

## 5. 驗證與收尾

- [x] 5.1 執行 `./gradlew ktlintCheck` 確認格式符合規範。
- [x] 5.2 執行 `./gradlew :core:domain:test :feature:search:test` 確認新增/修改的單元測試皆通過。
- [ ] 5.3 手動驗證：在 collect、history、search 三個頁面點擊電影卡片皆能正確導航至詳情頁；在 search 頁面收藏/取消收藏後，圖示即時更新且不重置捲動位置。
