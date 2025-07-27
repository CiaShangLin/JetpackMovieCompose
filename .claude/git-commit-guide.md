# Claude Code Git Commit 指導

此檔案專門為 Claude Code 在此專案中進行 git commit 時提供指導。

## 專案 Commit 風格

遵循 `.github/git-commit-instructions.md` 中的 Conventional Commits 標準，並使用繁體中文。

### 基本格式
```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### 常用類型 (Type)
- `feat`: 新功能
- `fix`: 錯誤修復
- `docs`: 文件更新
- `style`: 程式碼格式調整（不影響邏輯）
- `refactor`: 程式碼重構
- `test`: 測試相關
- `chore`: 建置工具、依賴更新等
- `perf`: 效能改善
- `ci`: CI/CD 相關

### 常用範圍 (Scope)
- `app`: 主應用程式
- `ui`: UI 元件
- `network`: 網路層
- `database`: 資料庫
- `auth`: 認證相關
- `home`: 首頁功能
- `search`: 搜尋功能
- `detail`: 詳情頁面
- `setting`: 設定頁面

## Claude Code 專用規則

### 1. 自動生成的 Footer
每個 Claude Code 生成的 commit 都應包含：
```
🤖 Generated with [Claude Code](https://claude.ai/code)

Co-Authored-By: Claude <noreply@anthropic.com>
```

### 2. 描述原則
- 使用繁體中文
- 動詞使用過去式（新增、修復、更新）
- 簡潔明瞭，不超過 50 字元
- 技術術語保持原文（API、Module、Repository 等）

### 3. 常見情境範例

#### 新增功能
```
feat: 新增電影收藏功能
feat(search): 新增搜尋結果排序選項
feat(ui): 新增深色主題支援
```

#### 錯誤修復
```
fix: 修復網路請求超時問題
fix(database): 修復 Room 資料庫遷移錯誤
fix(ui): 修復 Compose 重組導致的效能問題
```

#### 重構改善
```
refactor: 重構 Repository 架構模式
refactor(network): 簡化 API 回應處理邏輯
refactor(ui): 提取共用 Composable 元件
```

#### 測試相關
```
test: 新增 UseCase 單元測試
test(network): 新增 API 整合測試
test: 提升測試覆蓋率至 85%
```

#### 文件更新
```
docs: 更新 README 安裝說明
docs: 新增 API 使用文件
docs: 更新架構圖和模組說明
```

#### 依賴和建置
```
chore: 更新 Gradle 至 8.5 版本
chore: 升級 Compose BOM 至最新版本
chore(deps): 更新 Hilt 至 2.51.1
```

### 4. 多行 Commit 範例

#### 功能新增（含詳細說明）
```
feat: 新增使用者偏好設定功能

- 新增語言切換選項（繁中/英文）
- 新增主題切換選項（淺色/深色）
- 使用 Proto DataStore 儲存設定
- 整合 LanguageInterceptor 支援 API 語言切換

🤖 Generated with [Claude Code](https://claude.ai/code)

Co-Authored-By: Claude <noreply@anthropic.com>
```

#### 重大變更
```
feat!: 重構多模組架構

BREAKING CHANGE: 變更模組依賴結構，需要重新同步專案

- 分離 core 和 feature 模組
- 建立統一的 buildSrc 配置
- 更新所有模組的 build.gradle.kts

🤖 Generated with [Claude Code](https://claude.ai/code)

Co-Authored-By: Claude <noreply@anthropic.com>
```

### 5. 特殊情境處理

#### 修復安全問題
```
fix(security): 修復 API 金鑰洩漏風險

- 將 API 金鑰移至 key.properties
- 更新 .gitignore 排除敏感檔案
- 新增環境變數支援

🤖 Generated with [Claude Code](https://claude.ai/code)

Co-Authored-By: Claude <noreply@anthropic.com>
```

#### 效能改善
```
perf(ui): 改善電影列表載入效能

- 實作 LazyColumn 虛擬化
- 最佳化圖片載入和快取
- 減少不必要的 recomposition

🤖 Generated with [Claude Code](https://claude.ai/code)

Co-Authored-By: Claude <noreply@anthropic.com>
```

## 注意事項

1. **保持一致性**：與專案既有的 commit 風格保持一致
2. **簡潔明瞭**：避免冗長的描述，重點放在「做了什麼」
3. **技術術語**：保持英文原文以維持準確性
4. **分類明確**：正確使用 type 和 scope 進行分類
5. **避免混合**：一個 commit 只處理一種類型的變更

## 工具整合

### 檢查 Commit 訊息
```bash
# 使用 commitlint 檢查（如果有配置）
npx commitlint --edit $1

# 手動檢查格式
git log --oneline -5
```

### 修改最後一次 Commit
```bash
# 修改 commit 訊息
git commit --amend -m "新的 commit 訊息"

# 重新編輯 commit 訊息
git commit --amend
```