buildscript {
    dependencies {
        classpath(Dependencies.GradlePlugin.navigationSafeArgsGradle)
        classpath(Dependencies.GradlePlugin.hiltAndroid)
    }
}

plugins {
    id(Dependencies.GradlePlugin.application).version(Versions.application).apply(false)
    id(Dependencies.GradlePlugin.androidLibrary).version(Versions.androidLibrary).apply(false)
    id(Dependencies.GradlePlugin.kotlinAndroid).version(Versions.kotlinAndroid).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}