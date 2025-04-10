package ru.foolstack.utils

actual fun getCurrentVersion(): String {
    return BuildConfig.VERSION_NAME
}