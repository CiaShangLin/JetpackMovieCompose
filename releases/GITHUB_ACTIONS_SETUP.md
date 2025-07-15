# 🚀 GitHub Actions APK 自動發布設定指南

## 📋 必要設定

### 1. GitHub Secrets 設定

前往 GitHub Repository → Settings → Secrets and variables → Actions，新增以下 Secrets：

```
TMDB_API_KEY=你的TMDB API金鑰
KEYSTORE_BASE64=你的keystore檔案的base64編碼
KEYSTORE_PASSWORD=keystore密碼
KEY_ALIAS=金鑰別名
KEY_PASSWORD=金鑰密碼
```

### 2. 產生 Keystore Base64 編碼

在終端機執行以下指令：
```bash
base64 -i qa_release_keystore.jks -o keystore.txt
```
然後將 `keystore.txt` 的內容複製到 `KEYSTORE_BASE64` Secret 中。

## 🎯 使用方式

### 方式一：自動發布（推薦）

1. 建立版本標籤：
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

2. GitHub Actions 會自動執行：
   - 🧪 單元測試
   - 🔨 建置 APK
   - 🔐 簽名 APK
   - 📝 生成 Changelog
   - 🎉 建立 GitHub Release

### 方式二：手動上傳（備用方案）

當自動編譯失敗時：

1. 本地編譯 APK：
   ```bash
   ./gradlew assembleProdRelease
   ```

2. 將 APK 複製到 `releases/` 目錄

3. 提交並推送：
   ```bash
   git add releases/
   git commit -m "Add APK for manual release"
   git push
   ```

4. 前往 GitHub Actions → 手動觸發「📦 手動上傳 APK 發布」

## 📁 檔案結構

```
.github/
└── workflows/
    ├── release-apk.yml      # 自動發布工作流程
    └── manual-release.yml   # 手動發布工作流程
releases/
└── README.md               # 手動發布說明
```

## 🔍 工作流程說明

### release-apk.yml
- **觸發條件**：推送版本標籤（v*.*.*）
- **執行步驟**：測試 → 建置 → 簽名 → 發布
- **輸出**：GitHub Release + APK 檔案

### manual-release.yml
- **觸發條件**：手動觸發
- **輸入參數**：版本號、更新說明、APK 路徑
- **執行步驟**：建立標籤 → 發布 Release

## ⚠️ 注意事項

1. 確保 `key.properties` 已加入 `.gitignore`
2. Keystore 檔案應妥善保管，不要提交到版本控制
3. 版本標籤格式必須符合 `v*.*.*`（如 v1.0.0）
4. 測試失敗時不會進行 APK 建置
