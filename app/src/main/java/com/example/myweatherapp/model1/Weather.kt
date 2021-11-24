package com.example.myweatherapp.model1


import com.google.gson.annotations.SerializedName

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)