package com.cxyzy.weather.viewmodels

import android.app.Activity
import com.cxyzy.weather.ext.KoinInject.getFromKoin
import com.cxyzy.weather.network.HttpRepository
import com.cxyzy.weather.network.response.DailyWeatherResp
import com.cxyzy.weather.network.response.RealTimeWeatherResp
import com.cxyzy.weather.ui.LoadIndicator
import com.cxyzy.weather.utils.CURRENT_LOCATION
import com.cxyzy.weather.utils.LocateUtil
import com.cxyzy.utils.error

class WeatherViewModel : BaseViewModel() {
    private val httpRepository = getFromKoin<HttpRepository>()
    private var cachedLocationWeatherList = ArrayList<LocationWeather>()

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
            { onFinal() }
        )
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
            { }
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
            { finallyBlock() }
        )
    }
}