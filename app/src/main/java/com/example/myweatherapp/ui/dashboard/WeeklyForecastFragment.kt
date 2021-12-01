package com.example.myweatherapp.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.DailyForecastListAdapter
import com.example.myweatherapp.ForecastRepository
import com.example.myweatherapp.R
import com.example.myweatherapp.TempDisplaySettingManager
import com.example.myweatherapp.model1.ForecastWeatherModel

import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Displays the 7-Day forecast for the current saved location
 */
class WeeklyForecastFragment : Fragment() {
//
//    private val forecastRepository = ForecastRepository()
//    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
//    private lateinit var locationRepository: LocationRepository
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        /*val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)
//        val emptyText = view.findViewById<TextView>(R.id.emptyText)
//        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)*/
//
//        //val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""
//
//        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
//
//        val dailyForecastList = view?.findViewById<RecyclerView>(R.id.recyclerView)
//        dailyForecastList?.layoutManager = LinearLayoutManager(activity)
//
//        /*val dailyForecastList: RecyclerView = view?.findViewById(R.id.recyclerView) ?:
//        dailyForecastList.layoutManager = LinearLayoutManager(requireContext())*/
//        val dailyForecastAdapter = DailyForecastListAdapter(tempDisplaySettingManager) {
//            showForecastDetails(it)
//        }
//        dailyForecastList?.adapter = dailyForecastAdapter
//
//        // Create the observer which updates the UI in response to forecast updates
//        val weeklyForecastObserver = Observer<ForecastWeatherModel> { weeklyForecast ->
//            /*emptyText.visibility = View.GONE
//            progressBar.visibility = View.GONE*/
//
//            // update our list adapter
//            dailyForecastAdapter.submitList(weeklyForecast.daily.subList(0).)
//        }
//        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)
//
//        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
//        locationEntryButton.setOnClickListener {
//            showLocationEntry()
//        }
//
//        locationRepository = LocationRepository(requireContext())
//        val savedLocationObserver = Observer<Location> { savedLocation ->
//            when (savedLocation) {
//                is Location.Zipcode -> {
//                    progressBar.visibility = View.VISIBLE
//                    forecastRepository.loadWeeklyForecast(savedLocation.zipcode)
//                }
//            }
//        }
//        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)
//
//        return view
//    }
//
//    private fun showLocationEntry() {
//        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
//        findNavController().navigate(action)
//    }
//
//    private fun showForecastDetails(forecast: ForecastWeatherModel) {
//        val temp = forecast.temp.max
//        val description = forecast.weather[0].description
//        val date = forecast.date
//        val icon = forecast.weather[0].icon
//        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(temp, description, date, icon)
//        findNavController().navigate(action)
//    }
//
//    companion object {
//        const val KEY_ZIPCODE = "key_zipcode"
//
//        fun newInstance(zipcode: String) : WeeklyForecastFragment {
//            val fragment = WeeklyForecastFragment()
//
//            val args = Bundle()
//            args.putString(KEY_ZIPCODE, zipcode)
//            fragment.arguments = args
//
//            return fragment
//        }
//    }
}
