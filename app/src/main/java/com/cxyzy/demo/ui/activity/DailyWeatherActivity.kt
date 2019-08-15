package com.cxyzy.demo.ui.activity

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
        locateAndFetchWeather()
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initAdapter() {
        rv.adapter = adapter
        adapter.setOnItemClick(this::onItemClick)
    }

    private fun fetchWeather(cityName: String) {
        viewModel().getWeather(cityName,
                {
                    progressBar.visibility = VISIBLE
                },
                {
                    toast(it.message.toString())
                },
                {
                    progressBar.visibility = GONE
                    viewModel().weatherList.observe(this, Observer {
                        adapter.dataList.addAll(it)
                        adapter.notifyDataSetChanged()
                    })
                })
    }


    private fun locateAndFetchWeather() {
        runWithPermissions(Permission.ACCESS_FINE_LOCATION) {
            if (isLocationProviderEnabled(this)) {
                progressBar.visibility = VISIBLE
                AMapLocationUtil.startLocation(object : Callback {
                    override fun onLocateFailure() {
                    }

                    override fun onLocateSuccess(location: MyLocation) = fetchWeather(location.cityName)
                })
            } else {
                showAlert("本应用需要获取地理位置，请打开获取位置的开关", this)
            }
        }
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
