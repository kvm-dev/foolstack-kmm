plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        framework {
            baseName = "ui"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            //navigation lib
            implementation(libs.navigation)
            //network
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)
        }
        androidMain.dependencies {
            //compose
            implementation(libs.compose.foundation)
            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.androidx.activity.compose)
            //material-design
            implementation(libs.material)
            implementation(libs.androidx.material3.android)
            //layouts
            implementation(libs.androidx.foundation.layout.android)
            //animation
            implementation(libs.androidx.animation.core.android)
            //network
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            //network
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "ru.foolstack.ui"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}
