// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.5.0-release-764")
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")

    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}")

    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}


tasks.register("clean",Delete::class) {
    delete(rootProject.buildDir)
}