object Plugins {
    private const val android = "com.android.application"

    private const val kotlinAndroid = "android"
    private const val kapt = "kapt"
    private const val parcelize = "org.jetbrains.kotlin.plugin.parcelize"
    private const val serialization = "org.jetbrains.kotlin.plugin.serialization"

    private const val navigationSafeArgs = "androidx.navigation.safeargs.kotlin"

    val appPlugins = arrayListOf<String>().apply {
        add(android)
        add(parcelize)
        add(serialization)
        add(navigationSafeArgs)
    }

    val kotlinPlugins = arrayListOf<String>().apply {
        add(kotlinAndroid)
        add(kapt)
    }
}
