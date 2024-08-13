package ru.foolstack.notifications.model

interface Message {

    class String(
        val message: kotlin.String
    ) : Message

    class IntRes(
        val message: kotlin.String?
    ) : Message

}

sealed class SnackBarData {

    class Error(val info: Message?) : SnackBarData()

    class Push(
        val title: Message?,
        val description: Message?,
        val action: () -> Unit
    ) : SnackBarData()

}