package com.cxyzy.demo.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import com.cxyzy.demo.utils.CURRENT_LOCATION
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel

object DailyWeatherAdapterFactory {
    fun getDailyWeatherAdapter(locationId: String, activity: AppCompatActivity, viewModel: DailyWeatherViewModel): BaseDailyWeatherAdapter {
        return if (CURRENT_LOCATION == locationId) {
            CurrentLocationDailyWeatherAdapter(activity, viewModel, locationId)
        } else {
            BaseDailyWeatherAdapter(activity, viewModel, locationId)
        }
    }
}