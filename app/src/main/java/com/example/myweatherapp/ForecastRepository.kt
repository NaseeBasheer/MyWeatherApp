package com.example.myweatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.model1.ForecastWeatherModel
import com.example.myweatherapp.service.WeatherApiService
import com.example.myweatherapp.ui.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastRepository {

    private val weatherService = WeatherApiService()
    private lateinit var homeViewModel: HomeViewModel

    private val _currentWeather = MutableLiveData<WeatherModel>()
    val getData: LiveData<WeatherModel> = _currentWeather
    //val currentWeather: LiveData<WeatherModel> = _currentWeather

    private val _weeklyForecast = MutableLiveData<ForecastWeatherModel>()
    val weeklyForecast: LiveData<ForecastWeatherModel> = _weeklyForecast

    fun loadCurrentForecast(cityname: String) {

        val call = weatherService.getDataService(cityname, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<WeatherModel> {
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading current weather", t)
            }

            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    _currentWeather.value = weatherResponse!!
                }
            }
        })
    }

    fun loadWeeklyForecast(cityname: String) {
        val call = weatherService.getDataService(cityname, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<WeatherModel> {
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading location for weekly forecast", t)
            }

            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    val forecastCall = weatherService.getSevenDayForecastService(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )

                    forecastCall.enqueue(object : Callback<ForecastWeatherModel> {
                        override fun onFailure(call: Call<ForecastWeatherModel>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName, "error loading weekly forecast", t)
                        }

                        override fun onResponse(call: Call<ForecastWeatherModel>, response: Response<ForecastWeatherModel>) {
                            val weeklyForecastResponse = response.body()
                            if (weeklyForecastResponse != null) {
                                _weeklyForecast.value = weeklyForecastResponse!!
                            }
                        }

                    })
                }
            }
        })

    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder than I would prefer"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That's the sweet spot!"
            in 80f.rangeTo(90f) -> "Getting a little warm"
            in 90f.rangeTo(100f) -> "Where's the A/C?"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What is this, Arizona?"
            else -> "Does not compute"
        }
    }
}