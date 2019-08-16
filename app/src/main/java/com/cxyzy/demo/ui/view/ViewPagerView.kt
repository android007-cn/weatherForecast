package com.cxyzy.demo.ui.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.ui.adapter.DailyWeatherAdapter
import kotlinx.android.synthetic.main.view_pager_view.view.*

class ViewPagerView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private val adapter = DailyWeatherAdapter(context)
    private val pageTitles = arrayOf("Page 1", "Page 2", "Page 3", "Page 4", "Page 5")

    init {
        LayoutInflater.from(context).inflate(R.layout.view_pager_view, this, true)
    }

    fun initViews(activity: Activity) {
        viewPager.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val inflater = LayoutInflater.from(activity)
                val rootView = inflater.inflate(R.layout.vp_content, container, false) as ViewGroup

                val recyclerView = rootView.findViewById<RecyclerView>(R.id.rv)
                recyclerView.adapter = adapter
//                textView.text = getPageTitle(position)
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
                return pageTitles[position]
            }

            override fun getCount(): Int {
                return pageTitles.size
            }

            override fun isViewFromObject(view: View, obj: Any): Boolean {
                return view === obj
            }
        }
    }

    fun setData(dataList: List<DailyWeatherResp.Data>) {
        adapter.dataList.clear()
        adapter.dataList.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

    fun getViewPager() = viewPager

}