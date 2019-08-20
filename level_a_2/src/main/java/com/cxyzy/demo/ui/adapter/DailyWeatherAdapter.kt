package com.cxyzy.demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cxyzy.demo.R
import com.cxyzy.demo.network.DailyWeatherResp
import kotlinx.android.synthetic.main.item_daily_weather.view.*

class DailyWeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClick: (resp: DailyWeatherResp.Data) -> Unit
    var dataList = mutableListOf<DailyWeatherResp.Data>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.itemView.dayNameTv.text = data.day
        holder.itemView.setOnClickListener { onItemClick(data) }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_weather, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}

