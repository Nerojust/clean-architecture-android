import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "clean-architecture-android"
include(":app")
include(":core:common")
include(":core:network")
include(":core:ui")
include(":data")
include(":domain")
include(":feature:repolist")
include(":feature:repodetail")
