plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("plugin.serialization") version "1.4.0"
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "de.syex.playground"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }

    compileOptions {
        coreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

val coroutines = "1.3.9"

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")

    // Android
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.10")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.7.0")
}

dependencies {
    val jUnit = "5.6.2"
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnit")
    testImplementation("org.junit.vintage:junit-vintage-engine:$jUnit")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines")
    testImplementation("com.google.truth:truth:1.0.1")
    testImplementation("io.mockk:mockk:1.10.0")
}
