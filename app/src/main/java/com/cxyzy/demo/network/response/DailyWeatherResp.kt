package com.cxyzy.demo.network.response

import com.google.gson.annotations.SerializedName


data class DailyWeatherResp(
    @SerializedName("data")
    var dataList: List<Data>,
    @SerializedName("city")
    var city: String,
    @SerializedName("cityEn")
    var cityEn: String,
    @SerializedName("cityid")
    var cityId: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("countryEn")
    var countryEn: String,
    @SerializedName("update_time")
    var updateTime: String
) {
    data class Data(
        @SerializedName("air")
        var air: Int,
        @SerializedName("air_level")
        var airLevel: String,
        @SerializedName("air_tips")
        var airTips: String,
        @SerializedName("alarm")
        var alarm: Alarm,
        @SerializedName("date")
        var date: String,
        @SerializedName("day")
        var day: String,
        @SerializedName("hours")
        var hours: List<Hour>,
        @SerializedName("humidity")
        var humidity: Int,
        @SerializedName("index")
        var index: List<Index>,
        @SerializedName("tem")
        var tem: String,
        @SerializedName("tem1")
        var tem1: String,
        @SerializedName("tem2")
        var tem2: String,
        @SerializedName("wea")
        var wea: String,
        @SerializedName("wea_img")
        var weaImg: String,
        @SerializedName("week")
        var week: String,
        @SerializedName("win")
        var win: List<String>,
        @SerializedName("win_speed")
        var winSpeed: String
    ) {
        data class Hour(
            @SerializedName("day")
            var day: String,
            @SerializedName("tem")
            var tem: String,
            @SerializedName("wea")
            var wea: String,
            @SerializedName("win")
            var win: String,
            @SerializedName("win_speed")
            var winSpeed: String
        )

        data class Index(
            @SerializedName("desc")
            var desc: String,
            @SerializedName("level")
            var level: String?,
            @SerializedName("title")
            var title: String
        )
    }
}

data class Alarm(
    @SerializedName("alarm_content")
    var alarmContent: String,
    @SerializedName("alarm_level")
    var alarmLevel: String,
    @SerializedName("alarm_type")
    var alarmType: String
)