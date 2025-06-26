package deps

object Dependencies {
    // AndroidX
    const val ANDROIDX_CORE = "androidx.core:core-ktx:${DependenciesVersions.ANDROIDX_CORE}"
    const val ANDROIDX_LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:${DependenciesVersions.LIFECYCLE_RUNTIME}"
    const val LIFECYCLE_RUNTIME_COMPOSE = "androidx.lifecycle:lifecycle-runtime-compose:${DependenciesVersions.LIFECYCLE_RUNTIME}"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${DependenciesVersions.ACTIVITY_COMPOSE}"
    const val CORE_SPLASHSCREEN = "androidx.core:core-splashscreen:${DependenciesVersions.CORE_SPLASHSCREEN}"

    // Compose
    const val COMPOSE_UI = "androidx.compose.ui:ui:${DependenciesVersions.COMPOSE}"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${DependenciesVersions.COMPOSE}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${DependenciesVersions.COMPOSE}"
    const val COMPOSE_MATERIAL3 = "androidx.compose.material3:material3:${DependenciesVersions.MATERIAL3}"
    const val COMPOSE_COMPILER = "androidx.compose.compiler:compiler:${DependenciesVersions.COMPILER}"


    // Google Material
    const val MATERIAL = "com.google.android.material:material:${DependenciesVersions.MATERIAL}"


    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${DependenciesVersions.RETROFIT}"
    const val retrofitConverterGson =
        "com.squareup.retrofit2:converter-gson:${DependenciesVersions.RETROFIT}"
    const val retrofitKotlinCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${DependenciesVersions.RETROFIT_COROUTINE_ADAPTER_VERSION}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${DependenciesVersions.OKHTTP}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${DependenciesVersions.OKHTTP}"


    // Chucker
    const val chuckerDebug = "com.github.chuckerteam.chucker:library:${DependenciesVersions.CHUCKER}"
    const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${DependenciesVersions.CHUCKER}"

    const val kotlinSerilaizations = "org.jetbrains.kotlinx:kotlinx-serialization-json:${DependenciesVersions.KOTLIN_SERIALIZATIONS}"

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${DependenciesVersions.HILT}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${DependenciesVersions.HILT}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${DependenciesVersions.HILT}"
    const val hiltCompose = "androidx.hilt:hilt-work:${DependenciesVersions.HILT_COMPOSE}"
    const val hiltCompilerKapt = "androidx.hilt:hilt-compiler:${DependenciesVersions.HILT_COMPOSE}"
    const val hiltNavigation =
        "androidx.hilt:hilt-navigation-compose:${DependenciesVersions.HILT_COMPOSE}"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${DependenciesVersions.ROOM}"
    const val roomCompiler = "androidx.room:room-compiler:${DependenciesVersions.ROOM}"
    const val roomKtx = "androidx.room:room-ktx:${DependenciesVersions.ROOM}"

    const val datastore = "androidx.datastore:datastore:${DependenciesVersions.DATA_STORE}"
    const val protoBufJavaLite = "com.google.protobuf:protobuf-javalite:${DependenciesVersions.PROTO_BUF_JAVA}"
    const val protoBufKotlinLite = "com.google.protobuf:protobuf-kotlin-lite:${DependenciesVersions.PROTO_BUF_KOTLIN}"
    const val protoBufArtifact = "com.google.protobuf:protoc:${DependenciesVersions.PROTO_BUF_KOTLIN}"
}