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
            baseName = "di"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(projects.common.base.network)
            implementation(projects.common.base.storage)
            implementation(projects.common.base.utils)
            implementation(projects.common.features.splash.impl)
            implementation(projects.common.features.authorization.impl)
            implementation(projects.common.features.registration.impl)
            implementation(projects.common.features.language.impl)
            implementation(projects.common.features.networkconnection.impl)
            implementation(projects.common.features.professions.impl)
            implementation(projects.common.features.profile.impl)
            implementation(projects.common.features.books.impl)
            implementation(projects.common.features.events.impl)
            implementation(projects.common.features.interview.impl)
            implementation(projects.common.features.news.impl)
            implementation(projects.common.features.study.impl)
            implementation(projects.common.features.tests.impl)
            implementation(projects.common.features.main.impl)
        }
        androidMain.dependencies {
            //di
            implementation(libs.koin.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
        }
    }
}

android {
    namespace = "ru.foolstack.di"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}