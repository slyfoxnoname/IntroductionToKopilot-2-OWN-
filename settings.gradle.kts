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

rootProject.name = "kotlin-homeworks-26"

include(":hm4")
project(":hm4").projectDir = file("src/part2/HM4")

include(":hm6")
project(":hm6").projectDir = file("src/part2/HM6/app")
