package ru.foolstack.splash.api.model

sealed class LangResultDomain(val lang: String) {
    class Ru : LangResultDomain("RU")
    class Eng : LangResultDomain("ENG")
}