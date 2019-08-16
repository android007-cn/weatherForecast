package com.cxyzy.demo.ui.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.getViewModel

class DailyWeatherActivity : BaseActivity<DailyWeatherViewModel>() {
    override fun viewModel(): DailyWeatherViewModel = getViewModel()

    override fun layoutId(): Int = R.layout.activity_weather

    override fun initData() {
        viewModel().initLocations(listOf("北京", "南京","赤峰","天津"))
    }

    override fun initViews() {
        initViewPagerView()
        initSwipeRefreshLayout()
    }

    override fun providerToolBar(): Toolbar? = toolbar

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
//            locateAndFetchWeatherRequirePermission(false)
        }
    }

    private fun initViewPagerView() {
        viewPagerView.initViews(this,viewModel())
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

}
