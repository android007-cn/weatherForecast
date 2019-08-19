package com.cxyzy.demo.ui.rvAdapter

import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.cxyzy.demo.R
import com.cxyzy.demo.utils.*
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import com.cxyzy.utils.LocationUtils
import com.cxyzy.utils.ext.toast

class CurrentLocationDailyWeatherAdapter(activity: AppCompatActivity, viewModel: DailyWeatherViewModel, locationId: String) : BaseDailyWeatherAdapter(activity, viewModel, locationId) {
    private fun locateAndFetchWeatherRequirePermission() {
        activity.runWithPermissions(Permission.ACCESS_FINE_LOCATION) {
            if (LocationUtils.isLocationProviderEnabled(activity)) {
//                if (needProgressBar) {
//                    progressBar.visibility = View.VISIBLE
//                }
                val currentLocationName = SpUtil.getSp(SpConst.CURRENT_LOCATION_NAME)
                if (!TextUtils.isEmpty(currentLocationName)) {
                    locationId = currentLocationName!!
                    superQueryWeather()
                } else {
                    locateAndFetchWeather()
                }
            } else {
                Utils.showAlert(activity.getString(R.string.need_open_location_switch), activity)
            }
        }
    }

    override fun queryWeather() {
        locateAndFetchWeatherRequirePermission()
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
        super.queryWeather()
    }

}