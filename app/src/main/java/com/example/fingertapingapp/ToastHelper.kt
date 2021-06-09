package com.example.fingertapingapp

import android.content.Context
import android.widget.Toast

class ToastHelper {
    companion object {
        fun showLongToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun showShortToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}