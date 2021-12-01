package com.example.myweatherapp.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorapp.ui.DataModel
import com.example.calculatorapp.ui.RecyclerViewAdapter
import com.example.myweatherapp.*
import com.example.myweatherapp.databinding.FragmentDashboardBinding
import com.example.myweatherapp.databinding.FragmentHomeBinding
import com.example.myweatherapp.model1.Daily
import com.example.myweatherapp.model1.ForecastWeatherModel
import com.example.myweatherapp.ui.home.HomeFragment
import com.example.myweatherapp.ui.home.HomeViewModel
import com.example.myweatherapp.ui.notifications.NotificationsFragment

class DashboardFragment : Fragment()
    //, RecyclerViewAdapter.ClickListener

{

    private lateinit var GET: SharedPreferences
    //private lateinit var adapter: RecyclerViewAdapter
    private  lateinit var adapter: DailyForecastListAdapter
    //val listData: ListAdapter<Daily, DailyForecastViewHolder> = ListAdapter
    val listData:ArrayList<DataModel> = ArrayList()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var locationRepository: LocationRepository



    private lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val cityname = arguments?.getString(CITYNAME)?: ""



        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        recyclerView.adapter = DailyForecastListAdapter(tempDisplaySettingManager){
            showForecastDetails()
        }
       /* val dailyForecastAdapter = DailyForecastListAdapter(tempDisplaySettingManager){
            showForecastDetails()
        }
         recyclerView.adapter = adapter
         recyclerView.adapter = dailyForecastAdapter*/

        val weeklyForecastObserver = Observer<ForecastWeatherModel> { weeklyForecast ->


            // update our list adapter
            //dailyForecastAdapter.submitList(weeklyForecast.daily)
            adapter.submitList(weeklyForecast.daily)
        }
        viewModel.forecast_weather_data.observe(viewLifecycleOwner, weeklyForecastObserver)





        //buildDisplayedData()
        //initRecyclerView(view)


        /*val dailyForecastAdapter = DailyForecastListAdapter(tempDisplaySettingManager) {

            showForecastDetails()

        }
        recyclerView.adapter = dailyForecastAdapter*/
        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when (savedLocation) {
                is Location.Cityname -> {
                    viewModel.refreshData1(savedLocation.cityname)
//                    progressBar.visibility = View.VISIBLE
//                    forecastRepository.loadWeeklyForecast(savedLocation.zipcode)
                }
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)



        viewModel.text.observe(viewLifecycleOwner, Observer {
        })

        return binding.root


        /*val recyclerView = view as RecyclerView
        recyclerView.findViewById<View>(R.id.recyclerView)
        val llm = LinearLayoutManager(activity)
        recyclerView.setLayoutManager(llm)*/
    }
    private fun initRecyclerView(view: View){
        //val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        //recyclerView.layoutManager = LinearLayoutManager(activity)




        //recyclerView.layoutManager = LinearLayoutManager(activity)



        //recyclerView.adapter = adapter



        /*tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        val dailyForecastAdapter = DailyForecastListAdapter(tempDisplaySettingManager) {

        }
        recyclerView.adapter = dailyForecastAdapter*/


        /*adapter = RecyclerViewAdapter(listData, this)
        recyclerView.adapter = adapter*/
    }
    private fun buildDisplayedData(){
        listData.add(DataModel("Aluv, un"))
        listData.add(DataModel("Fort Kochi, IN"))
        listData.add(DataModel("Thoppumpady, IN"))
        listData.add(DataModel("Mattanchery, IN"))
        listData.add(DataModel("Bangalore, IN"))
        listData.add(DataModel("Perumbavoor, IN"))
        listData.add(DataModel("Preston, UK"))
        listData.add(DataModel("New York, US"))
        listData.add(DataModel("Chicago, US"))
        listData.add(DataModel("Paris, FR"))
        listData.add(DataModel("New Delhi, IN"))
        listData.add(DataModel("Los Angeles, US"))
        listData.add(DataModel("Milan, IT"))
        listData.add(DataModel("Tokyo, JP"))
        listData.add(DataModel("Beijing, CN"))
        listData.add(DataModel("Mumbai, IN"))
        listData.add(DataModel("Manila, PH"))
        listData.add(DataModel("Barcelona, ES"))
        listData.add(DataModel("Zurich, CH"))
        listData.add(DataModel("Thrissur, IN"))
        listData.add(DataModel("Kochi, IN"))
        listData.add(DataModel("Aluva, IN"))





    }
    private fun showForecastDetails(){
        viewModel.forecast_weather_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {

                val cityname = GET.getString("cityName", "Paris")
                if (cityname != null) {
                    viewModel.refreshData1(cityname)
                }

               /* val temp = data.daily[0].temp.max
                val description = data.daily[0].weather[0].description
                val date = data.daily[0].dt
                val icon = data.daily[0].weather[0].icon*/

            }
    })
    }

   /* private fun showForecastDetails(forecast: ForecastWeatherModel) {



        val temp = forecast.daily[0].temp.max
        val description = forecast.daily[0].weather[0].description
        val date = forecast.daily[0].dt
        val icon = forecast.daily[0].weather[0].icon
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationDashboard(temp, description, date, icon)
        findNavController().navigate(action)
        //findNavController()
    }*/

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    /*override fun onItemClick(dataModel: DataModel) {

        val fragment:Fragment = NotificationsFragment.newInstance(dataModel.title!!)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.hide(activity?.supportFragmentManager!!.findFragmentByTag("main_fragment")!!)
        transaction.add(R.id.frame_holder, fragment)
            transaction.addToBackStack(null)
       transaction.commit()
    }*/


    companion object {
        const val CITYNAME = "cityname"

        fun newInstance(cityName: String) : DashboardFragment {
            val fragment = DashboardFragment()

            val args = Bundle()
            args.putString(CITYNAME, cityName)
            fragment.arguments = args

            return fragment
        }
    }

}