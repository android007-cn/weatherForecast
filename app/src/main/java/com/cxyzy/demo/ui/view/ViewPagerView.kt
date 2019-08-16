package com.cxyzy.demo.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.ui.adapter.DailyWeatherAdapter
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import kotlinx.android.synthetic.main.view_pager_view.view.*

class ViewPagerView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_pager_view, this, true)
    }

    fun initViews(activity: AppCompatActivity, viewModel: DailyWeatherViewModel) {
        viewPager.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val inflater = LayoutInflater.from(activity)
                val rootView = inflater.inflate(R.layout.vp_content, container, false) as ViewGroup

                val recyclerView = rootView.findViewById<RecyclerView>(R.id.rv)
                val adapter = DailyWeatherAdapter(context)
                recyclerView.adapter = adapter
                viewModel.getWeather(location = viewModel.getLocationList()[position],
                        tryBlock = {},
                        catchBlock = {},
                        finallyBlock = {
                            viewModel.locationMap[viewModel.getLocationList()[position]]?.observe(activity, Observer {
                                setData(it, adapter)
                            })
                        })
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
                return viewModel.getLocationList()[position]
            }

            override fun getCount(): Int {
                return viewModel.getLocationList().size
            }

            override fun isViewFromObject(view: View, obj: Any): Boolean {
                return view === obj
            }
        }
    }

    fun setData(dataList: List<DailyWeatherResp.Data>, adapter: DailyWeatherAdapter) {
        adapter.dataList.clear()
        adapter.dataList.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

    fun getViewPager() = viewPager

}