package com.cxyzy.demo.utils

import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AlertDialog
import com.amap.api.location.AMapLocation
import java.text.SimpleDateFormat
import java.util.*

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