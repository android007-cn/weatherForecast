package com.cxyzy.demo.ui.rvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.ui.activity.LoadIndicator
import com.cxyzy.demo.utils.WeatherTypes.CLOUDY
import com.cxyzy.demo.utils.WeatherTypes.RAINY
import com.cxyzy.demo.utils.WeatherTypes.SUNNY
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

open class BaseDailyWeatherAdapter(var locationId: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var activity: AppCompatActivity
    lateinit var viewModel: DailyWeatherViewModel
    lateinit var loadIndicator: LoadIndicator

    private lateinit var onItemClick: (resp: DailyWeatherResp.Data) -> Unit
    private var mDataList = mutableListOf<DailyWeatherResp.Data>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mDataList[position]
        holder.itemView.dayNameTv.text = data.day
        holder.itemView.highLowTemperatureTv.text = "${data.tem1} / ${data.tem2}"
        holder.itemView.weatherLogoIv.setImageResource(getWeatherLogo(data.weaImg))
        holder.itemView.setOnClickListener { onItemClick(data) }
    }

    private fun getWeatherLogo(weaImg: String): Int {
        return when {
            SUNNY == weaImg -> R.mipmap.sunny
            CLOUDY == weaImg -> R.mipmap.cloudy
            RAINY == weaImg -> R.mipmap.rainy
            else -> R.mipmap.rainy
        }
    }

    open fun queryWeather() {
        viewModel.getWeather(locationId = locationId,
                tryBlock = {},
                catchBlock = {},
                finallyBlock = {
                    loadIndicator.hideLoading(true)
                    viewModel.getCachedLocationWeather(locationId)?.weatherList?.observe(activity, Observer {
                        setData(it)
                    })
                })
    }


    private fun setData(dataList: List<DailyWeatherResp.Data>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return ViewHolder(view)
    }

    infix fun setOnItemClick(onClick: (resp: DailyWeatherResp.Data) -> Unit) {
        this.onItemClick = onClick
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)


}