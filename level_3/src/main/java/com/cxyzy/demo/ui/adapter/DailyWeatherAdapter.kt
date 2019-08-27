package com.cxyzy.demo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.utils.WeatherTypes.CLOUDY
import com.cxyzy.demo.utils.WeatherTypes.RAINY
import com.cxyzy.demo.utils.WeatherTypes.SUNNY
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

class DailyWeatherAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClick: (resp: DailyWeatherResp.Data) -> Unit
    var dataList = mutableListOf<DailyWeatherResp.Data>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
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

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return ViewHolder(view)
    }

    infix fun setOnItemClick(onClick: (resp: DailyWeatherResp.Data) -> Unit) {
        this.onItemClick = onClick
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}