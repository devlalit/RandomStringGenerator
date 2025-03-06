plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")

}

android {
    namespace = "com.example.randomstringapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.randomstringapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Jetpack Compose
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material:material:1.5.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Hilt for Dependency Injection
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Lifecycle ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Core KTX
    implementation ("androidx.core:core-ktx:1.12.0")

    // Material Design
    implementation ("com.google.android.material:material:1.10.0")

    // Compose UI Tooling (for Preview)
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.0")

    // Hilt Compiler
    kapt("androidx.hilt:hilt-compiler:1.1.0")
}