package ru.foolstack.ui.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.timestampToDateString(): String{
    val date = Instant.fromEpochMilliseconds(this*1000).toLocalDateTime(TimeZone.currentSystemDefault())
    val day = if(date.dayOfMonth<10){"0${date.dayOfMonth}"}else{"${date.dayOfMonth}"}
    val month = if(date.monthNumber<10){"0${date.monthNumber}"}else{"${date.monthNumber}"}
    return "$day.$month.${date.year}"
}