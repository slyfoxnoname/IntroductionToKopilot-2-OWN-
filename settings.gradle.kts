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

include(":part1", ":hm3", ":hm4", ":hm5", ":hm6",":hm7", ":hm8", ":hm9", ":hm10",":hm11")
project(":part1").projectDir = file("part1")
project(":hm3").projectDir = file("part2/hm3")
project(":hm4").projectDir = file("part2/hm4")
project(":hm5").projectDir = file("part2/hm5")
project(":hm6").projectDir = file("part2/hm6")
project(":hm7").projectDir = file("part2/hm7")
project(":hm8").projectDir = file("part2/hm8")
project(":hm9").projectDir = file("part2/hm9")
project(":hm10").projectDir = file("part2/hm10")
project(":hm11").projectDir = file("part2/hm11")