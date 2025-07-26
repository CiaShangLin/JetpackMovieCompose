package deps

object TestDependencies {
    // JUnit 4 (保持現有相容性)
    const val JUNIT = "junit:junit:${DependenciesVersions.JUNIT}"

    // JUnit 5 (Jupiter) - 現代化測試框架
    const val JUNIT5_API = "org.junit.jupiter:junit-jupiter-api:${DependenciesVersions.JUNIT5}"
    const val JUNIT5_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${DependenciesVersions.JUNIT5}"
    const val JUNIT5_PARAMS = "org.junit.jupiter:junit-jupiter-params:${DependenciesVersions.JUNIT5}"

    // MockK - Kotlin 模擬庫
    const val MOCKK = "io.mockk:mockk:${DependenciesVersions.MOCKK}"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:${DependenciesVersions.MOCKK}"

    // Strikt - 斷言庫
    const val STRIKT = "io.strikt:strikt-core:${DependenciesVersions.STRIKT}"

    // Coroutines Test - 協程測試支援
    const val KOTLINX_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependenciesVersions.KOTLINX_COROUTINES_TEST}"

    // Android 測試依賴
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:${DependenciesVersions.ANDROIDX_TEST_JUNIT}"
    const val ANDROIDX_ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${DependenciesVersions.ANDROIDX_ESPRESSO_CORE}"

    // Compose 測試依賴
    const val ANDROIDX_COMPOSE_UI_TEST = "androidx.compose.ui:ui-test-junit4:${DependenciesVersions.COMPOSE}"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${DependenciesVersions.COMPOSE}"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${DependenciesVersions.COMPOSE}"
}