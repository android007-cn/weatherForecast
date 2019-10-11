package com.cxyzy.weather.viewmodels

import androidx.lifecycle.MutableLiveData
import com.cxyzy.weather.network.HttpRepository
import com.cxyzy.weather.network.response.DailyWeatherResp

class DailyWeatherViewModel : BaseViewModel() {
    var weatherList: MutableLiveData<List<DailyWeatherResp.Data>> = MutableLiveData()

    fun getRepoDetail(id: String, tryBlock: () -> Unit, catchBlock: (throwable: Throwable) -> Unit, finallyBlock: () -> Unit) {
        launchOnUITryCatch(
                {
                    tryBlock()
                    //TODO: get DailyWeatherResp detail
                },
                {
                    catchBlock(it)
                    error(it)
                },
                { finallyBlock() },
                true)
    }

    /**
     * @param tryBlock 主要执行代码块
     * @param catchBlock 异常处理代码块
     * @param finallyBlock 无论是否异常都执行的代码块
     */
    fun getWeather(tryBlock: () -> Unit, catchBlock: (throwable: Throwable) -> Unit, finallyBlock: () -> Unit) {
        launchOnUITryCatch(
                {
                    tryBlock()
                    weatherList.value = HttpRepository.getDailyWeather().dataList
                },
                {
                    catchBlock(it)
                    error(it)
                },
                { finallyBlock() },
                true)

    }
}