plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.lifecycleapp"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        applicationId = "com.example.lifecycleapp"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    sourceSets["main"].apply {
        manifest.srcFile("main/AndroidManifest.xml")
        java.srcDirs("main/java")
        res.srcDirs("main/res")
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("com.jakewharton.timber:timber:5.0.1")
}
