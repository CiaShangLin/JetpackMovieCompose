plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    // id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.shang.jetpackmoviecompose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.shang.jetpackmoviecompose"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)
    implementation(libs.activity.compose)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)

    implementation(libs.compose.compiler)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.core.splashscreen)

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