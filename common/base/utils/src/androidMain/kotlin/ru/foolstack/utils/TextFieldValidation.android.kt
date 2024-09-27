package ru.foolstack.utils

import android.util.Patterns

actual object TextFieldValidation {
    actual fun validateEmail(email: String): Boolean {
        val emailValue = email.trim()
        var isValid = true
       if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            isValid = false
        }
        return isValid
    }
}