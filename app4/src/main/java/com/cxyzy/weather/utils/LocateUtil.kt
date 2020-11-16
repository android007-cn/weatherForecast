package com.cxyzy.weather.utils

import android.app.Activity
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.cxyzy.utils.LocationUtils

object LocateUtil {

    fun locateWithPermission(
        activity: Activity,
        onResult: (locationName: String) -> Unit
    ) {
        activity.runWithPermissions(Permission.ACCESS_FINE_LOCATION) {
            if (LocationUtils.isLocationProviderEnabled(activity)) {
                locate(onResult)
            } else {
                onResult("")
            }
        }
    }

    private fun locate(onResult: (locationName: String) -> Unit) {
        AMapLocationUtil.startLocation(object : Callback {
            override fun onLocateFailure() {
                onResult("")
            }

            override fun onLocateSuccess(location: MyLocation) {
                val locationName = location.cityName.removeSuffix("市")
                onResult(locationName)
            }
        })
    }

}