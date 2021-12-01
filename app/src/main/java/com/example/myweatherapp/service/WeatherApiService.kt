package com.example.myweatherapp.service

import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.model1.ForecastWeatherModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiService {

    //http://api.openweathermap.org/data/2.5/weather?q=bingol&APPID=04a42b96398abc8e4183798ed22f9485

    private val BASE_URL = "http://api.openweathermap.org/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    fun getDataService(cityname: String, openWeatherMapApiKey: String): Call<WeatherModel> {
        return api.getData(cityname, openWeatherMapApiKey)
    }

    fun getSevenDayForecastService(lat: Float, lon: Float, exclude: String, units: String, apiKey: String): Call<ForecastWeatherModel> {
        return api.sevenDayForecast(lat, lon, exclude, units, apiKey)
    }
}