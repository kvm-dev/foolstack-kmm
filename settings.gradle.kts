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
include(":common:base:network")
include(":common:base:notifications")
include(":common:base:utils")
include(":common:base:viewmodel")
include(":common:base:di")
include(":common:base:storage")
include(":common:base:ui")
include(":common:base:navigation")
include(":common:features:splash:api")
include(":common:features:splash:impl")
include(":common:features:profile:api")
include(":common:features:profile:impl")
include(":common:features:language:api")
include(":common:features:language:impl")
include(":common:features:networkconnection:api")
include(":common:features:networkconnection:impl")
include(":common:features:authorization:impl")
include(":common:features:authorization:api")
include(":common:features:events:api")
include(":common:features:events:impl")
include(":common:features:tests:api")
include(":common:features:tests:impl")
include(":common:features:books:api")
include(":common:features:books:impl")
include(":common:features:study:api")
include(":common:features:study:impl")
include(":common:features:interview:api")
include(":common:features:interview:impl")
include(":common:features:news:api")
include(":common:features:news:impl")
include(":common:features:registration:api")
include(":common:features:registration:impl")
include(":common:features:main:api")
include(":common:features:main:impl")


include(":common:features:professions:api")
include(":common:features:professions:impl")
