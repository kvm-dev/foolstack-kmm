package ru.foolstack.ui.utils

fun String.removeHtmlTags():String{
    return this
        .replace("<p>", "")
        .replace("</p>", "")
        .replace("<br>", "")
        .replace("<b>", "")
        .replace("</b>", "")
        .replace("<span>", "")
        .replace("</span>", "")
        .replace("<h1>", "")
        .replace("</h1>", "")
        .replace("<h2>", "")
        .replace("</h2>", "")
        .replace("<h3>", "")
        .replace("</h3>", "")
}