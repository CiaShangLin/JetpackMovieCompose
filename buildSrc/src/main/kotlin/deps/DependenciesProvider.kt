package deps

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project


fun DependencyHandler.room() {
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomPaging)
    kapt(Dependencies.roomCompiler)
    annotationProcessor(Dependencies.roomCompiler)
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
    kapt(Dependencies.hiltCompiler)

    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltNavigation)
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
    implementation(Dependencies.navigation)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.googleJson)
    implementation(Dependencies.workKtx)

}

fun DependencyHandler.kotlinx() {
    implementation(Dependencies.kotlinSerilaizations)
}

fun DependencyHandler.coroutine() {
    implementation(Dependencies.coroutines)
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





