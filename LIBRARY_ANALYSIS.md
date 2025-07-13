# JetpackMovieCompose 專案 Library 分析報告

## 📋 專案概述
這是一個基於 Modern Android Architecture 的電影應用，採用 Clean Architecture 模式，使用 Jetpack Compose 作為 UI 框架。

## 🏗️ 架構模式
- **整體架構**: Clean Architecture + MVVM
- **模組化**: Multi-module architecture  
- **UI 框架**: Jetpack Compose
- **依賴注入**: Hilt

---

## 📚 Library 分類簡化分析

### 🎨 UI 相關
- **Compose**: BOM 2025.05.00, UI, Foundation, Material3, Animation
- **Material Design**: Material3, Adaptive Layout, Navigation Suite, Extended Icons
- **圖片載入**: Coil 3.2.0 (Compose + Network)
- **動畫**: Lottie Compose 6.6.6
- **啟動畫面**: Core Splashscreen 1.0.1

### 🗄️ 資料存儲
- **本地資料庫**: Room 2.6.1 (Runtime, KTX, Compiler)
- **偏好設定**: DataStore 1.1.1 + Protocol Buffers 4.27.3

### 🌐 網路通訊
- **HTTP 客戶端**: Retrofit 2.11.0 + OkHttp 4.12.0
- **序列化**: Gson 2.11.0 + Kotlin Serialization 1.6.2
- **除錯工具**: Chucker 4.1.0

### 🔧 依賴注入
- **DI 框架**: Hilt 2.51.1 (Android, Compose, Navigation)

### 🧭 導航 & 分頁
- **導航**: Navigation Compose 2.9.0
- **分頁**: Paging 3.3.6 (Runtime + Compose)

### 🔄 異步處理
- **協程**: Kotlinx Coroutines 1.3.9
- **背景任務**: WorkManager 2.7.1

### 🏛️ Android 架構組件
- **核心**: AndroidX Core 1.16.0, Lifecycle 2.9.1
- **UI**: Activity Compose 1.10.1, Material Components 1.12.0

### 🧪 測試工具
- **測試框架**: JUnit 4.13.2, AndroidX Test, Espresso 3.5.1, Compose UI Test

---

## 🏗️ 模組架構

### **Core Modules (9個)**
- **common** - 共用工具
- **data** - 資料層  
- **database** - Room 配置
- **datastore** - 偏好設定
- **designsystem** - 設計系統
- **domain** - 業務邏輯
- **model** - 資料模型
- **network** - 網路層
- **ui** - 共用 UI

### **Feature Modules (7個)**
- **actor** - 演員詳情
- **collect** - 收藏功能
- **history** - 歷史記錄
- **home** - 首頁
- **moviedetail** - 電影詳情
- **search** - 搜尋
- **setting** - 設定

---

