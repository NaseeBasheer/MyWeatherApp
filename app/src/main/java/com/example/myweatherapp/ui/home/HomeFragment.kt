package com.example.myweatherapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import coil.api.load
import com.bumptech.glide.Glide
import com.example.myweatherapp.*
import com.example.myweatherapp.databinding.FragmentHomeBinding
import retrofit2.Retrofit
import retrofit2.http.GET
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.days

class HomeFragment : Fragment() {
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor


    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //val root: View = binding.root
        val view=  inflater.inflate(R.layout.fragment_home, container, false)
        val prefs: SharedPreferences? =
            requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

            GET = prefs!!


        //GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()




        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val cityname = GET.getString("cityName", "Paris")
        binding.cityName.setText("cityname")

        //binding.cityName.setText(cityname)

        //homeViewmodel.refreshData(cityname)
        cityname?.let { homeViewModel.refreshData(it) }
        getLiveData()
         binding.swipeRefreshLayout.setOnRefreshListener {

            val cityName = GET.getString("cityName", cityname)?.lowercase()
            binding.cityName.setText(cityName)
             homeViewModel.refreshData(cityName!!)
            //HomeViewModel.refreshData(cityName!!)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.goButton.setOnClickListener {
            val cityName = binding.cityName.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            homeViewModel.refreshData(cityName)
            getLiveData()
            //Log.i(TAG, "onCreate: " + cityName)
        }


        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return binding.root
    }
    @SuppressLint("SimpleDateFormat")
    private fun getLiveData() {

        homeViewModel.weather_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {


                val date = Date(data.dt*1000)
                val format: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                format.setTimeZone(TimeZone.getTimeZone("GMT+5:30"))
                val formatted: String = format.format(date)



                binding.date.text = formatted
                binding.feelsLike.text = "Feels like "+data.main.feelsLike.toString()+"°C"
                binding.temp.text = data.main.temp.toString()+"°C"
                binding.weather.text = data.weather[0].description
                binding.day.text = data.weather[0].main

                val iconId = data.weather[0].icon
                binding.forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")






            }
        })





     fun getDate() {
            val date = Date(1318386508000L)
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"))
            var formatted: String = format.format(date)
            println(formatted)
            format.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"))
            formatted = format.format(date)
            println(formatted)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}