plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safe.args)

    // Hilt
    alias(libs.plugins.hilt)
    // Kotlin
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
    // KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.swirl.pocketarcade"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.swirl.pocketarcade"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "WORD_GAME_DB_BASE_URL",
            "\"https://www.wordgamedb.com/api/v2/\""
        )
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // --- AndroidX ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // --- Testing ---
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.core.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Hilt ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // --- Retrofit ---
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx)

    // --- OkHttp ---
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // --- Serialization ---
    implementation(libs.kotlinx.serialization.json)
}