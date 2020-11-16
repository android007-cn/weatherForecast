package com.cxyzy.weather.ui

interface LoadIndicator {
    fun showLoading()
    fun hideLoading(isSuccess: Boolean)
}