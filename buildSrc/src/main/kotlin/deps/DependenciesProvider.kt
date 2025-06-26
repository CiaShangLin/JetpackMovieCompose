package deps

import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.room() {
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    annotationProcessor(Dependencies.roomCompiler)
}


fun DependencyHandler.retrofit() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.retrofitKotlinCoroutinesAdapter)
}

fun DependencyHandler.okHttp() {
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltNavigation)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltAgp)
    kapt(Dependencies.hiltCompilerKapt)
}

fun DependencyHandler.androidx() {
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.ANDROIDX_LIFECYCLE)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.LIFECYCLE_RUNTIME_COMPOSE)
    implementation(Dependencies.ACTIVITY_COMPOSE)
    implementation(Dependencies.CORE_SPLASHSCREEN)
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_UI_TOOLING)
    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_MATERIAL3)
    implementation(Dependencies.COMPOSE_COMPILER)
    implementation(Dependencies.navigation)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.googleJson)
}

fun DependencyHandler.kotlinx() {
    implementation(Dependencies.kotlinSerilaizations)
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
    testImplementation(TestDependencies.JUNIT)
}

fun DependencyHandler.testImplDeps() {
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_CORE)
    androidTestImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST)
}

fun DependencyHandler.testDebugDeps() {
    debugImplementation(TestDependencies.COMPOSE_UI_TOOLING_PREVIEW)
    debugImplementation(TestDependencies.COMPOSE_UI_TEST_MANIFEST)
}




