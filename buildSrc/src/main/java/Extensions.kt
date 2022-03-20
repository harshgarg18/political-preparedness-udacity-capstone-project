import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec


fun DependencyHandler.implementation(list: List<String>) {
    list.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach {
        add("kapt", it)
    }
}

fun PluginDependenciesSpec.id(list: List<String>) {
    list.forEach {
        id(it)
    }
}

fun PluginDependenciesSpec.kotlin(list: List<String>) {
    list.forEach {
        kotlin(it)
    }
}
