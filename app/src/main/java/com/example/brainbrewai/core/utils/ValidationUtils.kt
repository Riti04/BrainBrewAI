package com.example.brainbrewai.core.utils

object ValidationUtils {

    fun isValidPassword(
        password: String
    ): Boolean {

        val passwordRegex =
            Regex(
                "^(?=.*[A-Z])(?=.*[0-9]).{6,}$"
            )

        return passwordRegex.matches(password)
    }

    fun isValidEmail(
        email: String
    ): Boolean {

        return android.util.Patterns
            .EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }
}