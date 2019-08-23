package com.cxyzy.demo.viewmodels

import com.cxyzy.demo.ext.KoinInject.getFromKoin
import com.cxyzy.demo.network.HttpRepository
import com.cxyzy.demo.network.response.RealTimeWeatherResp
import com.cxyzy.demo.utils.CURRENT_LOCATION

class DailyWeatherViewModel : BaseViewModel() {
    private val httpRepository = getFromKoin<HttpRepository>()
    private var cachedLocationWeatherList = ArrayList<LocationWeather>()

    fun getWeatherDetail(
        id: String,
        tryBlock: () -> Unit,
        catchBlock: (throwable: Throwable) -> Unit,
        finallyBlock: () -> Unit
    ) {
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
            true
        )
    }

    fun initLocations(locationList: List<String>) {
        for (location in locationList) {
            var locationName = location
            if (location == CURRENT_LOCATION) {
                locationName = ""
            }
            cachedLocationWeatherList.add(
                LocationWeather(
                    id = location,
                    locationName = locationName
                )
            )
        }
    }

    /**
     * @param tryBlock 主要执行代码块
     * @param catchBlock 异常处理代码块
     * @param finallyBlock 无论是否异常都执行的代码块
     */
    fun getWeather(
        locationId: String,
        tryBlock: () -> Unit,
        catchBlock: (throwable: Throwable) -> Unit,
        finallyBlock: () -> Unit
    ) {
        launchOnUITryCatch(
            {
                tryBlock()
                val cachedLocationWeather = getCachedLocationWeather(locationId)
                cachedLocationWeather?.let {
                    it.weatherList?.value = httpRepository.getDailyWeather(it.locationName).dataList
                }
            },
            {
                catchBlock(it)
                error(it)
            },
            { finallyBlock() },
            true
        )

    }

    fun getRealTimeWeather(
        locationId: String,
        onSuccess: (resp: RealTimeWeatherResp) -> Unit,
        onFailed: (throwable: Throwable) -> Unit,
        onFinal: () -> Unit
    ) {
        launchOnUITryCatch(
            {
                val locationName = getCachedLocationWeatherName(locationId)
                locationName?.let {
                    val resp = httpRepository.getRealTimeWeather(it)
                    onSuccess(resp)
                }
            },
            {
                onFailed(it)
                error(it)
            },
            { onFinal() },
            true
        )

    }

    fun getCachedLocationWeather(locationId: String): LocationWeather? {
        for (cachedLocationWeather in cachedLocationWeatherList) {
            if (cachedLocationWeather.id == locationId) {
                return cachedLocationWeather
            }
        }
        return null
    }

    fun getCachedLocationWeatherName(locationId: String): String? {
        var locationName: String? = null
        val cachedLocationWeather = getCachedLocationWeather(locationId)
        cachedLocationWeather?.let {
            locationName = it.locationName
        }
        return locationName
    }

    fun getLocationList() = cachedLocationWeatherList
    fun getLocationId(index: Int) = cachedLocationWeatherList[index].id

    fun updateLocationName(locationId: String, locationName: String) {
        getCachedLocationWeather(locationId)?.locationName = locationName
    }

    fun getLocationCount() = cachedLocationWeatherList.size
}