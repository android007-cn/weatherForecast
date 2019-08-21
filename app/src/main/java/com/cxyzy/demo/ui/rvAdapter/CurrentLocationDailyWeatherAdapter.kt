/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.cxyzy.demo.ui.rvAdapter

import android.text.TextUtils
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.cxyzy.demo.utils.*
import com.cxyzy.utils.LocationUtils
import com.cxyzy.utils.ext.toast

class CurrentLocationDailyWeatherAdapter(locationId: String) : BaseDailyWeatherAdapter(locationId) {

    private fun locateAndFetchWeatherRequirePermission() {
        activity.runWithPermissions(Permission.ACCESS_FINE_LOCATION) {
            if (LocationUtils.isLocationProviderEnabled(activity)) {
                loadIndicator.showLoading()
                locateAndFetchWeather()
            } else {
                superQueryWeather()
            }
        }
    }

    override fun queryWeather() {
        val currentLocationName = SpUtil.getSp(SpConst.CURRENT_LOCATION_NAME)
        if (!TextUtils.isEmpty(currentLocationName)) {
            superQueryWeather()
            viewModel.updateLocationName(locationId, currentLocationName!!)
        } else {
            locateAndFetchWeatherRequirePermission()
        }
    }

    private fun locateAndFetchWeather() {
        AMapLocationUtil.startLocation(object : Callback {
            override fun onLocateFailure() {
                activity.toast("定位失败")
            }

            override fun onLocateSuccess(location: MyLocation) {
                val locationName = location.cityName.removeSuffix("市")
                SpUtil.saveSp(SpConst.CURRENT_LOCATION_NAME, locationName)
                viewModel.updateLocationName(locationId, locationName)
                superQueryWeather()
            }
        })
    }

    private fun superQueryWeather() {
        super@CurrentLocationDailyWeatherAdapter.queryWeather()
    }

}