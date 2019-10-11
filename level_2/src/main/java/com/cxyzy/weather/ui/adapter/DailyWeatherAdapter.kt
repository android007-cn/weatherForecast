package com.cxyzy.weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cxyzy.weather.R
import com.cxyzy.weather.network.response.DailyWeatherResp
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

class DailyWeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClick: (resp: DailyWeatherResp.Data) -> Unit
    var dataList = mutableListOf<DailyWeatherResp.Data>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.itemView.dayNameTv.text = data.day
        holder.itemView.setOnClickListener { onItemClick(data) }
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