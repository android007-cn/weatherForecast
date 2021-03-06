package com.cxyzy.weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cxyzy.weather.R
import com.cxyzy.weather.network.DailyWeatherResp
import kotlinx.android.synthetic.main.item_daily_weather.view.*

class DailyWeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = mutableListOf<DailyWeatherResp.Data>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.itemView.dayNameTv.text = data.day
    }

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_weather, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}

