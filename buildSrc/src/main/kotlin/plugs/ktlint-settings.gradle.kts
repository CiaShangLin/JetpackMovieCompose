import com.android.build.gradle.internal.tasks.factory.dependsOn

// 建立一個名為 ktlint 的自定義配置（Configuration）
// 用於管理 ktlint 相關的依賴項
val ktlint: Configuration by configurations.creating

dependencies {
    // 添加 Pinterest 的 ktlint 工具作為依賴
    // 版本為 0.49.0
    ktlint("com.pinterest:ktlint:0.49.0") {
        attributes {
            // 設定 bundling 屬性為 EXTERNAL
            // 表示這是一個外部的依賴，不會被打包到最終的 APK 中
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

// 註冊一個名為 "ktlintFormat" 的 JavaExec 任務
// ktlintFormat 會檢查並格式化 Kotlin 程式碼風格，嘗試自動修復一些違規問題
tasks.register<JavaExec>("ktlintFormat") {
    // 將此任務歸類到驗證群組（VERIFICATION_GROUP）
    group = LifecycleBasePlugin.VERIFICATION_GROUP

    // 設定任務的描述
    description = "Check Kotlin code style and format"

    // 設定執行時的 classpath，使用前面定義的 ktlint 配置
    classpath = ktlint

    // 指定要執行的主類別
    mainClass.set("com.pinterest.ktlint.Main")

    // 添加 JVM 參數以解決模組系統的存取限制
    // 這是因為較新版本的 Java 對反射存取有更嚴格的限制
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")

    // 設定 ktlint 的命令列參數
    // 詳細用法可參考：https://pinterest.github.io/ktlint/install/cli/#command-line-usage
    args(
        "-F",                    // --format: 啟用自動格式化模式
        "**/src/**/*.kt",        // 檢查所有 src 目錄下的 .kt 檔案
        "**.kts",               // 檢查所有 .kts 檔案（Kotlin 腳本檔案）
        "!**/build/**",         // 排除 build 目錄下的檔案
    )
}

// 設定任務依賴關係
tasks {
    // 讓 "preBuild" 任務依賴於 "ktlintFormat" 任務
    // 這意味著在執行 preBuild 之前，會先執行 ktlintFormat
    // 確保在建置專案前，程式碼風格已經被檢查和格式化
    named("preBuild").dependsOn("ktlintFormat")
}