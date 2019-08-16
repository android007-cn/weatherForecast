package com.cxyzy.demo.viewmodels

import androidx.lifecycle.MutableLiveData
import com.cxyzy.demo.ext.KoinInject.getFromKoin
import com.cxyzy.demo.network.HttpRepository
import com.cxyzy.demo.network.response.DailyWeatherResp
import java.util.*

class DailyWeatherViewModel : BaseViewModel() {
    private val httpRepository = getFromKoin<HttpRepository>()
    private lateinit var mLocationList: List<String>
    var locationMap = TreeMap<String, MutableLiveData<List<DailyWeatherResp.Data>>>()
//    var weatherList: MutableLiveData<List<DailyWeatherResp.Data>> = MutableLiveData()

    fun getWeatherDetail(id: String, tryBlock: () -> Unit, catchBlock: (throwable: Throwable) -> Unit, finallyBlock: () -> Unit) {
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

    fun initLocations(locationList: List<String>) {
        mLocationList = locationList
        for (location in locationList) {
            locationMap[location] = MutableLiveData()
        }
    }

    fun getLocationList() = mLocationList

    /**
     * @param tryBlock 主要执行代码块
     * @param catchBlock 异常处理代码块
     * @param finallyBlock 无论是否异常都执行的代码块
     */
    fun getWeather(location: String, tryBlock: () -> Unit, catchBlock: (throwable: Throwable) -> Unit, finallyBlock: () -> Unit) {
        launchOnUITryCatch(
                {
                    tryBlock()
                    locationMap[location]?.value = httpRepository.getDailyWeather(location).dataList
                },
                {
                    catchBlock(it)
                    error(it)
                },
                { finallyBlock() },
                true)

    }
}