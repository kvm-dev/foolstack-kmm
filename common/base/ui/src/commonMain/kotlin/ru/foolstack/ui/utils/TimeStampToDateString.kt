package ru.foolstack.ui.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.timestampToDateString(): String{
    val date = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    return "${date.dayOfMonth}.${date.monthNumber-1}.${date.year}"
}