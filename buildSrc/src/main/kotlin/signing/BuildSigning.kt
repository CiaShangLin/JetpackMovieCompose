package signing

import com.android.build.api.dsl.ApkSigningConfig
import extensions.getKeyProperty
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import java.io.File

sealed class BuildSigning(val name: String) {
    abstract fun create(namedDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>)


    class Debug(private val project: Project) : BuildSigning(SigningTypes.DEBUG) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>) {
            namedDomainObjectContainer.getByName(name) {
                //這邊的值是固定寫死的
                storeFile = File(project.rootProject.rootDir,"debug.keystore")
                storePassword = "android"
                keyAlias = "androiddebugkey"
                keyPassword = "android"
                enableV1Signing = true
                enableV2Signing = true
            }
        }
    }

    class Release(private val project: Project) : BuildSigning(SigningTypes.RELEASE) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>) {
            namedDomainObjectContainer.create(name) {
//                storeFile = File(project.getKeyProperty("qa_release_key.storeFile"))
//                storePassword = project.getKeyProperty("qa_release_key.storePassword")
//                keyAlias = project.getKeyProperty("qa_release_key.keyAlias")
//                keyPassword = project.getKeyProperty("qa_release_key.keyPassword")
                enableV1Signing = true
                enableV2Signing = true
            }
        }
    }

    class ReleaseExternalQA(private val project: Project) :
        BuildSigning(SigningTypes.RELEASE_EXTERNAL_QA) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>) {
            namedDomainObjectContainer.create(name) {
//                storeFile = File(project.getKeyProperty("qa_release_key.storeFile"))
//                storePassword = project.getKeyProperty("qa_release_key.storePassword")
//                keyAlias = project.getKeyProperty("qa_release_key.keyAlias")
//                keyPassword = project.getKeyProperty("qa_release_key.keyPassword")
                enableV1Signing = true
                enableV2Signing = true
            }
        }
    }
}