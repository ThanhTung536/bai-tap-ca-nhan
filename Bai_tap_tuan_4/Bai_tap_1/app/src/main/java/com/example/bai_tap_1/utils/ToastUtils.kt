package com.example.bai_tap_1.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

fun showTopToast(context: Context, message: String) {
    try {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 160)
        toast.show()
    } catch (_: Throwable) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
