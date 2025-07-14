import deps.chucker
import deps.commonModule
import deps.hilt
import deps.kotlinx
import deps.modelModule
import deps.okHttp
import deps.retrofit
import deps.testDeps
import deps.testImplDeps
import extensions.getKeyProperty
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        // 從 key.properties 讀取 API key，如果不存在則使用空字串
        buildConfigField("String", "TMDB_API_KEY", "\"${getKeyProperty("TMDB_API_KEY")}\"")
    }
}

dependencies {
    chucker()
    retrofit()
    okHttp()
    kotlinx()
    hilt()
    modelModule()
    commonModule()
    testDeps()
    testImplDeps()
}
