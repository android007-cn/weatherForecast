package com.cxyzy.demo.ui

interface LoadIndicator {
    fun showLoading()
    fun hideLoading(isSuccess: Boolean)
}