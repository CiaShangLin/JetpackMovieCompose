## Why

`collect`（收藏）、`history`（歷史紀錄）、`search`（搜尋）三個 feature 模組目前點擊電影卡片沒有任何反應——`NavGraphBuilder` 擴充函式（`collectScreen()`、`historyScreen()`、`searchScreen()`）都沒有暴露 `onMovieClick` 參數，`MainActivity.kt` 呼叫這三者時也沒有傳入導航邏輯，使用者無法從這三個頁面進入電影詳情頁（`moviedetail`）。另外，`search` 頁面的收藏功能也是壞的：`SearchViewModel.movieSearchPager` 直接呼叫 `movieRepository.getMovieSearchPager(query).cachedIn(viewModelScope)`，並未像 `GetHomeMovieListUseCase` 一樣把分頁資料流與 `getCollectedMovieIds()` 收藏狀態流 `combine` 起來，導致 `MovieCardResult.isCollect` 永遠是網路端回傳的預設值 `false`，收藏按鈕點擊後列表也不會反映最新收藏狀態。此外 `SearchScreen` 目前連 `onCollectClick` 都沒有接上 `MovieCard`。

現在補齊這兩塊，讓使用者可以從任何列表頁進入詳情頁，且搜尋結果的收藏狀態能正確即時反映。

## What Changes

- 為 `collect`、`history`、`search` 三個 feature 模組的 `NavGraphBuilder` 擴充函式新增 `onMovieClick: (Int) -> Unit` 參數（比照 `home` 模組現有慣例），並將其一路傳遞到各自 `Screen` 內的 `MovieCard` 呼叫處。
- 在 `app/src/main/java/.../ui/MainActivity.kt` 的 `NavHost` 中，為 `collectScreen()`、`historyScreen()`、`searchScreen()` 補上 `onMovieClick = { movieId -> navController.navigateToMovieDetail(movieId) }`，與 `home` 模組寫法一致。
- 為 `SearchScreen` 補上 `onCollectClick`，串接既有的 `SearchViewModel.toggleMovieCollectStatus(...)`（若尚未存在則新增對應方法，比照 `HomeContentViewModel`/`HistoryViewModel` 現有寫法）。
- 在 `core/domain` 新增 `GetMovieSearchUseCase`，比照 `GetHomeMovieListUseCase` 的做法：對 `movieRepository.getMovieSearchPager(query)` 套用 `cachedIn(scope)` 後，再與 `movieRepository.getCollectedMovieIds().map { it.toSet() }` 做 `combine`，將收藏狀態 map 回每筆 `MovieCardResult`。
- `SearchViewModel` 改為呼叫新的 `GetMovieSearchUseCase`，取代目前直接呼叫 `movieRepository.getMovieSearchPager(query)` 的寫法。

## Capabilities

### New Capabilities
- `movie-list-detail-navigation`：collect、history、search 三個列表頁的電影卡片點擊需能導航至電影詳情頁，行為與 home 模組一致。
- `search-collect-sync`：搜尋結果分頁資料需與收藏狀態即時同步，收藏/取消收藏後列表需正確反映最新狀態。

### Modified Capabilities
（無現有 capability spec，故此變更全數列為 New Capabilities。）

## Impact

- **feature/collect**：`CollectNavigation.kt`、`CollectScreen.kt`（新增 `onMovieClick` 參數並傳給 `MovieCard`）。
- **feature/history**：`HistoryNavigation.kt`、`HistoryScreen.kt`（同上）。
- **feature/search**：`SearchNavigation.kt`、`SearchScreen.kt`（新增 `onMovieClick`／`onCollectClick`）、`SearchViewModel.kt`（改用新 UseCase，並視需要新增 `toggleMovieCollectStatus`）。
- **app**：`app/src/main/java/com/shang/jetpackmoviecompose/ui/MainActivity.kt`（三個 `NavGraphBuilder` 呼叫處補上 `onMovieClick`）。
- **core/domain**：新增 `GetMovieSearchUseCase.kt`（依賴現有 `MovieRepository`，不需新增外部依賴，故不涉及 buildSrc 變更）。
- 不涉及 `core/network`、`core/database`、`core/datastore` 的結構變更；`MovieRepository` 介面現有的 `getMovieSearchPager`、`getCollectedMovieIds` 方法即足夠支援，不需修改介面或 DAO。
