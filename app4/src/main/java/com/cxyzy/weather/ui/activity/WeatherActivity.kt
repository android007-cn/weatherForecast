package com.cxyzy.weather.ui.activity

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import com.cxyzy.utils.ext.toast
import com.cxyzy.weather.R
import com.cxyzy.weather.network.response.DailyWeatherResp
import com.cxyzy.weather.ui.LoadIndicator
import com.cxyzy.weather.utils.CURRENT_LOCATION
import com.cxyzy.weather.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.getViewModel

class WeatherActivity : BaseActivity<WeatherViewModel>(), LoadIndicator {


    override fun viewModel(): WeatherViewModel = getViewModel()

    override fun layoutId(): Int = R.layout.activity_weather

    override fun initData() {
        viewModel().initLocations(listOf(CURRENT_LOCATION))
    }

    override fun initViews() {
        initViewPagerView()
        initSwipeRefreshLayout()
    }

    override fun providerToolBar(): Toolbar? = toolbar

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            initViewPagerView()
        }
    }

    private fun initViewPagerView() {
        viewPagerView.initViews(this, viewModel(), this)
        dotsIndicator.attachViewPager(viewPagerView.getViewPager())
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

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading(isSuccess: Boolean) {
        progressBar.visibility = GONE
    }

}
