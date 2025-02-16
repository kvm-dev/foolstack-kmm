package ru.foolstack.utils

fun String.clearHtml():String{
    return this
        .replace("<p>", "")
        .replace("</p>", "")
        .replace("&nbsp;", " ")
}