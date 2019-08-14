package com.cxyzy.demo.ui.activity

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.ui.adapter.DailyWeatherAdapter
import com.cxyzy.demo.utils.Utils.showAlert
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import com.cxyzy.utils.LocationUtils.isLocationProviderEnabled
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.getViewModel

class DailyWeatherActivity : BaseActivity<DailyWeatherViewModel>() {
    private lateinit var mLocationClient: AMapLocationClient
    private var mLocationOption = AMapLocationClientOption()

    private val adapter = DailyWeatherAdapter(this)
    override fun viewModel(): DailyWeatherViewModel = getViewModel()

    override fun layoutId(): Int = R.layout.activity_weather

    override fun initViews() {
        initAdapter()

        initSwipeRefreshLayout()

        initAMap()
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

    private fun initAMap() {
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption.isOnceLocation = true

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.isOnceLocationLatest = true

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.isNeedAddress = true
        //关闭缓存机制
        mLocationOption.isLocationCacheEnable = false

        //声明定位回调监听器
        val locationListener = AMapLocationListener { loc ->
            progressBar.visibility = GONE
            if (null != loc) {
                fetchWeather(loc.city)
            } else {
                toast("定位失败")
            }
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(locationListener)
    }

    private fun locateAndFetchWeather() {
        runWithPermissions(Permission.ACCESS_FINE_LOCATION) {
            if (isLocationProviderEnabled(this)) {
                progressBar.visibility = VISIBLE
                //给定位客户端对象设置定位参数
                mLocationClient.setLocationOption(mLocationOption);
                //启动定位
                mLocationClient.startLocation()
            } else {
                showAlert("本应用需要获取地理位置，请打开获取位置的开关", this)
            }
        }
    }


    private fun initLocationAndWeather() {

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

    override fun startObserve() {

    }

}
