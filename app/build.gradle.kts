plugins {
    // Do not change this order, kotlin plugins need to applied before navigationSafeArgs
    kotlin(Plugins.kotlinPlugins)
    id(Plugins.appPlugins)
}

android {
    compileSdk = AppConfig.compileSdkVersion
    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        buildConfigField("String", "API_KEY", AppConfig.CIVIC_API_KEY)
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    buildTypes {
        getByName(AppConfig.buildTypeRelease) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.appLibraries)
    kapt(Dependencies.kaptLibraries)
}
