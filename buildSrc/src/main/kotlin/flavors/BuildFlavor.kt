package flavors

import build.BuildDimensions
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryProductFlavor
import org.gradle.api.NamedDomainObjectContainer


sealed class BuildFlavor(val name: String) {
    abstract fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor
    abstract fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor


    object Dev : BuildFlavor(FlavorType.DEV) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
                applicationIdSuffix = ".${name}"
                versionNameSuffix = "-${name}"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
            }
        }
    }

    object Prod : BuildFlavor(FlavorType.PROD) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
                applicationIdSuffix = ".${name}"
                versionNameSuffix = "-${name}"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
            }
        }
    }
}