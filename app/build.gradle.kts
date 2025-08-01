import build.BuildConfig
import build.BuildCreator
import build.BuildDimensions
import deps.DependenciesVersions
import deps.TestBuildConfig
import deps.actorModule
import deps.androidx
import deps.collectModule
import deps.commonModule
import deps.dataModule
import deps.designsystemModule
import deps.domainModule
import deps.hilt
import deps.historyModule
import deps.homeModule
import deps.kotlinx
import deps.modelModule
import deps.moviedetailModule
import deps.searchModule
import deps.settingModule
import deps.testDebugDeps
import deps.testDeps
import deps.testImplDeps
import deps.uiModule
import flavors.BuildFlavor
import signing.BuildSigning
import signing.SigningTypes


plugins {
    id(plugs.BuildPlugins.ANDROID_APPLICATION)
    id(plugs.BuildPlugins.KOTLIN_ANDROID)
    // id(plugs.BuildPlugins.KAPT)
    id(plugs.BuildPlugins.KSP)
    id(plugs.BuildPlugins.KOTLIN_COMPOSE) version deps.DependenciesVersions.KOTLIN_COMPILER
    id(plugs.BuildPlugins.KTLINT)
    id(plugs.BuildPlugins.HILT) version deps.DependenciesVersions.HILT
    id(plugs.BuildPlugins.KOTLIN_SERIALIZATION)
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
            isMinifyEnabled = true
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
    kotlinx()

    // core modules
    dataModule()
    domainModule()
    modelModule()
    commonModule()
    designsystemModule()
    uiModule()

    // feature modules
    homeModule()
    settingModule()
    searchModule()
    collectModule()
    historyModule()
    moviedetailModule()
    actorModule()

    testDeps()
    testImplDeps()
    testDebugDeps()
}
