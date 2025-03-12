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
    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
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
            implementation(projects.common.features.asmode.api)
            //utils
            implementation(projects.common.base.utils)
            //date time
            implementation(libs.kotlinx.datetime)
            //ui
            implementation(projects.common.base.ui)
            //language
            implementation(projects.common.features.language.api)
            //view model
            implementation(projects.common.base.viewmodel)
            //lifecycle
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            //network
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(projects.common.base.network)
            //storage
            implementation(libs.sqldelight.runtime)
            implementation(projects.common.base.storage)
            //network connection
            implementation(projects.common.features.networkconnection.api)
            //profile
            implementation(projects.common.features.profile.api)
            //di
            implementation(libs.koin.core)
            //navigation
            implementation(libs.navigation)
        }
        androidMain.dependencies {
            //work manager
            implementation(libs.work.manager)
            //utils
            implementation(projects.common.base.utils)
            //viewmodel
            implementation(libs.androidx.lifecycle.runtime.compose)
            //lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            //network
            implementation(libs.ktor.client.okhttp)
            //storage
            implementation(libs.sqldelight.android.driver)
            //di
            implementation(libs.koin.compose)
            //ui
            implementation(projects.common.base.ui)
            implementation(libs.material3.android)
            implementation(libs.compose.material3)
            implementation(libs.androidx.material3.android)
            //compose
            implementation(libs.compose.foundation)
            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.androidx.activity.compose)
            //material-design
            implementation(libs.material)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            //storage
            implementation(libs.sqldelight.native.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
        }
    }
}

android {
    namespace = "ru.foolstack.asmode.impl"
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
