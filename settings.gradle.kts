enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FoolStack"
include(":androidApp")
include(":shared")
include(":android:features:main:api")
include(":android:features:main:impl")
include(":android:features:authorization:api")
include(":android:features:authorization:impl")
include(":android:features:notifications:api")
include(":android:features:notifications:impl")
include(":android:features:events:api")
include(":android:features:events:impl")
include(":android:features:books:api")
include(":android:features:books:impl")
include(":android:features:study:api")
include(":android:features:study:impl")
include(":android:features:news:api")
include(":android:features:news:impl")
include(":android:features:tests:api")
include(":android:features:tests:impl")
include(":android:features:professions:api")
include(":android:features:professions:impl")
include(":android:features:interview:api")
include(":android:features:interview:impl")
include(":android:features:settings:api")
include(":android:features:settings:impl")
include(":common:base:network")
include(":common:base:notifications")
include(":common:base:utils")
include(":common:base:viewmodel")
include(":common:features:splash:impl")
include(":common:base:di")
include(":common:base:navigation")
include(":common:features:authorization:impl")
include(":common:base:ui")
include(":common:features:main")
include(":common:features:splash:api")
include(":common:features:storage:impl")
include(":common:features:storage:api")
