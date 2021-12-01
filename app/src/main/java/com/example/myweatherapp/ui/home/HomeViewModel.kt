package com.example.myweatherapp.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.ForecastRepository
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.model1.ForecastWeatherModel
import com.example.myweatherapp.service.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val weatherApiService = WeatherApiService()
    private val disposable = CompositeDisposable()

    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_loading = MutableLiveData<Boolean>()


    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }


    fun getDataFromAPI(cityName: String) {

        val call = weatherApiService.getDataService(cityName, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<WeatherModel> {
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading current weather", t)
            }

            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                   weather_data.value = weatherResponse!!
                }
            }
        })
    }



    /*private fun getDataFromAPI(cityName: String) {

        weather_loading.value = true
        disposable.add(
            weatherApiService.getDataService(cityName, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    override fun onSuccess(t: WeatherModel) {
                        weather_data.value = t
                        weather_error.value = false
                        weather_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error.value = true
                        weather_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }*/




    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}