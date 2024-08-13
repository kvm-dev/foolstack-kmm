package ru.foolstack.impl.model

sealed class LangResult(val lang: String) {
    class Ru : LangResult("RU")
    class Eng : LangResult("ENG")
}