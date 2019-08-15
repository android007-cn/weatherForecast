package com.cxyzy.demo.ui.activity

import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.ui.adapter.DailyWeatherAdapter
import com.cxyzy.demo.utils.AMapLocationUtil
import com.cxyzy.demo.utils.Callback
import com.cxyzy.demo.utils.MyLocation
import com.cxyzy.demo.utils.SpConst.CURRENT_LOCATION_CITY_NAME
import com.cxyzy.demo.utils.SpUtil
import com.cxyzy.demo.utils.Utils.showAlert
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import com.cxyzy.utils.LocationUtils.isLocationProviderEnabled
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.getViewModel

class DailyWeatherActivity : BaseActivity<DailyWeatherViewModel>() {

    private val adapter = DailyWeatherAdapter(this)
    override fun viewModel(): DailyWeatherViewModel = getViewModel()

    override fun layoutId(): Int = R.layout.activity_weather

    override fun initViews() {
        initAdapter()
        initSwipeRefreshLayout()
        locateAndFetchWeatherRequirePermission(true)
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            locateAndFetchWeatherRequirePermission(false)
        }
    }

    private fun initAdapter() {
        rv.adapter = adapter
        adapter.setOnItemClick(this::onItemClick)
    }

    private fun fetchWeather(cityName: String) {
        viewModel().getWeather(cityName = cityName,
                tryBlock = {},
                catchBlock = {
                    toast(it.message.toString())
                },
                finallyBlock = {
                    progressBar.visibility = GONE
                    viewModel().weatherList.observe(this, Observer {
                        adapter.dataList.clear()
                        adapter.dataList.addAll(it)
                        adapter.notifyDataSetChanged()
                    })
                })
    }

    private fun locateAndFetchWeatherRequirePermission(needProgressBar: Boolean) {
        runWithPermissions(Permission.ACCESS_FINE_LOCATION) {
            if (isLocationProviderEnabled(this)) {
                if (needProgressBar) {
                    progressBar.visibility = VISIBLE
                }
                val currentLocationCityName = SpUtil.getSp(CURRENT_LOCATION_CITY_NAME)
                if (!TextUtils.isEmpty(currentLocationCityName)) {
                    fetchWeather(currentLocationCityName!!)
                } else {
                    locateAndFetchWeather()
                }
            } else {
                showAlert(getString(R.string.need_open_location_switch), this)
            }
        }
    }

    private fun locateAndFetchWeather() {
        AMapLocationUtil.startLocation(object : Callback {
            override fun onLocateFailure() {
            }

            override fun onLocateSuccess(location: MyLocation) {
                SpUtil.saveSp(CURRENT_LOCATION_CITY_NAME, location.cityName)
                fetchWeather(location.cityName)
            }
        })
    }

    private fun onItemClick(resp: DailyWeatherResp.Data) {
        viewModel().getWeatherDetail(resp.day,
                {
                    progressBar.visibility = VISIBLE
                },
                {
                    toast(it.message.toString())
                },
                {
                    progressBar.visibility = GONE
                })
    }

}
