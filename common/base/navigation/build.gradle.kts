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
            baseName = "navigation"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            //navigation
            implementation(libs.navigation)
            //features
            implementation(projects.common.features.splash.impl)
            implementation(projects.common.features.main.impl)
            implementation(projects.common.features.events.impl)
            implementation(projects.common.features.books.impl)
            implementation(projects.common.features.study.impl)
            implementation(projects.common.features.news.impl)
            implementation(projects.common.features.interview.impl)
            implementation(projects.common.features.tests.impl)
            implementation(projects.common.features.professions.impl)
            implementation(projects.common.features.settings.impl)
            implementation(projects.common.features.language.api)
            //di
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            //ui
            implementation(libs.material3.android)
            implementation(projects.common.base.ui)
            //di
            implementation(libs.koin.compose)
        }
        iosMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "ru.foolstack.navigation"
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