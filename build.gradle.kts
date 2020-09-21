buildscript {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }

    val kotlinVersion = "1.4.20-M1"
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.android.tools.build:gradle:4.2.0-alpha12")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    // Enable JUnit5
    tasks.withType(Test::class.java) {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
