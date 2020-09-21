buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.4.0"))
        classpath("com.android.tools.build:gradle:4.2.0-alpha11")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
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
