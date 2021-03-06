import extensions.androidTestImplementations
import extensions.implementations
import extensions.kapts
import extensions.testImplementations

plugins {
    id(Dependencies.AppPlugins.application)
    id(Dependencies.AppPlugins.jetbrainsKotlinAndroid)
    id(Dependencies.AppPlugins.navigationSafeargsKotlin)
    id(Dependencies.AppPlugins.kotlinAndroid)
    id(Dependencies.AppPlugins.kotlinKapt)
    id(Dependencies.AppPlugins.hiltAndroid)
    id(Dependencies.AppPlugins.kotlinAndroidExtensions)
}

android {
    compileSdk = Configurations.compileSdk

    defaultConfig {
        applicationId = Configurations.applicationId
        minSdk = Configurations.minSdk
        targetSdk = Configurations.targetSdk
        versionCode = Configurations.versionCode
        versionName = Configurations.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementations(Dependencies.appDependencies)
    kapts(Dependencies.kaptDependencies)

    testImplementations(Dependencies.testDependencies)
    androidTestImplementations(Dependencies.androidTestDependencies)
}

kapt {
    correctErrorTypes = true
}