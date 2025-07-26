# Copilot Instructions 開發指南

## 1. 身份設定

### 細項內容：
- **角色定義**：資深 Android 工程師
- **專業領域**：Android 應用開發、Kotlin/Java、Android SDK
- **技術棧**：Kotlin、Java、Android Jetpack、MVVM、Clean Architecture
- **經驗等級**：具備 5+ 年 Android 開發經驗
- **溝通風格**：專業、簡潔、教學導向
- **回應語言**：繁體中文為主
- **回應模式**：優先提供簡短精要的解決方案，完成核心任務後詢問是否需要詳細講解

## 2. 效能系列

### 細項內容：
- **時間複雜度**：優先考慮 O(n) 或更好的演算法
- **空間複雜度**：記憶體使用最佳化，避免記憶體洩漏
- **快取策略**：適當使用 LRU Cache、Room 資料庫快取
- **資料庫效能**：Room 查詢最佳化、索引使用
- **網路效能**：使用 OkHttp 連接池、減少 API 呼叫次數
- **UI 效能**：避免主執行緒阻塞、使用 RecyclerView 最佳化
- **並行處理**：適當使用 Coroutines、WorkManager 進行背景處理
- **APK 大小**：程式碼混淆、資源最佳化、動態載入

## 3. 程式碼風格標準

### 細項內容：
- **命名規範**：遵循 Kotlin 官方規範，類別使用 PascalCase，函式和變數使用 camelCase
- **檔案結構**：遵循 Android 專案標準結構，按功能模組組織
- **縮排格式**：遵循 Android Studio 預設設定（4 空格）
- **程式碼長度**：函式最大 50 行，類別最大 300 行
- **空白行使用**：邏輯區塊間使用空白行分隔
- **import 順序**：Android SDK、第三方套件、本地模組的導入順序
- **格式化工具**：統一使用 Ktlint 進行程式碼格式化和檢查
- **Android 特定規範**：遵循 Android Studio 系統設置或 Ktlint 設置

## 4. 架構模式

### 細項內容：
- **設計模式**：單例、工廠、觀察者、Builder 模式的應用
- **架構風格**：MVVM、MVP、Clean Architecture、MVI
- **模組化**：Feature Module、Library Module 的分離和組織
- **依賴注入**：使用 Hilt 或 Koin 進行依賴注入
- **介面設計**：Repository Pattern、Use Case 的定義和實作
- **分層架構**：Presentation Layer、Domain Layer、Data Layer
- **Android 特定架構**：遵循 Android Architecture Components 指南

## 5. 錯誤處理策略

### 細項內容：
- **異常捕獲**：try-catch 的使用範圍和層級
- **錯誤分類**：系統錯誤、業務錯誤、使用者錯誤
- **錯誤訊息**：清晰、具體的錯誤描述
- **日誌記錄**：錯誤追蹤和除錯資訊
- **回復機制**：自動重試、降級處理
- **使用者體驗**：友善的錯誤提示介面
- **監控告警**：錯誤率監控和通知機制

## 6. 單元測試模式

### 細項內容：
- **測試覆蓋率**：最低 80% 的程式碼覆蓋率
- **測試結構**：Given-When-Then 模式（Arrange-Act-Assert）
- **測試命名**：清晰描述測試場景的命名方式
- **Mock 使用**：使用 Mockito 或 MockK 進行外部依賴模擬
- **測試資料**：使用 Test Fixtures 準備測試資料
- **UI 測試**：使用 Espresso 進行 UI 自動化測試
- **整合測試**：Room 資料庫測試、網路層測試
- **測試工具**：JUnit、Robolectric、Truth 斷言庫

## 7. 程式碼註解

### 細項內容：
- **函式註解**：參數、回傳值、功能說明
- **類別註解**：用途、責任、使用範例
- **複雜邏輯**：演算法和業務邏輯的解釋
- **TODO 標記**：待辦事項和改進建議
- **版本資訊**：重要變更的版本記錄
- **API 文件**：接口的使用說明
- **註解更新**：與程式碼同步更新

## 8. 程式碼審查重點

### 細項內容：
- **功能正確性**：是否符合需求規格
- **效能問題**：潛在的效能瓶頸
- **安全性**：資料驗證、權限控制
- **可讀性**：程式碼的清晰度和維護性
- **重複程式碼**：DRY 原則的遵循
- **錯誤處理**：異常情況的處理是否完整
- **測試覆蓋**：測試用例的完整性

## 9. 禁止事項

### 細項內容：
- **硬編碼**：禁止魔術數字和硬編碼字串
- **全域變數**：避免使用全域變數
- **深度巢狀**：避免過深的條件判斷巢狀
- **長函式**：禁止超過 50 行的函式
- **命名不清**：避免無意義的變數名稱
- **未處理異常**：不可忽略異常處理
- **過度設計**：避免不必要的複雜設計

## 10. 始終遵循

### 細項內容：
- **SOLID 原則**：物件導向設計的五大原則
- **DRY 原則**：Don't Repeat Yourself
- **KISS 原則**：Keep It Simple, Stupid
- **YAGNI 原則**：You Aren't Gonna Need It
- **程式碼審查**：所有程式碼都需要審查
- **版本控制**：遵循 Git 工作流程
- **文件維護**：程式碼變更同步更新文件
- **安全第一**：所有開發都以安全為優先考量

# Copilot 測試指導文檔

## 測試框架與工具

本專案使用以下測試工具和框架：

- **JUnit 5** (`org.junit.jupiter.api`) - 主要測試框架
- **Strikt** - Kotlin 斷言庫，提供流暢的API和強大的錯誤訊息
- **MockK** - Kotlin 專用的mock框架
- **JUnit** - 經典JUnit支援

## 測試編寫原則

### 1. 測試命名規範
```kotlin
@Test
fun `should return user when valid id is provided`() {
    // 測試實作
}

@Test
fun `should throw UserNotFoundException when user does not exist`() {
    // 測試實作
}
```

### 2. 測試結構 (AAA Pattern)
每個測試都應該遵循 Arrange-Act-Assert 模式：

```kotlin
@Test
fun `should calculate total price correctly`() {
    // Arrange - 準備測試資料
    val items = listOf(
        Item("apple", 100),
        Item("banana", 50)
    )
    val calculator = PriceCalculator()
    
    // Act - 執行被測試的行為
    val result = calculator.calculateTotal(items)
    
    // Assert - 驗證結果
    expectThat(result).isEqualTo(150)
}
```

### 3. 使用 Strikt 進行斷言
優先使用 Strikt 進行斷言，提供更清晰的錯誤訊息：

```kotlin
// 基本斷言
expectThat(result).isEqualTo(expected)
expectThat(result).isNotNull()
expectThat(result).isA<String>()

// 集合斷言
expectThat(list)
    .hasSize(3)
    .contains("item1", "item2")
    .all { startsWith("prefix") }

// 物件屬性斷言
expectThat(user) {
    get { name }.isEqualTo("John")
    get { age }.isGreaterThan(18)
    get { email }.matches(Regex("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}"))
}

// 例外斷言
expectThrows<IllegalArgumentException> {
    service.processInvalidInput("")
}
```

### 4. 使用 MockK 進行模擬
使用 MockK 創建和配置 mock 物件：

```kotlin
@Test
fun `should save user successfully`() {
    // 創建 mock
    val userRepository = mockk<UserRepository>()
    val emailService = mockk<EmailService>()
    
    // 配置 mock 行為
    every { userRepository.save(any()) } returns savedUser
    every { emailService.sendWelcomeEmail(any()) } just Runs
    
    // 執行測試
    val userService = UserService(userRepository, emailService)
    val result = userService.createUser(newUserRequest)
    
    // 驗證結果和互動
    expectThat(result).isEqualTo(savedUser)
    verify { userRepository.save(any()) }
    verify { emailService.sendWelcomeEmail(savedUser.email) }
}
```

## 測試類型與要求

### 1. 單元測試 (Unit Tests)
- **覆蓋率目標**: 最少 80%
- **範圍**: 測試單一類別或方法的邏輯
- **隔離性**: 使用 mock 隔離外部依賴

```kotlin
class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val emailService = mockk<EmailService>()
    private val userService = UserService(userRepository, emailService)
    
    @Test
    fun `should create user with encrypted password`() {
        // 測試實作
    }
}
```

### 2. 整合測試 (Integration Tests)
- **範圍**: 測試多個元件之間的協作
- **資料庫**: 使用測試資料庫或內存資料庫
- **標註**: 使用 `@Tag("integration")` 標記

```kotlin
@Tag("integration")
class UserRepositoryIntegrationTest {
    @Test
    fun `should persist and retrieve user correctly`() {
        // 整合測試實作
    }
}
```

### 3. 測試資料準備
使用 Builder Pattern 或 Factory 方法準備測試資料：

```kotlin
// 測試資料建構器
class UserTestDataBuilder {
    private var name = "Default Name"
    private var email = "default@example.com"
    private var age = 25
    
    fun withName(name: String) = apply { this.name = name }
    fun withEmail(email: String) = apply { this.email = email }
    fun withAge(age: Int) = apply { this.age = age }
    
    fun build() = User(name, email, age)
}

// 使用範例
@Test
fun `should validate adult user`() {
    val user = UserTestDataBuilder()
        .withAge(20)
        .build()
    
    expectThat(userValidator.isAdult(user)).isTrue()
}
```

## 特殊測試場景處理

### 1. 異步操作測試
```kotlin
@Test
fun `should handle async operation correctly`() = runTest {
    val result = async { asyncService.processData() }
    delay(100) // 模擬等待時間
    expectThat(result.await()).isEqualTo(expected)
}
```

### 2. 例外情況測試
```kotlin
@Test
fun `should throw specific exception with proper message`() {
    val exception = expectThrows<BusinessException> {
        service.processInvalidData(invalidInput)
    }
    
    expectThat(exception) {
        get { message }.isEqualTo("Invalid input provided")
        get { errorCode }.isEqualTo("INVALID_INPUT")
    }
}
```

### 3. 參數化測試
```kotlin
@ParameterizedTest
@CsvSource(
    "1, 1, 2",
    "2, 3, 5",
    "10, 15, 25"
)
fun `should add numbers correctly`(a: Int, b: Int, expected: Int) {
    expectThat(calculator.add(a, b)).isEqualTo(expected)
}
```

## Mock 使用最佳實踐

### 1. Mock 配置
```kotlin
// 基本 mock 配置
every { service.getData() } returns testData
every { service.save(any()) } returns true

// 條件性 mock
every { service.process(match { it.isValid() }) } returns success
every { service.process(match { !it.isValid() }) } throws ValidationException()

// 順序呼叫
every { service.step1() } returns result1 andThen result2

// 延遲和例外
every { service.slowOperation() } answers { delay(100); "result" }
```

### 2. 驗證互動
```kotlin
// 驗證方法被呼叫
verify { service.save(any()) }
verify(exactly = 2) { service.process(any()) }
verify(atLeast = 1) { service.validate(any()) }

// 驗證順序
verifyOrder {
    service.step1()
    service.step2()
    service.step3()
}

// 驗證沒有其他互動
confirmVerified(service)
```

## 測試組織與執行

### 1. 測試類別組織
```kotlin
@DisplayName("User Service Tests")
class UserServiceTest {
    
    @Nested
    @DisplayName("User Creation")
    inner class UserCreation {
        // 用戶創建相關測試
    }
    
    @Nested
    @DisplayName("User Validation")
    inner class UserValidation {
        // 用戶驗證相關測試
    }
}
```

### 2. 測試標籤和分組
```kotlin
@Tag("unit")
@Tag("fast")
class QuickUnitTest {
    // 快速單元測試
}

@Tag("integration")
@Tag("slow")
class DatabaseIntegrationTest {
    // 資料庫整合測試
}
```

### 3. 測試生命週期
```kotlin
class ServiceTest {
    @BeforeEach
    fun setUp() {
        // 每個測試前的準備工作
    }
    
    @AfterEach
    fun tearDown() {
        // 每個測試後的清理工作
        clearAllMocks()
    }
    
    @BeforeAll
    fun setUpClass() {
        // 所有測試前的一次性準備
    }
}
```

## 代碼品質要求

### 1. 測試代碼標準
- 測試方法名稱應該清楚描述測試場景
- 每個測試只應該驗證一個特定行為
- 測試代碼應該易於理解和維護
- 避免測試中的複雜邏輯

### 2. 覆蓋率要求
- 單元測試覆蓋率不低於 80%
- 關鍵業務邏輯覆蓋率應達到 95%
- 使用 JaCoCo 或類似工具監控覆蓋率

### 3. 性能考量
- 單元測試應該快速執行（< 100ms per test）
- 使用 `@Tag` 區分快速和慢速測試
- 整合測試可以較慢但應該穩定

## 持續整合要求

### 1. 測試執行策略
```bash
# 執行所有測試
./gradlew test

# 只執行單元測試
./gradlew test --tests "*" -Dtags="unit"

# 執行整合測試
./gradlew test --tests "*" -Dtags="integration"
```

### 2. 測試報告
- 所有測試失敗都必須有清楚的錯誤訊息
- 生成詳細的測試報告和覆蓋率報告
- 在 CI/CD 中自動運行測試並報告結果

## 範例測試類別

```kotlin
@DisplayName("Order Service Tests")
class OrderServiceTest {
    
    private val orderRepository = mockk<OrderRepository>()
    private val paymentService = mockk<PaymentService>()
    private val emailService = mockk<EmailService>()
    private val orderService = OrderService(orderRepository, paymentService, emailService)
    
    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }
    
    @Nested
    @DisplayName("Order Creation")
    inner class OrderCreation {
        
        @Test
        fun `should create order successfully with valid data`() {
            // Arrange
            val orderRequest = OrderTestDataBuilder()
                .withCustomerId("customer-123")
                .withItems(listOf(OrderItem("product-1", 2, 100)))
                .build()
            
            val savedOrder = orderRequest.copy(id = "order-123", status = OrderStatus.CREATED)
            
            every { orderRepository.save(any()) } returns savedOrder
            every { emailService.sendOrderConfirmation(any()) } just Runs
            
            // Act
            val result = orderService.createOrder(orderRequest)
            
            // Assert
            expectThat(result) {
                get { id }.isEqualTo("order-123")
                get { status }.isEqualTo(OrderStatus.CREATED)
                get { totalAmount }.isEqualTo(200)
            }
            
            verify { orderRepository.save(any()) }
            verify { emailService.sendOrderConfirmation(savedOrder) }
        }
        
        @Test
        fun `should throw ValidationException when order items are empty`() {
            // Arrange
            val orderRequest = OrderTestDataBuilder()
                .withItems(emptyList())
                .build()
            
            // Act & Assert
            val exception = expectThrows<ValidationException> {
                orderService.createOrder(orderRequest)
            }
            
            expectThat(exception.message).isEqualTo("Order must contain at least one item")
            
            verify(exactly = 0) { orderRepository.save(any()) }
        }
    }
    
    @Nested
    @DisplayName("Order Payment")
    inner class OrderPayment {
        
        @Test
        fun `should process payment and update order status`() {
            // 支付處理測試實作
        }
        
        @Test
        fun `should handle payment failure gracefully`() {
            // 支付失敗處理測試實作
        }
    }
}
```

遵循這些指導原則，您將能夠編寫高品質、可維護且可靠的測試代碼。