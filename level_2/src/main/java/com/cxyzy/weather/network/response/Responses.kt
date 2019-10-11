package com.cxyzy.weather.network.response

import com.google.gson.annotations.SerializedName

data class RepoResp(
        @SerializedName("id")
        val id: String,
        @SerializedName("full_name")
        val fullName: String)


