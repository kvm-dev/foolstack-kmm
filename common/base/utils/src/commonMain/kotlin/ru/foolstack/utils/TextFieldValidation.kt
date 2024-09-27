package ru.foolstack.utils

expect object TextFieldValidation{
    fun validateEmail(email: String):Boolean
}