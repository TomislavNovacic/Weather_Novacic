package com.tomislav.novacic.weathernovacic.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tomislav.novacic.weathernovacic.R
import com.tomislav.novacic.weathernovacic.data.model.List
import com.tomislav.novacic.weathernovacic.utils.Utils
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class WeatherAdapter(private val context: Context, val data: ArrayList<List>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTime: TextView = itemView.findViewById(R.id.time)
        val mWeatherImg: ImageView = itemView.findViewById(R.id.weatherImg)
        val mTemp: TextView = itemView.findViewById(R.id.temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false))
    }

    override fun onBindViewHolder(viewHolder: WeatherViewHolder, position: Int) {
        val instant = Instant.ofEpochSecond(data[position].dt.toLong())
        val dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = dateTime.format(formatter)
        viewHolder.mTime.text = time
        Glide.with(context).load(String.format(context.getString(R.string.weather_icon_url), data[position].weather[0].icon)).into(viewHolder.mWeatherImg)
        viewHolder.mTemp.text = Utils.formatTemperature(data[position].main.temp)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}