@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "ru.foolstack.android.splash.impl"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        kotlinCompilerExtensionVersion  = "1.5.4"
    }
}

dependencies {
    implementation(libs.androidx.runtime.android)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    //compose
    implementation(libs.compose.foundation)
    implementation(libs.compose.bom)
    implementation(libs.androidx.activity.compose)
    //matetial-design
    implementation(libs.material)
    //ui
    implementation(projects.android.base.ui)
    //coroutines
    implementation(libs.kotlinx.coroutines.android)
    //viewmodel
//    implementation(libs.androidx.lifecycle.runtime.compose)
//    implementation(libs.androidx.lifecycle.viewmodel.compose)
//    implementation(projects.common.features.splash.impl)
    //di
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    //tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.koin.test)
}