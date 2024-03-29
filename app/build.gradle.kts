import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
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

        buildConfigField("String", "API_KEY", getApiKey("PROD_API_KEY"))
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            manifestPlaceholders["appName"] = "@string/app_name_dev"
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            manifestPlaceholders["appName"] = "@string/app_name"
            isMinifyEnabled = false

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
fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.coreKtx)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(AndroidXDependencies.constraintLayout)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // debugImplementation("com.squareup.leakcanary:leakcanary-android:2.6")
    //Hilt
    implementation("com.google.dagger:hilt-android:2.37")
    kapt("com.google.dagger:hilt-android-compiler:2.37")

// ViewModel
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.5.0-alpha06")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha06")
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
    implementation("com.android.support:support-v4:28.0.0")
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



    //bottomsheet
    implementation("com.google.android.material:material:1.4.0")

    //google
    implementation(platform("com.google.firebase:firebase-bom:29.1.0"))     // Firebase BoM
    implementation("com.google.firebase:firebase-common-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation("androidx.work:work-runtime:2.8.0-alpha01")


    //dot indicator
    implementation("com.tbuonomo:dotsindicator:4.2")

    //Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //update
    implementation("com.google.android.play:core:1.10.3")

    //flex-box layout
    implementation("com.google.android.flexbox:flexbox:3.0.0")

}