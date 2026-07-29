## Context

`home` 模組已經是「列表點擊 → 詳情頁」與「Pager + 收藏狀態 combine」兩種模式的正確範例：

- 導航：`HomeNavigation.kt` 的 `homeScreen(onMovieClick: (Int) -> Unit)` 把點擊回呼一路往下傳到 `MovieCard(onMovieClick = { onMovieClick(it.movieCardId) })`，由 `app` 模組的 `MainActivity.kt` 在 `NavHost` 組裝時提供 `{ movieId -> navController.navigateToMovieDetail(movieId) }`（定義於 `feature/moviedetail` 的 `MovieDetailNavigation.kt`）。
- 收藏同步：`core/domain` 的 `GetHomeMovieListUseCase` 對 `movieRepository.getMovieListPager(withGenres)` 先 `flowOn(ioDispatcher).cachedIn(viewModelScope)`，再與 `movieRepository.getCollectedMovieIds().map { it.toSet() }.flowOn(ioDispatcher)` 用 `combine` 組合，把收藏狀態 map 回每筆 `MovieCardResult.copy(isCollect = ...)`。

但 `collect`、`history`、`search` 三個模組的 `NavGraphBuilder` 擴充函式完全沒有 `onMovieClick` 參數，`MainActivity.kt` 呼叫它們時也是零參數；而 `search` 更進一步繞過 domain 層，`SearchViewModel` 直接呼叫 `movieRepository.getMovieSearchPager(query).cachedIn(viewModelScope)`，沒有任何 `GetMovieSearchUseCase` 存在，因此收藏狀態永遠不會被 combine 進分頁資料。

已比對過姊妹專案 JetpackMovieKMP（KMP 架構，domain 在 `shared/domain`）：其 `GetSearchMovieListUseCase`、`GetHomeMovieListUseCase` 採用完全相同的 `cachedIn` 後再 `combine` 寫法，可直接作為本次 `GetMovieSearchUseCase` 的實作範本；但該專案的 detail 導航目前也只是 `onMovieClick = {}` 的占位符，尚未真正實作，因此本次改動在「detail 導航」這塊沒有既有成品可抄，需要自行比照 `home` 模組實作。

## Goals / Non-Goals

**Goals:**
- collect、history、search 三個列表頁點擊電影卡片後，皆能導航至 `moviedetail` 頁面，行為與 `home` 一致（同樣的 `onMovieClick: (Int) -> Unit` callback 形狀、同樣呼叫 `navController.navigateToMovieDetail(movieId)`）。
- 新增 `GetMovieSearchUseCase`，讓 search 分頁資料流即時反映收藏狀態；收藏/取消收藏後，`SearchScreen` 上的收藏圖示需正確更新。
- `SearchScreen` 補上 `onCollectClick`，實際串接 toggle 收藏的邏輯。

**Non-Goals:**
- 不重構 collect / history 既有的「非 Paging」列表架構（維持 `Flow<List<MovieCardResult>>`，不改為 Paging 3）。
- 不修改 `MovieDetailNavigation.kt` 既有的路由設計（`movie_detail_route/{movieId}`），沿用現有簽名。
- 不新增 `CollectMovieUseCase` 之類的收藏切換 UseCase 抽象——參考 JetpackMovieKMP 現況，collect toggle 目前也是直接呼叫 Repository，本次維持既有做法（ViewModel 直接呼叫 `movieRepository.insertMovieCollect/deleteMovieCollect`），非本次改動範圍。
- 不處理 `MovieDetailNavigation.kt` 中 `movieDetailScreen(onMovieClick: (MovieCardData) -> Unit)`（詳情頁內「相關推薦」點擊，形狀為 `MovieCardData` 而非 `Int`）的不一致問題——這是既有行為，本次三個列表頁一律採用 `home` 的 `(Int) -> Unit` 形狀，不引入第三種形狀。

## Decisions

### 1. 導航 callback 形狀：沿用 `home` 的 `(Int) -> Unit`，不採用 KMP 專案的 `(MovieCardData) -> Unit`
`home` 模組現有寫法是 `onMovieClick: (Int) -> Unit`，直接對應 `navigateToMovieDetail(movieId: Int)`。JetpackMovieKMP 裡 search/collect/history 的 `onMovieClick` 型別是 `(MovieCardData) -> Unit`（但那邊尚未真正導航，只是占位）。為了與本專案 `home` 既有的、已經在運作的模式保持一致，三個模組一律採用 `(Int) -> Unit`，避免同一專案內出現兩種不同的 callback 形狀。

### 2. `GetMovieSearchUseCase` 的 combine 順序：`cachedIn` 在前、`combine` 在後
直接複製 `GetHomeMovieListUseCase` 已驗證的寫法：
```kotlin
val pageFlow = movieRepository.getMovieSearchPager(query).flowOn(ioDispatcher).cachedIn(scope)
val collectIdsFlow = movieRepository.getCollectedMovieIds().map { it.toSet() }.flowOn(ioDispatcher)
return pageFlow.combine(collectIdsFlow) { pagingData, collectIds ->
    pagingData.map { movie -> movie.copy(isCollect = collectIds.contains(movie.id)) }
}
```
`cachedIn(scope)` 必須放在 `combine` 之前，否則每次 `collectIdsFlow` 重新 emit 都會產生新的 `PagingData` 實例並繞過快取，導致分頁狀態被重置（這也是 JetpackMovieKMP 對應 UseCase 採同樣順序的原因）。`scope` 由呼叫端（`SearchViewModel.viewModelScope`）傳入，維持與 `GetHomeMovieListUseCase` 相同的介面慣例。

### 3. `SearchViewModel` 改走 UseCase，而非直接修補 Repository 呼叫
現況 `SearchViewModel` 直接呼叫 `movieRepository.getMovieSearchPager(query)`，繞過了 domain 層。與其在 ViewModel 裡就地補上 `combine` 邏輯，選擇新增 `GetMovieSearchUseCase` 讓 search 補齊與 home 相同的分層方式（Repository 只回傳原始 Pager，收藏狀態的組合邏輯放在 domain 層），維持專案既有的 Use Case 模式（`core/domain` 封裝業務邏輯，ViewModel 提供 CoroutineScope）。

### 4. `onCollectClick` 串接既有 toggle 邏輯，不新增額外抽象
`SearchViewModel` 目前沒有 `toggleMovieCollectStatus` 方法（因為 UI 從未接上 `onCollectClick`）。比照 `HomeContentViewModel.toggleMovieCollectStatus` / `HistoryViewModel` 的既有寫法，在 `SearchViewModel` 新增同名方法，直接呼叫 `movieRepository.insertMovieCollect` / `deleteMovieCollect`，不引入新的 UseCase 層（維持與 Non-Goals 一致）。

## Risks / Trade-offs

- **[Risk] `cachedIn` 與 `combine` 順序寫反會導致分頁狀態被收藏動作打斷（列表滾動位置重置、重新從第一頁載入）** → Mitigation：嚴格比照 `GetHomeMovieListUseCase` 已驗證的順序（`cachedIn` 在 `combine` 之前），並在單元測試中驗證同一個 `collectIdsFlow` 變化不會觸發 Pager 重新整頁。
- **[Risk] 三個模組同時修改 `NavGraphBuilder` 簽名（新增必要參數）可能被視為 breaking change，若有其他呼叫端遺漏會編譯失敗** → Mitigation：`onMovieClick` 設計為必要參數（不給預設值 `= {}`），故意讓遺漏呼叫處在編譯期就失敗，而不是靜默變成不可點擊；範圍內只有 `MainActivity.kt` 這一個呼叫端，修改風險可控。
- 本次變更不涉及 Room schema，故無 migration 需求。

## Migration Plan

不涉及資料庫或 API 契約變更，無需 migration / rollback 特殊步驟；純屬新增程式碼與既有函式簽名擴充。若需回退，直接 revert 對應 commit 即可。

## Open Questions

無。
