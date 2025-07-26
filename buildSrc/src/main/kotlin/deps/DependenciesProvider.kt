package deps

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project


fun DependencyHandler.room() {
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomRuntime)
    //implementation(Dependencies.roomPaging)
    ksp(Dependencies.roomCompiler)
}


fun DependencyHandler.retrofit() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.retrofitKotlinCoroutinesAdapter)
    implementation(Dependencies.retrofitKotlinxSerialization)
}

fun DependencyHandler.okHttp() {
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)

    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltNavigation)
    ksp(Dependencies.hiltAgp)
    ksp(Dependencies.hiltCompilerKapt)
}

fun DependencyHandler.androidx() {
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.ANDROIDX_LIFECYCLE)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.LIFECYCLE_RUNTIME_COMPOSE)
    implementation(Dependencies.ACTIVITY_COMPOSE)
    implementation(Dependencies.CORE_SPLASHSCREEN)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.googleJson)
    implementation(Dependencies.workKtx)
    implementation(Dependencies.pagingRuntime)
    implementation(Dependencies.pagingCompose)
    implementation(Dependencies.coil)
    implementation(Dependencies.coilNetwork)
    implementationPlatform(Dependencies.COMPOSE_BOM)
    implementation(Dependencies.COMPOSE_FOUNDATION)
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_UI_TOOLING)
    implementation(Dependencies.COMPOSE_ANIMATION)
    implementation(Dependencies.COMPOSE_ANIMATION_CORE)
    implementation(Dependencies.COMPOSE_FOUNDATION_LAYOUT)
    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_MATERIAL3)
    implementation(Dependencies.COMPOSE_RUNTIME)
    implementation(Dependencies.COMPOSE_ADAPTIVE_NAVIGATION)
    implementation(Dependencies.COMPOSE_MATERIAL3_ADAPTIVE_LAYOUT)
    implementation(Dependencies.COMPOSE_MATERIAL3_ADAPTIVE)
    implementation(Dependencies.COMPOSE_ADAPTIVE_NAVIGATION_SUITE)
    implementation(Dependencies.COMPOSE_MATERIAL_ICON_EXTENDED)
}

fun DependencyHandler.lottie() {
    implementation(Dependencies.lottieCompose)
}

fun DependencyHandler.kotlinx() {
    implementation(Dependencies.kotlinSerilaizations)
}

fun DependencyHandler.coroutine() {
    implementation(Dependencies.coroutines)
}

fun DependencyHandler.paging3() {
    implementation(Dependencies.pagingRuntime)
    implementation(Dependencies.pagingCompose)
}

fun DependencyHandler.protoDataStore() {
    implementation(Dependencies.datastore)
    implementation(Dependencies.protoBufJavaLite)
    implementation(Dependencies.protoBufKotlinLite)
}

fun DependencyHandler.chucker() {
    debugImplementation(Dependencies.chuckerDebug)
    releaseImplementation(Dependencies.chuckerRelease)
}


fun DependencyHandler.testDeps() {
    // JUnit 4 (保持現有相容性)
    testImplementation(TestDependencies.JUNIT)

    // JUnit 5 (Jupiter) - 現代化測試框架
    testImplementation(TestDependencies.JUNIT5_API)
    testImplementation(TestDependencies.JUNIT5_ENGINE)
    testImplementation(TestDependencies.JUNIT5_PARAMS)

    // MockK - Kotlin 模擬庫
    testImplementation(TestDependencies.MOCKK)

    // Strikt - 斷言庫
    testImplementation(TestDependencies.STRIKT)

    // Coroutines Test - 協程測試支援
    testImplementation(TestDependencies.KOTLINX_COROUTINES_TEST)
}

fun DependencyHandler.testImplDeps() {
    // Android 測試依賴
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_CORE)

    // MockK Android
    androidTestImplementation(TestDependencies.MOCKK_ANDROID)

    // Compose 測試依賴
    androidTestImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST)
    debugImplementation(TestDependencies.COMPOSE_UI_TOOLING_PREVIEW)
    debugImplementation(TestDependencies.COMPOSE_UI_TEST_MANIFEST)
}

fun DependencyHandler.testDebugDeps() {
    debugImplementation(TestDependencies.COMPOSE_UI_TOOLING_PREVIEW)
    debugImplementation(TestDependencies.COMPOSE_UI_TEST_MANIFEST)
}

fun DependencyHandler.domainModule() {
    moduleImplementation(project(":core:domain"))
}

fun DependencyHandler.dataModule() {
    moduleImplementation(project(":core:data"))
}

fun DependencyHandler.networkModule() {
    moduleImplementation(project(":core:network"))
}

fun DependencyHandler.databaseModule() {
    moduleImplementation(project(":core:database"))
}

fun DependencyHandler.datastoreModule() {
    moduleImplementation(project(":core:datastore"))
}

fun DependencyHandler.modelModule() {
    moduleImplementation(project(":core:model"))
}

fun DependencyHandler.commonModule() {
    moduleImplementation(project(":core:common"))
}

fun DependencyHandler.designsystemModule() {
    moduleImplementation(project(":core:designsystem"))
}

fun DependencyHandler.uiModule() {
    moduleImplementation(project(":core:ui"))
}

fun DependencyHandler.homeModule() {
    moduleImplementation(project(":feature:home"))
}

fun DependencyHandler.settingModule() {
    moduleImplementation(project(":feature:setting"))
}

fun DependencyHandler.searchModule() {
    moduleImplementation(project(":feature:search"))
}

fun DependencyHandler.collectModule() {
    moduleImplementation(project(":feature:collect"))
}

fun DependencyHandler.historyModule() {
    moduleImplementation(project(":feature:history"))
}

fun DependencyHandler.moviedetailModule() {
    moduleImplementation(project(":feature:moviedetail"))
}

fun DependencyHandler.actorModule() {
    moduleImplementation(project(":feature:actor"))
}
