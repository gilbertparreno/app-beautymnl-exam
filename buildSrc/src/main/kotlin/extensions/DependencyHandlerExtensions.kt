package extensions

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementations(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.kapts(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.testImplementations(dependencies: List<String>) {
    dependencies.forEach {
        add("testImplementation", it)
    }
}

fun DependencyHandler.androidTestImplementations(dependencies: List<String>) {
    dependencies.forEach {
        add("androidTestImplementation", it)
    }
}