import build.BuildConfig
import build.BuildCreator
import build.BuildDimensions
import deps.DependenciesVersions
import deps.TestBuildConfig
import deps.androidx
import deps.hilt
import deps.testDebugDeps
import deps.testDeps
import deps.testImplDeps
import flavors.BuildFlavor
import signing.BuildSigning
import signing.SigningTypes


plugins {
    id(plugs.BuildPlugins.ANDROID_APPLICATION)
    id(plugs.BuildPlugins.KOTLIN_ANDROID)
    id(plugs.BuildPlugins.KAPT)
    id(plugs.BuildPlugins.KOTLIN_COMPOSE) version "2.1.21"
    id(plugs.BuildPlugins.KTLINT)
    id(plugs.BuildPlugins.HILT)
}

android {
    namespace = BuildConfig.APP_ID
    compileSdk = BuildConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildConfig.APP_ID
        minSdk = BuildConfig.MIN_SDK_VERSION
        targetSdk = BuildConfig.TARGET_SDK_VERSION
        versionCode = BuildConfig.VERSION_CODE
        versionName = BuildConfig.VERSION_NAME

        testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        BuildSigning.Debug(project).create(this)
        BuildSigning.Release(project).create(this)
        BuildSigning.ReleaseExternalQA(project).create(this)
    }

    buildTypes {
        BuildCreator.Debug(project).create(this).apply {
            signingConfig = signingConfigs.getByName(SigningTypes.DEBUG)
        }
        BuildCreator.Release(project).create(this).apply {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName(SigningTypes.RELEASE)
        }
        BuildCreator.ReleaseExternalQA(project).create(this).apply {
            signingConfig = signingConfigs.getByName(SigningTypes.RELEASE_EXTERNAL_QA)
        }
    }

    flavorDimensions.add(BuildDimensions.APP)

    productFlavors {
        BuildFlavor.Dev.create(this)
        BuildFlavor.Prod.create(this)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DependenciesVersions.KOTLIN_COMPILER
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    androidx()
    hilt()
//    dataModule()
//    modelModule()
    testDeps()
    testImplDeps()
    testDebugDeps()

//    implementation(libs.androidx.core)
//    implementation(libs.androidx.lifecycle)
//    implementation(libs.activity.compose)
//    implementation(libs.material)
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.test.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.compose.ui.test.junit4)
//    debugImplementation(libs.compose.ui.tooling)
//    debugImplementation(libs.compose.ui.test.manifest)

//    implementation(libs.compose.ui)
//    implementation(libs.compose.ui.tooling.preview)
//    implementation(libs.compose.material)
//    implementation(libs.compose.material3)
//
//    implementation(libs.compose.compiler)
//    implementation(libs.lifecycle.runtime.compose)
//    implementation(libs.core.splashscreen)

    // 其餘依賴請依需求取消註解並調整語法
    // implementation("io.coil-kt:coil:2.4.0")
    // implementation("io.coil-kt:coil-compose:2.4.0")
    // implementation(platform("androidx.compose:compose-bom:2023.01.00"))
    // implementation("androidx.navigation:navigation-compose:2.6.0")
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    // implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // implementation(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
    // implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    // implementation("com.squareup.retrofit2:converter-moshi:2.4.0")
    // implementation("com.squareup.moshi:moshi:1.14.0")
    // kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
    // implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    // implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1")
    // implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    // implementation("androidx.room:room-runtime:$room_version")
    // kapt("androidx.room:room-compiler:$room_version")
    // implementation("androidx.room:room-ktx:$room_version")
    // implementation("com.github.skydoves:sandwich:$sandwich_version")
    // implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    // implementation("androidx.paging:paging-compose:$paging_version")
    // implementation("com.google.dagger:hilt-android:$hilt_version")
    // kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    // implementation("androidx.hilt:hilt-navigation-compose:$hilt_navigation_compose_version")
    // implementation("androidx.core:core-splashscreen:$splashscreen_version")
//    implementation("com.tencent:mmkv:1.3.0")
}
