pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "kotlin-homeworks-26"

include(":hm4")
project(":hm4").projectDir = file("src/part2/HM4")
