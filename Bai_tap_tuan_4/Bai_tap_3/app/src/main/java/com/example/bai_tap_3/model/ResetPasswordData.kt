package com.example.bai_tap_3.model

import android.util.Patterns

data class ResetPasswordData(
    val email: String = "",
    val otp: String = "",
    val password: String = "",
) {
    fun isEmailValid(): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isOtpValid(): Boolean {
        return (otp.length == 5) && otp.all { it.isDigit() }
    }

    fun isPasswordValid(): Boolean {
        return password.length >= 6
    }

    fun getMaskedPassword(): String {
        return "•".repeat(password.length.coerceAtLeast(8))
    }

    fun isComplete(): Boolean {
        return email.isNotBlank() && otp.isNotBlank() && password.isNotBlank()
    }
}
