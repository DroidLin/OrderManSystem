pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "OrderManSystem"
include(":app")
include(":common")
include(":feature")
include(":common:foundation")
include(":common:foundation-ui")
include(":feature:login")
include(":common:network")
include(":common:datastore")
include(":common:database")
include(":feature:user")
