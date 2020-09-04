buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.4.0"))
        classpath("com.android.tools.build:gradle:4.0.1")
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
