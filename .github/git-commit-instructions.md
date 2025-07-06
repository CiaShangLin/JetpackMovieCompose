# Conventional Commits 1.0.0（繁體中文優化版）

## 摘要

Conventional Commits 是一套輕量級的 Git commit 訊息規範，能提升專案歷史紀錄的可讀性與自動化處理能力，並與 [語意化版本（SemVer）](http://semver.org/lang/zh-TW/) 完美結合。遵循本規範有助於自動產生變更日誌、版本升級與持續整合。

## Commit 訊息格式

```
<type>[可選範圍]: <描述>

[可選主體]

[可選註腳]
```

- `type`：提交類型（如 feat、fix 等，僅允許英文）
- `scope`：影響範圍（可選，英文，括號包裹）
- `description`：簡要描述（**必須使用繁體中文**）
- `body`：詳細說明（可選，繁體中文）
- `footer`：註腳（如 BREAKING CHANGE，繁體中文）

## 常用類型（type）

- **feat**：新增功能（對應 SemVer MINOR）
- **fix**：修復錯誤（對應 SemVer PATCH）
- **BREAKING CHANGE**：重大變更（對應 SemVer MAJOR，可於 type 後加 `!` 或於 footer 註明）
- 其他常見類型：`build`、`chore`、`ci`、`docs`、`style`、`refactor`、`perf`、`test` 等

## 範例

```
feat(ui): 新增電影搜尋功能

fix(network): 修正 API 回傳資料解析錯誤

refactor: 重構資料層結構，提升可維護性

feat: 支援多語系顯示

perf: 優化圖片載入效能

feat!: 移除舊版收藏功能
BREAKING CHANGE: 收藏功能 API 已全面調整，請參考最新文件
```

## 本地化規則

- **commit 訊息描述、主體、註腳皆使用繁體中文**
- type 與 scope 保持英文
- 目的：提升本地團隊協作與審查效率
- **如遇專有名詞（如技術詞彙、函式、類別名稱、API、第三方庫名稱等），可維持原文，不需轉換為繁體中文**

## 進階說明

- 可自訂 type，但建議遵循 [Angular commit message guidelines](https://github.com/angular/angular/blob/main/CONTRIBUTING.md#-commit-message-guidelines)
- 註腳（footer）可用於關聯議題、描述重大變更等
- 建議每次 commit 僅專注於單一目的，避免混合多項變更

---

如需更詳細規範，請參考 [Conventional Commits 官方文件](https://www.conventionalcommits.org/zh-hant/v1.0.0/)。
