/*
 * Copyright (c) 2019. test
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

    override fun queryDailyWeather() {
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
        super@CurrentLocationDailyWeatherAdapter.queryDailyWeather()
    }

}