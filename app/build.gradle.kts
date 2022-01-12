import Versions.kotlin_version

plugins {
    id ("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-kapt")

}

android {
    compileSdk = Apps.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Apps.pacakageName
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(KotlinDependencies.kotlin)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.coreKtx)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(AndroidXDependencies.constraintLayout)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")


//
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")


// DataStore
    implementation(AndroidXDependencies.dataStore)
    implementation(AndroidXDependencies.dataStoreCore)


// Android KTX
    implementation(AndroidXDependencies.fragmentKtx)
    implementation(AndroidXDependencies.activityKtx)
    implementation(AndroidXDependencies.viewModelKtx)
    implementation(AndroidXDependencies.liveDataKtx)

// Glide
    implementation(ThirdPartyDependencies.glide)
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    kapt(KaptDependencies.glideCompiler)
    implementation("com.github.bumptech.glide:okhttp3-integration:4.11.0")

// Navigation
    implementation(AndroidXDependencies.navigation)
    implementation(AndroidXDependencies.navigationFragment)

// Gson
    implementation(ThirdPartyDependencies.gson)

// Okhttp
    implementation(platform(ThirdPartyDependencies.okhttpBOM))
    implementation(ThirdPartyDependencies.okhttp)
    implementation(ThirdPartyDependencies.okhttpInterceptor)

// Retrofit
    implementation(ThirdPartyDependencies.retrofit)
    implementation(ThirdPartyDependencies.retrofitGsonConverter)
    implementation("com.squareup.retrofit2:retrofit-converters:2.4.0")

// Androidx Security
    implementation(AndroidXDependencies.security)
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)

//coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

//CardView
    implementation(AndroidXDependencies.cardview)

//recyclerview
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

//annotation
    implementation("org.jetbrains:annotations:15.0")
    implementation("androidx.annotation:annotation:1.3.0")

//koin
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-android-compat:3.1.2")
    testImplementation("io.insert-koin:koin-test:3.1.2")


    //bottomsheet
    implementation("com.google.android.material:material:1.4.0")


}