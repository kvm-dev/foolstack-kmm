import com.android.manifmerger.ManifestSystemProperty
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "ru.foolstack.foolstack.android"
    compileSdk = 34
    defaultConfig {
        val version: String = (properties.get("VERSION_NAME") as String).replace(""""""", "")
//        val version: String = properties["VERSION_NAME"].toString()
        applicationId = "ru.foolstack.foolstack.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = version
        buildConfigField("String", "VERSION_NAME", properties["VERSION_NAME"].toString())
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        create("releaseDev") {
        }
        create("debugDev") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
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
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    //di
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(projects.common.base.di)
    //navigation
    implementation(libs.navigation)
    implementation(projects.common.base.navigation)
    //viewmodel lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.comppose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(projects.common.base.viewmodel)
    //encryptedPrefs
    implementation(projects.common.base.storage)
    //utils
    implementation(projects.common.base.utils)
    //tests
    debugImplementation(libs.compose.ui.tooling)
}