import org.gradle.api.tasks.compile.JavaCompile

plugins {
    kotlin("jvm")
    application
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

application {
    mainClass.set(providers.gradleProperty("mainClass").orElse("MainKt"))
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}
