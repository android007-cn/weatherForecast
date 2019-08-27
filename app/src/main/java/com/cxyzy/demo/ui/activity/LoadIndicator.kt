package com.cxyzy.demo.ui.activity

interface LoadIndicator {
    fun showLoading()
    fun hideLoading(isSuccess: Boolean)
}