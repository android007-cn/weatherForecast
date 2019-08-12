package com.cxyzy.demo

import com.cxyzy.demo.ext.provideOkHttpClient
import com.cxyzy.demo.network.HttpRepository
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideOkHttpClient() }
    single { HttpRepository }
    viewModel { DailyWeatherViewModel() }
}
