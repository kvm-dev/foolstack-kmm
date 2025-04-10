package ru.foolstack.splash.api.model

data class LastVersionDomain(
    val lastVersion: String,
    val aboutUpdate: String,
    val isImportant: Boolean,
    val errorMsg: String
)