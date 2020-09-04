plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
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
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
}

dependencies {
    val jUnit = "5.6.2"
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnit")
    testImplementation("org.junit.vintage:junit-vintage-engine:$jUnit")
}
