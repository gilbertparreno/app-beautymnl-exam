object Dependencies {

    object AppPlugins {
        const val application = "com.android.application"
        const val jetbrainsKotlinAndroid = "org.jetbrains.kotlin.android"
        const val navigationSafeargsKotlin = "androidx.navigation.safeargs.kotlin"
        const val hiltAndroid = "dagger.hilt.android.plugin"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinKapt = "kotlin-kapt"
        const val kotlinAndroidExtensions = "kotlin-android-extensions"
    }

    object GradlePlugin {
        const val navigationSafeArgsGradle = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafeArgsGradle}"
        const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

        const val application = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "org.jetbrains.kotlin.android"
    }

    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    private const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"

    // networking
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val okhttp3Interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Interceptor}"

    // image loading
    private const val coil = "io.coil-kt:coil:${Versions.coil}"

    // dependency injection
    private const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    private const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    private const val materialDialog = "com.afollestad.material-dialogs:lifecycle:${Versions.materialDialog}"

    private const val sweetDialog = "com.github.f0ris.sweetalert:library:${Versions.sweetDialog}"

    // unit testing
    private const val testJUnit = "junit:junit:${Versions.testJUnit}"

    // UI testing
    private const val testExt = "androidx.test.ext:junit:${Versions.testExt}"
    private const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appDependencies = listOf(
        coreKtx,
        appCompat,
        material,
        constraintLayout,
        fragment,
        navigation,
        coroutinesCore,
        retrofit,
        retrofitConverterGson,
        okhttp3Interceptor,
        coil,
        hiltAndroid,
        timber,
        materialDialog,
        sweetDialog
    )

    val kaptDependencies = listOf(hiltAndroidCompiler)

    val testDependencies = listOf(testJUnit)

    val androidTestDependencies = listOf(
        testExt,
        espresso
    )
}