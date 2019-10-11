package com.cxyzy.weather.viewmodels

import androidx.lifecycle.MutableLiveData
import com.cxyzy.weather.network.HttpRepository
import com.cxyzy.weather.network.response.DailyWeatherResp

class DailyWeatherViewModel : BaseViewModel() {
    var weatherList: MutableLiveData<List<DailyWeatherResp.Data>> = MutableLiveData()

    fun getWeather(onResult: () -> Unit) {
        launchOnUITryCatch(
                {
                    weatherList.value = HttpRepository.getDailyWeather().dataList
                },
                {
                    error(it)
                },
                { onResult() },
                true)

    }
}