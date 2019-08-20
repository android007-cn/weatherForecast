package com.cxyzy.demo.viewmodels

import androidx.lifecycle.MutableLiveData
import com.cxyzy.demo.network.HttpRepository
import com.cxyzy.demo.network.response.DailyWeatherResp

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