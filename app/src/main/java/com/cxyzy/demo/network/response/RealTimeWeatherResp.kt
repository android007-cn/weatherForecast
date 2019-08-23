package com.cxyzy.demo.network.response

import com.google.gson.annotations.SerializedName

data class RealTimeWeatherResp(
    @SerializedName("air")
    var air: String,
    @SerializedName("air_level")
    var airLevel: String,
    @SerializedName("air_pm25")
    var airPm25: String,
    @SerializedName("air_tips")
    var airTips: String,
    @SerializedName("alarm")
    var alarm: Alarm,
    @SerializedName("city")
    var city: String,
    @SerializedName("cityEn")
    var cityEn: String,
    @SerializedName("cityid")
    var cityid: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("countryEn")
    var countryEn: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("humidity")
    var humidity: String,
    @SerializedName("pressure")
    var pressure: String,
    @SerializedName("tem")
    var tem: String,
    @SerializedName("tem1")
    var tem1: String,
    @SerializedName("tem2")
    var tem2: String,
    @SerializedName("update_time")
    var updateTime: String,
    @SerializedName("visibility")
    var visibility: String,
    @SerializedName("wea")
    var wea: String,
    @SerializedName("wea_img")
    var weaImg: String,
    @SerializedName("week")
    var week: String,
    @SerializedName("win")
    var win: String,
    @SerializedName("win_meter")
    var winMeter: String,
    @SerializedName("win_speed")
    var winSpeed: String
)