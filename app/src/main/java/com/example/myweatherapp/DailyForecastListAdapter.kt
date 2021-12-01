package com.example.myweatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.model1.Daily
import com.example.myweatherapp.model1.ForecastWeatherModel
import com.example.myweatherapp.model1.Temp
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class DailyForecastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
) : RecyclerView.ViewHolder(view) {

    private val tempText = view.findViewById<TextView>(R.id.max_temp)
    private val descriptionText = view.findViewById<TextView>(R.id.climate)
    private val dateText = view.findViewById<TextView>(R.id.titleTextView)
    private val forecastIcon = view.findViewById<ImageView>(R.id.sun)

    fun bind(dailyForecast: Daily) {
        tempText.text = formatTempForDisplay(dailyForecast.temp.max , tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.weather[0].description
        //dateText.text = DATE_FORMAT.format(Date(dailyForecast.daily[0].dt * 1000))
        dateText.text = DATE_FORMAT.format(dailyForecast.dt)

        val iconId = dailyForecast.weather[0].icon
        forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
    }
}

class DailyForecastListAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private val clickHandler: (Daily) -> Unit
) : ListAdapter<Daily, DailyForecastViewHolder>(DIFF_CONFIG) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }

    companion object {
        val DIFF_CONFIG = object: DiffUtil.ItemCallback<Daily>() {
            override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: Daily,
                newItem: Daily
            ): Boolean {
                return oldItem == newItem
            }


        }
    }
}