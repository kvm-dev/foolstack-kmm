package ru.foolstack.language.api.model

sealed class LangResultDomain(val lang: String) {
    class Ru : LangResultDomain("RU")
    class Eng : LangResultDomain("ENG")
}