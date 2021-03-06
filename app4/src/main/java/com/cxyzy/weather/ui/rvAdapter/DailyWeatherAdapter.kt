package com.cxyzy.weather.ui.rvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cxyzy.weather.R
import com.cxyzy.weather.network.response.DailyWeatherResp
import com.cxyzy.weather.utils.WeatherTypes.CLOUDY
import com.cxyzy.weather.utils.WeatherTypes.RAINY
import com.cxyzy.weather.utils.WeatherTypes.SUNNY
import kotlinx.android.synthetic.main.item_future_forecast.view.*
import kotlinx.android.synthetic.main.item_future_forecast.view.dayNameTv
import kotlinx.android.synthetic.main.item_future_forecast.view.weatherLogoIv
import kotlinx.android.synthetic.main.item_today_forecast.view.*

class DailyWeatherAdapter(private var isToday: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClick: (resp: DailyWeatherResp.Data) -> Unit
    private var mFutureDataList = mutableListOf<DailyWeatherResp.Data>()
    private var mTodayDataList = mutableListOf<DailyWeatherResp.Data.Hour>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isToday) {
            val data = mTodayDataList[position]
            holder.itemView.dayNameTv.text = data.day
            holder.itemView.temperatureTv.text = "${data.tem} "
            holder.itemView.weatherLogoIv.setImageResource(getWeatherLogo(data.wea))
//            holder.itemView.setOnClickListener { onItemClick(data) }
        } else {
            val data = mFutureDataList[position]
            holder.itemView.dayNameTv.text = data.day
            holder.itemView.highLowTemperatureTv.text = "${data.tem1} / ${data.tem2}"
            holder.itemView.weatherLogoIv.setImageResource(getWeatherLogo(data.wea))
            holder.itemView.setOnClickListener { onItemClick(data) }
        }
    }

    private fun getWeatherLogo(weaImg: String): Int {
        return when {
            SUNNY == weaImg -> R.mipmap.sunny
            CLOUDY == weaImg -> R.mipmap.cloudy
            RAINY == weaImg -> R.mipmap.rainy
            else -> R.mipmap.rainy
        }
    }


    fun setData(dataList: List<DailyWeatherResp.Data>) {
        if (dataList.isEmpty()) {
            return
        }
        if (isToday) {
            mTodayDataList.clear()
            mTodayDataList.addAll(dataList[0].hours)
        } else {
            mFutureDataList.clear()
            for (i in 1 until dataList.size) {
                mFutureDataList.add(dataList[i])
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (isToday) {
            mTodayDataList.size
        } else {
            mFutureDataList.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (isToday) {
            R.layout.item_today_forecast
        } else {
            R.layout.item_future_forecast
        }

        val view =
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    infix fun setOnItemClick(onClick: (resp: DailyWeatherResp.Data) -> Unit) {
        this.onItemClick = onClick
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)


}