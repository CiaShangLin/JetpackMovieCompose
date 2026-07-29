# JetpackMovieCompose

一個使用 Jetpack Compose 和 [TMDB API](https://www.themoviedb.org/documentation/api) 打造的現代化 Android 電影資訊 App。此專案展示了最新的 Android 開發實踐，包含多模組架構、Clean Architecture、以及 Jetpack 生態系中的各種元件。

## Medium

📖 [Android 多模組化實戰練習](https://medium.com/@west7418/android-%E5%A4%9A%E6%A8%A1%E7%B5%84%E5%8C%96%E5%AF%A6%E6%88%B0%E7%B7%B4%E7%BF%92-df87d06cef54)

此文章詳細說明了本專案的多模組架構設計思路、實作細節以及最佳實踐，是理解專案架構的最佳參考資料。

## Demo 影片

🎥 **功能展示**: [YouTube Demo](https://www.youtube.com/shorts/qXaRIgD8KaY)

快速預覽 App 的核心功能和使用者介面，包含電影瀏覽、搜尋、詳情查看等主要特色。

## 主要功能 (Features)

- **電影探索**: 瀏覽熱門、即將上映、和高評分的電影。
- **電影搜尋**: 透過關鍵字即時搜尋電影。
- **詳細資訊**: 查看電影的詳細介紹、預告片、評分和演員陣容。
- **個人收藏**: 將喜歡的電影加入收藏清單以便日後查看。
- **主題切換**: 支援深色與淺色主題模式。
- **語言切換**: 支援英文與繁體中文。

## 螢幕截圖 (Screenshots)

### 淺色主題 (Light Theme)
| 首頁 | 電影詳情 |
| :---: | :---: |
| ![首頁](image/light/首頁.jpg) | ![電影詳情](image/light/電影詳細頁.jpg) |

| 搜尋 | 收藏清單 |
| :---: | :---: |
| ![搜尋](image/light/收尋初始頁.jpg) | ![收藏清單](image/light/我的收藏有資料.jpg) |

### 深色主題 (Dark Theme)
| 首頁 | 電影詳情 |
| :---: | :---: |
| ![首頁](image/night/首頁.jpg) | ![電影詳情](image/night/電影詳細頁.jpg) |

| 搜尋 | 收藏清單 |
| :---: | :---: |
| ![搜尋](image/night/搜尋初始頁.jpg) | ![收藏清單](image/night/我的收藏有資料.jpg) |

### 其他功能頁面
| 設定頁 | 觀看歷史 | 主題切換 |
| :---: | :---: | :---: |
| ![設定頁](image/light/設定頁.jpg) | ![觀看歷史](image/light/觀看歷史有資料.jpg) | ![主題切換](image/light/切換主題.jpg) |

## 技術棧與關鍵函式庫 (Tech Stack & Key Libraries)

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - 用於建構原生 UI 的現代化工具包。
- **語言**: [Kotlin](https://kotlinlang.org/) - 主要開發語言。
- **非同步處理**: [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://developer.android.com/kotlin/flow) - 用於處理非同步任務和資料流。
- **架構**: 
  - 多模組 (Multi-module) 架構
  - Clean Architecture (分層式架構)
  - MVVM (Model-View-ViewModel)
- **依賴注入**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - 簡化 Android 的依賴注入。
- **網路請求**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) - 強大且高效的 HTTP 客戶端。
- **資料庫**: [Room](https://developer.android.com/training/data-storage/room) - 在本地持久化儲存資料。
- **分頁載入**: [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - 漸進式地從遠端或本地載入資料。
- **資料儲存**: [Proto DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - 安全且高效地儲存鍵值對或型別物件。
- **圖片載入**: [Coil](https://coil-kt.github.io/coil/) - 基於 Kotlin Coroutines 的圖片載入庫。

## 專案架構 (Architecture)

本專案採用了現代化的**多模組 (Multi-module)** 架構，將程式碼按照功能和分層進行隔離，以達到高內聚、低耦合的目標。整體架構遵循 **Clean Architecture** 的原則，分為三個主要層次：`feature`、`core` 和 `app`。

- **`app` 層**: 負責整合所有模組，建立依賴注入圖，並作為應用程式的進入點。
- **`feature` 層**: 包含所有獨立的功能模組 (如首頁、搜尋、電影詳情等)。每個 `feature` 模組都擁有自己的 UI 和 ViewModel，並透過 `domain` 層與資料層互動。
- **`core` 層**: 提供了所有 `feature` 模組共享的基礎建設和業務邏輯。它進一步細分為：
  - `ui`: 共享的 Compose 元件。
  - `domain`: 包含 UseCases 和業務邏輯。
  - `data`: 資料儲存庫 (Repository) 的實作，負責協調遠端和本地資料來源。
  - `network`: 處理所有遠端 API 請求。
  - `database`: Room 資料庫的定義和 DAOs。
  - `datastore`: Proto DataStore 的實作。
  - `model`: 定義了整個應用程式共用的資料模型。
  - `designsystem`: 定義 App 的顏色、字體和主題。
  - `common`: 共享的工具類和擴充函式。

### Core 模組依賴圖

以下是 `core` 內部各模組的依賴關係圖：

```mermaid
graph TD
    subgraph core
        data --> common
        data --> database
        data --> datastore
        data --> model
        data --> network
        database --> common
        database --> model
        datastore --> common
        datastore --> model
        domain --> common
        domain --> data
        domain --> model
        network --> common
        network --> model
        ui --> common
        ui --> designsystem
        ui --> model
    end
```

## 如何開始 (Getting Started)

請依照以下步驟在本機環境中建置並執行專案：

### 1. Clone 專案

```bash
git clone https://github.com/CiaShangLin/JetpackMovieCompose.git
cd JetpackMovieCompose
```

### 2. 設定 API 金鑰

本專案需要使用 The Movie Database (TMDB) 的 API 金鑰才能取得電影資料。請依照以下步驟設定：

1.  前往 [TMDB 網站](https://www.themoviedb.org/signup) 註冊一個帳號並申請一組 API 金鑰。
2.  在專案的根目錄下，找到 `key.properties` 檔案。如果檔案不存在，請自行建立一個。
3.  在 `key.properties` 檔案中，加入您的 API 金鑰，格式如下：

    ```properties
    API_KEY="YOUR_TMDB_API_KEY"
    ```

    **注意**: `key.properties` 已被加入 `.gitignore` 中，以確保您的金鑰不會被上傳到版本控制系統。

### 3. 建置並執行

一切就緒後，您可以使用 Android Studio 開啟專案，並點擊 "Run" 按鈕來建置和執行應用程式。

或者，您也可以使用以下 Gradle 指令：

```bash
# 在您的裝置或模擬器上安裝 Debug 版本的 App
./gradlew installDebug
```

## CI/CD 持續整合與部署 (Continuous Integration & Deployment)

本專案採用 **GitHub Actions** 實現自動化的 CI/CD 流程，確保程式碼品質並簡化發布流程。

### 🔧 CI/CD 流程概覽

```mermaid
graph LR
    A[推送程式碼] --> B[觸發 GitHub Actions]
    B --> C[程式碼檢查]
    C --> D[單元測試]
    D --> E[建置 APK]
    E --> F[簽名 APK]
    F --> G[建立 Release]
    G --> H[上傳到 GitHub Releases]
```

### 📱 自動化流程功能

- **程式碼品質檢查**: 使用 Ktlint 進行程式碼格式化檢查
- **單元測試執行**: 自動執行所有模組的單元測試
- **APK 建置**: 自動建置 Release 版本的 APK
- **APK 簽名**: 使用預設的簽名金鑰進行 APK 簽名
- **自動發布**: 當推送版本標籤時，自動建立 GitHub Release
- **Changelog 生成**: 自動產生版本更新日誌

### 🚀 發布新版本

#### 方法一：自動發布（推薦）

1. **建立版本標籤並推送**：
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

2. **GitHub Actions 會自動執行**：
   - 執行測試套件
   - 建置並簽名 APK
   - 建立 GitHub Release
   - 上傳 APK 檔案

### ⚙️ CI/CD 設定檔案

專案包含以下 CI/CD 相關設定檔案：

- `releases/GITHUB_ACTIONS_SETUP.md`: GitHub Actions 詳細設定指南
- `.github/workflows/`: GitHub Actions 工作流程定義（如存在）

### 🔐 必要的 GitHub Secrets

為了讓 CI/CD 流程正常運作，需要在 GitHub Repository 設定以下 Secrets：

| Secret 名稱 | 說明 | 必要性 |
|------------|------|-------|
| `TMDB_API_KEY` | TMDB API 金鑰 | ✅ 必要 |
| `KEYSTORE_BASE64` | 簽名檔案的 Base64 編碼 | ✅ 必要 |
| `KEYSTORE_PASSWORD` | Keystore 密碼 | ✅ 必要 |
| `KEY_ALIAS` | 金鑰別名 | ✅ 必要 |
| `KEY_PASSWORD` | 金鑰密碼 | ✅ 必要 |

### 📊 建置狀態

| 分支 | 建置狀態 | 程式碼覆蓋率 |
|------|---------|-------------|
| main | ![Build Status](https://github.com/your-username/JetpackMovieCompose/workflows/CI/badge.svg) | ![Coverage](https://img.shields.io/badge/coverage-80%25-green) |

### 🛠️ 本地測試 CI 流程

在推送之前，您可以在本地執行相同的檢查：

```bash
# 執行程式碼格式檢查
./gradlew ktlintCheck

# 執行所有單元測試
./gradlew test

# 建置 Release APK
./gradlew assembleProdRelease

# 執行所有檢查（包含測試和 Lint）
./gradlew check
```

詳細的 CI/CD 設定說明請參考 [`releases/GITHUB_ACTIONS_SETUP.md`](releases/GITHUB_ACTIONS_SETUP.md)。

## 📋 待辦事項 (TODO List)

以下是專案的未來開發計劃，歡迎有興趣的開發者提交 Pull Request 貢獻：

### 🔧 功能改進
- [ ] **演員結果頁** - 新增演員詳細資訊頁面，展示演員的作品列表和個人資料
- [ ] **單元測試** - 單元測試

### 💡 專案狀態
> **注意**: 由於作者需要開發公司專案，此專案暫時擱置開發。但仍歡迎社群貢獻者提交 Pull Request！

我們歡迎任何形式的貢獻，包括但不限於：
- 新功能開發
- Bug 修復
- 程式碼重構
- 文件改進
- 單元測試擴充

## 授權 (License)

```
Copyright 2025 Your Name

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
