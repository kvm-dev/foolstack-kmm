plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqldelight)
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
            baseName = "impl"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //api
            api(projects.common.features.authorization.api)
            //utils
            implementation(projects.common.base.utils)
            //date time
            implementation(libs.kotlinx.datetime)
            //coroutines
            implementation(libs.kotlinx.coroutines.core)
            //network
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(projects.common.base.network)
            //lifecycle
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            //storage
            implementation(libs.sqldelight.runtime)
            implementation(projects.common.base.storage)
            //di
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            //network
            implementation(libs.ktor.client.android)
            //storage
            implementation(libs.sqldelight.android.driver)
            //work manager
            implementation(libs.work.manager)
        }
        iosMain.dependencies {
            //network
            implementation(libs.ktor.client.darwin)
            //storage
            implementation(libs.sqldelight.native.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "ru.foolstack.authorization.impl"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}