package com.cxyzy.demo.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object SpUtil {

    private lateinit var mContext: Context
    private lateinit var mDefaultSpFile: String

    fun init(applicationContext: Context, defaultSpFile: String) {
        mContext = applicationContext
        mDefaultSpFile = defaultSpFile
    }

    fun saveSp(key: String, value: String) =
        getDefaultSharedPreferences().edit().putString(key, value).apply()

    fun getSp(key: String) = getDefaultSharedPreferences().getString(key, null)

    private fun getDefaultSharedPreferences() =
        mContext.getSharedPreferences(mDefaultSpFile, Context.MODE_PRIVATE)
}
