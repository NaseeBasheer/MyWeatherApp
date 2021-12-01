package com.example.myweatherapp

import android.content.Context
import android.security.keystore.KeyNotYetValidException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val CITYNAME = "cityname"

sealed class Location {
    data class Cityname(val cityname: String) : Location()
}

class LocationRepository(context: Context) {

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _savedLocation: MutableLiveData<Location> = MutableLiveData()
    val savedLocation: LiveData<Location> = _savedLocation

    init {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if(key != CITYNAME) return@registerOnSharedPreferenceChangeListener
            broadcastSavedCityname()
        }

        broadcastSavedCityname()
    }

    fun saveLocation(location: Location) {
        when (location) {
            is Location.Cityname -> preferences.edit().putString(CITYNAME, location.cityname).apply()
        }
    }

    private fun broadcastSavedCityname() {
        val zipcode = preferences.getString(CITYNAME, "")
        if (zipcode != null && zipcode.isNotBlank()) {
            _savedLocation.value = Location.Cityname(zipcode)
        }
    }
}