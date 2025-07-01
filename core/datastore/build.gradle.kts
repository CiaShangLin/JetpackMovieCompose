import deps.Dependencies.protoBufArtifact
import deps.commonModule
import deps.hilt
import deps.kotlinx
import deps.modelModule
import deps.protoDataStore
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
    id(plugs.BuildPlugins.GOOGLE_PROTOBUF)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.datastore"

    protobuf {
        protoc {
            artifact = protoBufArtifact
        }
        generateProtoTasks {
            all().forEach { task ->
                task.plugins {
                    create("kotlin").apply {
                        option("lite")
                    }
                }
                task.plugins {
                    create("java").apply {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {
    modelModule()
    commonModule()
    kotlinx()
    protoDataStore()
    hilt()
    testDeps()
    testImplDeps()
}
