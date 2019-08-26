package com.cxyzy.demo.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.RealTimeWeatherResp
import com.cxyzy.demo.ui.activity.LoadIndicator
import com.cxyzy.demo.ui.rvAdapter.DailyWeatherAdapterFactory
import com.cxyzy.demo.utils.CURRENT_LOCATION
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import kotlinx.android.synthetic.main.view_pager_view.view.*

class ViewPagerView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_pager_view, this, true)
    }

    fun initViews(
        activity: AppCompatActivity,
        viewModel: DailyWeatherViewModel,
        loadIndicator: LoadIndicator
    ) {
        viewPager.offscreenPageLimit = viewModel.getLocationCount()
        viewPager.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val inflater = LayoutInflater.from(activity)
                val rootView = inflater.inflate(R.layout.vp_weather, container, false) as ViewGroup
                queryRealTimeWeather(rootView, viewModel, position)
                initRecyclerView(rootView, viewModel, position, activity, loadIndicator)
                container.addView(rootView)
                return rootView
            }

            override fun getItemPosition(obj: Any): Int {
                return POSITION_NONE
            }

            override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
                container.removeView(obj as View)
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return getLocationId(viewModel, position)
            }

            override fun getCount(): Int {
                return viewModel.getLocationList().size
            }

            override fun isViewFromObject(view: View, obj: Any): Boolean {
                return view === obj
            }
        }
    }


    private fun initRecyclerView(
        rootView: ViewGroup, viewModel: DailyWeatherViewModel, position: Int,
        activity: AppCompatActivity, loadIndicator: LoadIndicator
    ) {
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.rv)
        val locationId = getLocationId(viewModel, position)

        recyclerView.adapter =
            DailyWeatherAdapterFactory.getDailyWeatherRvAdapter(locationId).also {
                it.activity = activity
                it.viewModel = viewModel
                it.loadIndicator = loadIndicator
                it.queryDailyWeather()
            }
    }

    private fun getLocationId(viewModel: DailyWeatherViewModel, position: Int) =
        viewModel.getLocationId(position)

    fun getViewPager(): ViewPager = viewPager

    fun queryRealTimeWeather(rootView: ViewGroup, viewModel: DailyWeatherViewModel, position: Int) {
        val locationId = getLocationId(viewModel, position)
        viewModel.getRealTimeWeather(
            locationId = locationId,
            onSuccess = { initRealTimeWeather(rootView, it, locationId) },
            onFailed = {},
            onFinal = {})
    }

    @SuppressLint("SetTextI18n")
    private fun initRealTimeWeather(
        rootView: ViewGroup,
        resp: RealTimeWeatherResp,
        locationId: String
    ) {
        rootView.findViewById<View>(R.id.realTimeWeatherLayout).visibility = View.VISIBLE
        rootView.findViewById<TextView>(R.id.realTimeTemperatureTv).text = resp.tem
        rootView.findViewById<TextView>(R.id.realTimeHighLowTemperatureTv).text =
            "${resp.tem1}℃ / ${resp.tem2}℃"
        rootView.findViewById<TextView>(R.id.weatherDescTv).text = resp.wea
        rootView.findViewById<TextView>(R.id.locationNameTv).text = resp.city
        rootView.findViewById<TextView>(R.id.realTimeTemperatureTv).text = resp.tem
        rootView.findViewById<TextView>(R.id.realTimeTemperatureTv).text = resp.tem
        rootView.findViewById<TextView>(R.id.lastUpdateTimeTv).text =
            context.getString(R.string.last_update_time, resp.updateTime)
        if (locationId == CURRENT_LOCATION) {
            rootView.findViewById<ImageView>(R.id.currentLocationIv).visibility = View.VISIBLE
        }
    }
}