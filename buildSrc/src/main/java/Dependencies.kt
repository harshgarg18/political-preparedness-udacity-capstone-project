object Dependencies {
    // Android
    private const val appCompat =  "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    private const val legacySupport =  "androidx.legacy:legacy-support-v4:${Versions.androidXLegacySupport}"
    private const val annotation =  "androidx.annotation:annotation:${Versions.androidXAnnotations}"

    // Kotlin
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    private const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    private const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
//    private const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc"

    // UI
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintVersion}"
    private const val recyclerView =  "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    private const val cardView =  "androidx.cardview:cardview:${Versions.cardVersion}"
    private const val material =  "com.google.android.material:material:${Versions.materialVersion}"

    // ViewModel and LiveData
    private const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycleVersion}"
    private const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    private const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"

    // Navigation
    private const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    private const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    // Core with Ktx
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"

    // Retrofit
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    private const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    private const val retrofitCoroutineAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutineAdapterVersion}"

    // Moshi
    private const val moshi = "com.squareup.moshi:moshi:${Versions.moshiVersion}"
    private const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
    private const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshiVersion}"

    // Logging
    private const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    // Glide
    private const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"

    //Room
    private const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    // Location
    private const val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServicesLocationVersion}"


    val appLibraries = arrayListOf<String>().apply {
        add(appCompat)
        add(legacySupport)
        add(annotation)
        add(kotlinStdLib)
        add(coroutineCore)
        add(coroutineAndroid)
        add(constraintLayout)
        add(recyclerView)
        add(material)
        add(cardView)
        add(lifecycleExtensions)
        add(lifecycleViewModel)
        add(lifecycleLiveData)
        add(navigationFragment)
        add(navigationUI)
        add(coreKtx)
        add(retrofit)
        add(retrofitMoshiConverter)
        add(retrofitCoroutineAdapter)
        add(glide)
        add(moshi)
        add(moshiKotlin)
        add(moshiAdapters)
        add(timber)
        add(room)
        add(roomKtx)
        add(playServicesLocation)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(roomCompiler)
    }

}
