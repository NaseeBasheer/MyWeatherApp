package com.example.myweatherapp.model1


import com.google.gson.annotations.SerializedName

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Float,
    val min: Double,
    val morn: Double,
    val night: Double
)