package com.example.myweatherapp.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.ForecastRepository
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.model1.Daily
import com.example.myweatherapp.model1.ForecastWeatherModel
import com.example.myweatherapp.service.WeatherApiService
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {
    val forecast_weather_data = MutableLiveData<ForecastWeatherModel>()

    //val forecast_weather_data = MutableLiveData<ForecastWeatherModel>()
    val weeklyForecast: LiveData<ForecastWeatherModel> = forecast_weather_data

    private val weatherApiService = WeatherApiService()
    private val disposable = CompositeDisposable()

    val weather_data = MutableLiveData<ForecastWeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_loading = MutableLiveData<Boolean>()

    fun refreshData1(cityName: String) {
       getForecastDataFromAPI(cityName)
   }


    fun getForecastDataFromAPI(cityName: String) {
        val call = weatherApiService.getDataService(cityName, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<WeatherModel> {
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading location for weekly forecast", t)
            }

            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    val forecastCall = weatherApiService.getSevenDayForecastService(
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
                                forecast_weather_data.value = weeklyForecastResponse!!
                            }
                        }

                    })
                }
            }
        })

        /*val call = weatherApiService.getSevenDayForecastService(lat, lon, exclude, units, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<ForecastWeatherModel> {
            override fun onFailure(call: Call<ForecastWeatherModel>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading current weather", t)
            }

            override fun onResponse(call: Call<ForecastWeatherModel>, response: Response<ForecastWeatherModel>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    forecast_weather_data.value = weatherResponse!!
                }
            }
        })*/
    }



   /* fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }

    private fun getDataFromAPI(lat: Float, lon: Float, exclude: String, units: String, apiKey: String) {

        weather_loading.value = true
        disposable.add(
            weatherApiService.getSevenDayForecastService(lat, lon, exclude, units, apiKey )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ForecastWeatherModel>() {

                    override fun onSuccess(t: ForecastWeatherModel) {
                        weather_data.value = t
                        weather_error.value = false
                        weather_loading.value = false
                        Log.d(ContentValues.TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error.value = true
                        weather_loading.value = false
                        Log.e(ContentValues.TAG, "onError: " + e)
                    }

                })
        )

    }
*/




    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}