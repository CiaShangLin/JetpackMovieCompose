# movie-list-detail-navigation

## Purpose

collect、history、search 三個列表頁的電影卡片點擊需能導航至電影詳情頁，行為與 home 模組一致。

## Requirements

### Requirement: 收藏頁點擊電影卡片導航至詳情頁
`collect` 模組的 `CollectScreen` SHALL 在使用者點擊列表中的電影卡片時，導航至 `moviedetail` 模組對應的電影詳情頁，並帶入該電影的 `movieId`。

#### Scenario: 使用者在收藏頁點擊電影卡片
- **WHEN** 使用者在 `CollectScreen` 的收藏列表中點擊任一電影卡片
- **THEN** 系統導航至該電影的詳情頁（`movie_detail_route/{movieId}`），且帶入的 `movieId` 與被點擊卡片一致

### Requirement: 歷史紀錄頁點擊電影卡片導航至詳情頁
`history` 模組的 `HistoryScreen` SHALL 在使用者點擊列表中的電影卡片時，導航至 `moviedetail` 模組對應的電影詳情頁，並帶入該電影的 `movieId`。

#### Scenario: 使用者在歷史紀錄頁點擊電影卡片
- **WHEN** 使用者在 `HistoryScreen` 的歷史列表中點擊任一電影卡片
- **THEN** 系統導航至該電影的詳情頁（`movie_detail_route/{movieId}`），且帶入的 `movieId` 與被點擊卡片一致

### Requirement: 搜尋結果頁點擊電影卡片導航至詳情頁
`search` 模組的 `SearchScreen` SHALL 在使用者點擊搜尋結果中的電影卡片時，導航至 `moviedetail` 模組對應的電影詳情頁，並帶入該電影的 `movieId`。

#### Scenario: 使用者在搜尋結果頁點擊電影卡片
- **WHEN** 使用者在 `SearchScreen` 的搜尋結果分頁列表中點擊任一電影卡片
- **THEN** 系統導航至該電影的詳情頁（`movie_detail_route/{movieId}`），且帶入的 `movieId` 與被點擊卡片一致

### Requirement: 三個列表頁的導航 callback 形狀與 home 模組一致
`collect`、`history`、`search` 三個模組的 `NavGraphBuilder` 擴充函式 SHALL 暴露 `onMovieClick: (Int) -> Unit` 必要參數（無預設值），與 `feature/home` 模組現有的 `homeScreen(onMovieClick: (Int) -> Unit)` 形狀一致，並由 `app` 模組在組裝 `NavHost` 時提供實際導航邏輯。

#### Scenario: app 模組組裝 NavHost 時遺漏傳入 onMovieClick
- **WHEN** `app` 模組呼叫 `collectScreen()`、`historyScreen()` 或 `searchScreen()` 卻未傳入 `onMovieClick` 引數
- **THEN** 專案編譯失敗（因 `onMovieClick` 為必要參數，無預設值），而非靜默產生點擊無反應的畫面
