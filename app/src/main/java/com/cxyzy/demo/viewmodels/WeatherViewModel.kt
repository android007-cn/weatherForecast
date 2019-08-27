package com.cxyzy.demo.viewmodels

import android.app.Activity
import com.cxyzy.demo.ext.KoinInject.getFromKoin
import com.cxyzy.demo.network.HttpRepository
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.network.response.RealTimeWeatherResp
import com.cxyzy.demo.ui.activity.LoadIndicator
import com.cxyzy.demo.utils.CURRENT_LOCATION
import com.cxyzy.demo.utils.LocateUtil

class WeatherViewModel : BaseViewModel() {
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

    private fun queryDailyWeather(
        locationId: String,
        loadIndicator: LoadIndicator,
        onResult: (weatherList: List<DailyWeatherResp.Data>) -> Unit
    ) {
        launchOnUITryCatch(
            {
                val weather = getCachedLocationWeather(locationId)
                if (weather != null) {
                    weather.weatherList =
                        httpRepository.queryDailyWeather(weather.locationName).dataList
                    onResult(weather.weatherList)
                    loadIndicator.hideLoading(true)
                } else {
                    loadIndicator.hideLoading(false)
                }
            },
            {
                loadIndicator.hideLoading(false)
                error(it)
            },
            { },
            false
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

    private fun getCachedLocationWeather(locationId: String): LocationWeather? {
        for (cachedLocationWeather in cachedLocationWeatherList) {
            if (cachedLocationWeather.id == locationId) {
                return cachedLocationWeather
            }
        }
        return null
    }

    private fun getCachedLocationWeatherName(locationId: String): String? {
        var locationName: String? = null
        val cachedLocationWeather = getCachedLocationWeather(locationId)
        cachedLocationWeather?.let {
            locationName = it.locationName
        }
        return locationName
    }

    fun getLocationList() = cachedLocationWeatherList
    fun getLocationId(index: Int) = cachedLocationWeatherList[index].id
    fun getLocationCount() = cachedLocationWeatherList.size

    private fun updateLocationName(locationId: String, locationName: String) {
        getCachedLocationWeather(locationId)?.locationName = locationName
    }

    fun queryDailyWeather(
        activity: Activity,
        loadIndicator: LoadIndicator,
        locationId: String,
        onResult: (weatherList: List<DailyWeatherResp.Data>) -> Unit
    ) {
        if (locationId == CURRENT_LOCATION) {
            LocateUtil.locateWithPermission(activity) {
                updateLocationName(locationId, it)
                queryDailyWeather(locationId, loadIndicator, onResult)
            }
        } else {
            queryDailyWeather(locationId, loadIndicator, onResult)
        }

    }
}