package com.cxyzy.demo.ui.rvAdapter

import com.cxyzy.demo.utils.CURRENT_LOCATION

object DailyWeatherAdapterFactory {
    fun getDailyWeatherAdapter(locationId: String): BaseDailyWeatherAdapter {
        return if (CURRENT_LOCATION == locationId) {
            CurrentLocationDailyWeatherAdapter(locationId)
        } else {
            BaseDailyWeatherAdapter(locationId)
        }
    }
}