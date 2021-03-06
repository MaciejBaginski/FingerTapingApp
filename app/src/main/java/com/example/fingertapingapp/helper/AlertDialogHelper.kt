package com.example.fingertapingapp.helper

import android.app.AlertDialog
import android.content.Context
import com.example.fingertapingapp.validator.ValidationException

class AlertDialogHelper {
    companion object {
        fun showAlertDialog(context: Context, validationException: ValidationException) {
            showAlertDialog(context, validationException.message.toString(), "Validation error")
        }

        fun showAlertDialog(context: Context, content: String, title: String) {
            val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle(title)
            alertDialog.setMessage(content)
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }
    }
}