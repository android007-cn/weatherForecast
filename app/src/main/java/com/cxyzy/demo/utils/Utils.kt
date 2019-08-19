package com.cxyzy.demo.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object Utils {

    fun showAlert(message: String, context: Context) {
        val alertDialog = AlertDialog.Builder(context).setMessage(message).setCancelable(false)
                .setPositiveButton(android.R.string.ok)
                { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .create()
        alertDialog.show()
    }
}