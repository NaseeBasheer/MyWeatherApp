package com.example.myweatherapp.service

//https://api.openweathermap.org/data/2.5/weather?q=Paris&appid=dcab4dda113853dfe17f72970307a72e


//https://api.openweathermap.org/data/2.5/onecall?lat=47.64&lon=-122.36&exclude=current,minutely,hourly&units=imperial&appid=04a42b96398abc8e4183798ed22f9485

import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.model1.ForecastWeatherModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?&units=metric&APPID=04a42b96398abc8e4183798ed22f9485")
    fun getData(
        @Query("q") cityname: String
    ): Single<WeatherModel>


    @GET("/data/2.5/onecall")
    fun sevenDayForecast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ) : Call<ForecastWeatherModel>
}