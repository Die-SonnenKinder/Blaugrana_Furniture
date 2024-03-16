buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        val nav_version = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
    }
}


plugins {
    id("com.android.application") version "8.2.1" apply false

    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}