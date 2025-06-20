package build


import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.LibraryBuildType
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project


sealed class BuildCreator(val name: String) {

    abstract fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType
    abstract fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType

    class Debug(private val project: Project) : BuildCreator(BuildTypes.DEBUG) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Debug.isMinifyEnabled
                enableUnitTestCoverage = Build.Debug.enableUnitTestCoverage
                isDebuggable = Build.Debug.isDebuggable
                versionNameSuffix = Build.Debug.versionNameSuffix
                applicationIdSuffix = Build.Debug.applicationIdSuffix
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Debug.isMinifyEnabled
                enableUnitTestCoverage = Build.Debug.enableUnitTestCoverage
            }
        }
    }

    class Release(private val project: Project) : BuildCreator(BuildTypes.RELEASE) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Release.isMinifyEnabled
                enableUnitTestCoverage = Build.Release.enableUnitTestCoverage
                isDebuggable = Build.Release.isDebuggable
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Release.isMinifyEnabled
                enableUnitTestCoverage = Build.Release.enableUnitTestCoverage
            }
        }
    }

    class ReleaseExternalQA(private val project: Project) : BuildCreator(BuildTypes.RELEASE_EXTERNAL_QA) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.create(name) {
                isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
                enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage
                isDebuggable = Build.ReleaseExternalQa.isDebuggable
                versionNameSuffix = Build.ReleaseExternalQa.versionNameSuffix
                applicationIdSuffix = Build.ReleaseExternalQa.applicationIdSuffix
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.create(name) {
                isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
                enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage
            }
        }
    }
}