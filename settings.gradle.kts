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

include(":part1", ":hm3", ":hm4", ":hm5", ":hm6")
project(":part1").projectDir = file("part1")
project(":hm3").projectDir = file("part2/hm3")
project(":hm4").projectDir = file("part2/hm4")
project(":hm5").projectDir = file("part2/hm5")
project(":hm6").projectDir = file("part2/hm6")
