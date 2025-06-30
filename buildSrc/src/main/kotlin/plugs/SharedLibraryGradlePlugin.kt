package plugs

import build.BuildConfig
import build.BuildCreator
import build.BuildDimensions
import com.android.build.api.dsl.LibraryExtension
import deps.DependenciesVersions
import deps.TestBuildConfig
import flavors.BuildFlavor
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import signing.BuildSigning
import signing.SigningTypes


class SharedLibraryGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.addConfigurations()
        project.addAndroidConfigurations()
        project.addKotlinOptions()
    }

    private fun Project.addConfigurations() {
        plugins.apply(BuildPlugins.KOTLIN_ANDROID)
        plugins.apply(BuildPlugins.KAPT)
        plugins.apply(BuildPlugins.KSP)
        plugins.apply(BuildPlugins.KTLINT)
        plugins.apply(BuildPlugins.KOTLIN_SERIALIZATION)
        //plugins.apply(BuildPlugins.KOTLIN_COMPOSE)
    }

    private fun Project.addAndroidConfigurations() {
        extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = BuildConfig.COMPILE_SDK_VERSION
            defaultConfig.apply {
                minSdk = BuildConfig.MIN_SDK_VERSION
                testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
            }

            signingConfigs {
                BuildSigning.Debug(project).create(this)
                BuildSigning.Release(project).create(this)
                BuildSigning.ReleaseExternalQA(project).create(this)
            }

            buildTypes {
                BuildCreator.Debug(project).createLibrary(this).apply {
                    signingConfig = signingConfigs.getByName(SigningTypes.DEBUG)
                }
                BuildCreator.Release(project).createLibrary(this).apply {
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                    signingConfig = signingConfigs.getByName(SigningTypes.RELEASE)
                }
                BuildCreator.ReleaseExternalQA(project).createLibrary(this).apply {
                    signingConfig = signingConfigs.getByName(SigningTypes.RELEASE_EXTERNAL_QA)
                }
            }

            flavorDimensions.add(BuildDimensions.APP)

            productFlavors {
                BuildFlavor.Dev.createLibrary(this)
                BuildFlavor.Prod.createLibrary(this)
            }

            buildFeatures {
                //compose = true
                buildConfig = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = DependenciesVersions.KOTLIN_COMPILER
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }

    private fun Project.addKotlinOptions() {
        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
}