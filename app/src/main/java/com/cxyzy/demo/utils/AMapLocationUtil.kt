package com.cxyzy.demo.utils

import android.annotation.SuppressLint
import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * 高德地图定位封装工具类
 */
object AMapLocationUtil {
    @SuppressLint("StaticFieldLeak")
    private lateinit var mLocationClient: AMapLocationClient
    private var mLocationOption = AMapLocationClientOption()

    fun init(context: Context) {
        mLocationClient = AMapLocationClient(context)
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption.isOnceLocation = true

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.isOnceLocationLatest = true

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.isNeedAddress = true
        //关闭缓存机制
        mLocationOption.isLocationCacheEnable = false

    }

    fun startLocation(callback: Callback) {
        //声明定位回调监听器
        val locationListener = AMapLocationListener { location ->
            if (null != location) {
                val myLocation = MyLocation(location.longitude, location.latitude, location.city)
                callback.onLocateSuccess(myLocation)
                location.latitude
            } else {
                callback.onLocateFailure()
            }
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(locationListener)

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation()
    }
}

data class MyLocation(
        var longitude: Double,
        var latitude: Double,
        var cityName: String
)

interface Callback {
    fun onLocateSuccess(location: MyLocation)
    fun onLocateFailure()
}