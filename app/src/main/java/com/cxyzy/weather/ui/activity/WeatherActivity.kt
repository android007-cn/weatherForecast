package com.cxyzy.weather.ui.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import com.cxyzy.weather.R
import com.cxyzy.weather.network.response.DailyWeatherResp
import com.cxyzy.weather.ui.LoadIndicator
import com.cxyzy.weather.utils.CURRENT_LOCATION
import com.cxyzy.weather.viewmodels.WeatherViewModel
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.getViewModel

class WeatherActivity : BaseActivity<WeatherViewModel>(), LoadIndicator {


    override fun viewModel(): WeatherViewModel = getViewModel()

    override fun layoutId(): Int = R.layout.activity_weather

    override fun initData() {
        viewModel().initLocations(listOf(CURRENT_LOCATION, "赤峰", "天津"))
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_cities -> true
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading(isSuccess: Boolean) {
        progressBar.visibility = GONE
    }

}
