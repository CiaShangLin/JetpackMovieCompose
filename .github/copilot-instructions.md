# Copilot Instructions for Android Development

## 核心開發原則

### 語言偏好
- **優先使用 Kotlin**：所有新程式碼都使用 Kotlin，避免 Java 風格的寫法
- **函數式編程**：適當使用 Kotlin 的函數式特性（高階函數、lambda 表達式）
- **空安全**：充分利用 Kotlin 的空安全特性，避免 `!!` 操作符

### 架構模式
- **MVVM 架構**：Activity/Fragment + ViewModel + Repository 模式
- **單一責任原則**：每個類別只負責一個功能
- **依賴注入**：使用 Hilt 或 Koin 管理依賴
- **資料層分離**：Repository 模式處理資料來源邏輯

## 程式碼規範

### 命名慣例
```kotlin
// 類別：PascalCase
class UserProfileActivity
data class UserInfo

// 函數和變數：camelCase
fun getUserInfo()
val userName = "example"

// 常數：UPPER_SNAKE_CASE
const val MAX_RETRY_COUNT = 3

// 資源檔案：snake_case
// layout: activity_user_profile.xml
// drawable: ic_user_avatar.xml
// string: user_profile_title
```

### UI 開發規範
- **ViewBinding**：替代 findViewById，提高類型安全
- **ConstraintLayout**：優先使用，減少嵌套層級
- **RecyclerView**：使用 DiffUtil 優化效能
- **Material Design**：遵循 Material Design 設計規範

### 資料處理
- **Coroutines**：處理異步操作，避免 AsyncTask
- **LiveData/StateFlow**：觀察資料變化
- **Room**：本地資料庫操作
- **Retrofit**：網路請求處理

## 效能最佳化

### 記憶體管理
- **避免記憶體洩漏**：
    - Activity/Fragment 中不持有靜態引用
    - 及時取消 Coroutine 和訂閱
    - 使用 WeakReference 處理回調
- **圖片處理**：使用 Glide 或 Coil，避免 Bitmap OOM
- **大型物件**：適時釋放，避免長時間持有

### UI 效能
- **避免過度繪製**：使用開發者選項檢查
- **RecyclerView 優化**：ViewHolder 模式，避免頻繁 findViewById
- **布局優化**：減少嵌套層級，使用 merge 標籤

## 安全性要求

### 資料安全
- **敏感資料加密**：使用 EncryptedSharedPreferences
- **網路安全**：強制使用 HTTPS，證書釘扎
- **輸入驗證**：所有用戶輸入都需驗證
- **權限管理**：最小權限原則，運行時權限檢查

### 程式碼安全
- **混淆保護**：發佈版本啟用 ProGuard/R8
- **API 金鑰保護**：使用 BuildConfig 或原生 NDK
- **日誌安全**：生產環境不輸出敏感資訊

## 測試策略

### 單元測試
- **ViewModel 測試**：使用 JUnit 和 Mockk
- **Repository 測試**：模擬網路和資料庫操作
- **工具類測試**：覆蓋邊界條件

### UI 測試
- **Espresso**：關鍵流程的 UI 測試
- **Screenshot 測試**：使用 Screenshot Testing 確保 UI 一致性

## 第三方函式庫偏好

### 網路處理
```kotlin
// Retrofit + OkHttp + Moshi/Gson
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.okhttp3:okhttp:4.11.0'
```

### 圖片載入
```kotlin
// Glide 或 Coil
implementation 'com.github.bumptech.glide:glide:4.15.1'
// 或
implementation 'io.coil-kt:coil:2.4.0'
```

### 依賴注入
```kotlin
// Hilt
implementation 'com.google.dagger:hilt-android:2.47'
```

## 錯誤處理

### 異常處理
- **具體異常**：拋出具體的異常類型，避免通用 Exception
- **用戶友好**：向用戶顯示有意義的錯誤訊息
- **日誌記錄**：記錄足夠的調試資訊
- **優雅降級**：提供備用方案

### 網路錯誤
```kotlin
sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: String) : NetworkResult<T>()
    data class Loading<T>(val isLoading: Boolean = true) : NetworkResult<T>()
}
```

## 版本控制

### Git 提交規範
```
feat: 新功能
fix: 修復 bug
docs: 文檔更新
style: 程式碼格式調整
refactor: 重構
test: 測試相關
chore: 構建過程或輔助工具變動
```

### 分支策略
- `main/master`：生產環境代碼
- `develop`：開發環境整合
- `feature/*`：功能開發分支
- `hotfix/*`：緊急修復分支

## 程式碼審查重點

### 檢查清單
- [ ] 架構模式是否正確
- [ ] 是否有記憶體洩漏風險
- [ ] 錯誤處理是否完整
- [ ] 單元測試是否充分
- [ ] 效能是否符合要求
- [ ] 安全性是否考慮周全
- [ ] 程式碼可讀性和維護性

## 特殊注意事項

### Android 版本兼容
- **最低支援版本**：API 21 (Android 5.0)
- **目標版本**：最新穩定版 API
- **向後兼容**：使用 AndroidX 函式庫

### 響應式設計
- **多螢幕支援**：手機、平板、摺疊螢幕
- **深色模式**：支援系統深色主題
- **無障礙功能**：contentDescription、TalkBack 支援

---

*此指令檔案會根據專案需求和 Android 開發最佳實踐持續更新*