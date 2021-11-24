package com.example.myweatherapp.model1


import com.google.gson.annotations.SerializedName

data class ForecastWeatherModel(
    val alerts: List<Alert>,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)