
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
            baseName = "impl"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //api
            api(projects.common.features.authorized.api)
            //utils
            implementation(projects.common.base.utils)
            //language
            implementation(projects.common.features.language.api)
            //view model
            implementation(projects.common.base.viewmodel)
            //lifecycle
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            //network
            implementation(libs.ktor.client.core)
            //network connection
            implementation(projects.common.features.networkconnection.api)
            //di
            implementation(libs.koin.core)
            //navigation
            implementation(libs.navigation)
        }
        androidMain.dependencies {
            //viewmodel
            implementation(libs.androidx.lifecycle.runtime.compose)
            //lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            //network
            implementation(libs.ktor.client.okhttp)
            //di
            implementation(libs.koin.compose)
            //ui
            implementation(projects.common.base.ui)
            //compose
            implementation(libs.compose.foundation)
            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.androidx.activity.compose)
            //material-design
            implementation(libs.material)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
        }
    }
}

android {
    namespace = "ru.foolstack.authorized.impl"
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
dependencies {
    implementation(libs.material3.android)
}