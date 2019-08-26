package com.cxyzy.demo.ui.rvAdapter

import com.cxyzy.demo.utils.CURRENT_LOCATION

object DailyWeatherAdapterFactory {
    fun getDailyWeatherRvAdapter(locationId: String, isToday: Boolean): BaseDailyWeatherAdapter {
        return if (CURRENT_LOCATION == locationId) {
            CurrentLocationDailyWeatherAdapter(locationId,isToday)
        } else {
            BaseDailyWeatherAdapter(locationId,isToday)
        }
    }
}