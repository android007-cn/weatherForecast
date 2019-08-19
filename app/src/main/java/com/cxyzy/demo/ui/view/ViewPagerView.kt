package com.cxyzy.demo.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cxyzy.demo.R
import com.cxyzy.demo.ui.activity.LoadIndicator
import com.cxyzy.demo.ui.rvAdapter.DailyWeatherAdapterFactory
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import kotlinx.android.synthetic.main.view_pager_view.view.*

class ViewPagerView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_pager_view, this, true)
    }

    fun initViews(activity: AppCompatActivity, viewModel: DailyWeatherViewModel, loadIndicator: LoadIndicator) {
        viewPager.offscreenPageLimit = viewModel.getLocationCount()
        viewPager.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val inflater = LayoutInflater.from(activity)
                val rootView = inflater.inflate(R.layout.vp_weather, container, false) as ViewGroup
                val recyclerView = rootView.findViewById<RecyclerView>(R.id.rv)
                val locationId = getLocationId(viewModel, position)

                recyclerView.adapter = DailyWeatherAdapterFactory.getDailyWeatherAdapter(locationId).also {
                    it.activity = activity
                    it.viewModel = viewModel
                    it.loadIndicator = loadIndicator
                    it.queryWeather()
                }
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

    private fun getLocationId(viewModel: DailyWeatherViewModel, position: Int) =
            viewModel.getLocationId(position)

    fun getViewPager(): ViewPager = viewPager

}