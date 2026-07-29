# search-collect-sync

## Purpose

搜尋結果分頁資料需與收藏狀態即時同步，收藏/取消收藏後列表需正確反映最新狀態。

## Requirements

### Requirement: 搜尋結果分頁資料需與收藏狀態即時同步
`core/domain` SHALL 提供 `GetMovieSearchUseCase`，將 `movieRepository.getMovieSearchPager(query)` 的分頁資料流與 `movieRepository.getCollectedMovieIds()` 收藏狀態流組合，使每筆搜尋結果的 `MovieCardResult.isCollect` 正確反映當下的收藏狀態，而非永遠使用網路端回傳的預設值。

#### Scenario: 搜尋結果中已收藏的電影應標示為已收藏
- **WHEN** 使用者搜尋到一部先前已加入收藏的電影
- **THEN** 該筆搜尋結果的 `isCollect` 為 `true`，收藏圖示顯示為「已收藏」狀態

#### Scenario: 收藏狀態變動後搜尋結果即時更新
- **WHEN** 使用者在搜尋結果列表中對某筆電影點擊收藏（或取消收藏）
- **THEN** 該筆電影對應的收藏狀態變動會反映到 `MovieCollectEntity` 資料表，且同一個搜尋結果列表（含已載入的分頁）中對應項目的收藏圖示會更新為最新狀態，不需使用者手動重新整理或重新輸入搜尋關鍵字

#### Scenario: 收藏狀態變動不會重置搜尋分頁的載入進度
- **WHEN** 使用者已捲動載入搜尋結果的第二頁以後，並對列表中任一電影切換收藏狀態
- **THEN** 已載入的分頁資料與捲動位置保持不變，僅該筆電影的收藏狀態更新，不會觸發整個分頁重新從第一頁載入

### Requirement: 搜尋結果頁可直接切換收藏狀態
`search` 模組的 `SearchScreen` SHALL 讓使用者可以直接在搜尋結果列表中點擊收藏圖示以切換該電影的收藏狀態，行為與 `home` 模組的收藏切換一致。

#### Scenario: 使用者在搜尋結果頁點擊收藏圖示
- **WHEN** 使用者在 `SearchScreen` 的搜尋結果中點擊某電影卡片的收藏圖示
- **THEN** 若該電影尚未收藏，系統將其加入收藏（寫入 `MovieCollectEntity`）；若已收藏，系統將其移除收藏
