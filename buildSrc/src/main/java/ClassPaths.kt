object ClassPaths {
    private const val gradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
    private const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    private const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinVersion}"
    private const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"

    val scriptClassPaths = arrayListOf<String>().apply {
        add(gradle)
        add(kotlin)
        add(serialization)
        add(navigationSafeArgs)
    }
}
