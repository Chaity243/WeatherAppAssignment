package com.test.weatherassignment.view;

import android.app.Dialog

import android.os.Bundle

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.test.weatherassignment.R
import com.test.weatherassignment.databinding.ActivityWeatherDataBinding
import com.test.weatherassignment.databinding.DetailDialogBinding
import com.test.weatherassignment.model.WeatherData
import com.test.weatherassignment.viewmodel.WeatherDataViewModel
import kotlinx.android.synthetic.main.content_weatherdata.*


class WeatherActivity : AppCompatActivity() {

    lateinit var binding: ActivityWeatherDataBinding
    lateinit var  hourlyAdaptor : WeatherDataAdapterWeekly
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weather_data_processor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                binding.vm?.weatherDataProcessor?.clearWeatherData()
                binding.vm?.db?.deleteAllData()
                recyclerView.visibility = View.VISIBLE
                recyclerViewHourly.visibility = View.GONE
                progressBar.visibility = View.GONE
                getWeatherData(binding.root)
                true
            }
            R.id.action_hourly_list -> {
                binding.vm?.weatherDataProcessor?.clearWeatherData()
                binding.vm?.db?.deleteAllData()
                recyclerView.visibility = View.GONE
                recyclerViewHourly.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                getHourlyList()
                true
            }
            R.id.action_clear -> {
                binding.vm?.weatherDataProcessor?.clearWeatherData()
                binding.vm?.db?.deleteAllData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getWeatherData(view: View) {
        binding.vm?.weatherDataProcessor?.getWeatherData(et_zipcode.text.toString(),binding.vm?.weatherDataListener!!)
        //binding.vm?.weatherDataProcessor?.getHourlyWeatherData("141003",binding.vm?.weatherDataListener!!)
    }
    fun getHourlyList(){
        binding.vm?.weatherDataProcessor?.getHourlyWeatherData(et_zipcode.text.toString(),
            binding.vm?.weatherDataHourlyListener!!)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_data)
        binding.vm = ViewModelProviders.of(this).get(WeatherDataViewModel::class.java)
        val vm = ViewModelProviders.of(this).get(WeatherDataViewModel::class.java)
        setSupportActionBar(binding.toolbar)
        recyclerView.setHasFixedSize(true)
        val adapter = WeatherDataAdapter { item -> showDialog(vm.weatherDataProcessor.loadWeatherDataByDt(item.dateCreated)!!) }
        recyclerView.adapter = adapter
        recyclerView.visibility= View.GONE

        hourlyAdaptor = WeatherDataAdapterWeekly()
        recyclerViewHourly.adapter = hourlyAdaptor
        vm.loadSavedWeatherDataSummaries().observe(this, Observer {
            if (it!!.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                recyclerViewHourly.visibility = View.GONE
                progressBar.visibility = View.GONE
                adapter.updateList(it)
            } else {
                if (progressBar.visibility != View.VISIBLE) {
                    recyclerViewHourly.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
                adapter.updateList(it)
            }
        })

        observeForecast(vm)
    }

    private fun observeForecast(vm: WeatherDataViewModel) {
        vm.weatherHourlyDataValue.observe(this, Observer {
            println("Inside activity $it")
            recyclerView.visibility = View.GONE
            recyclerViewHourly.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            it.list?.let { it1 -> hourlyAdaptor.updateList(it1) }
        })
    }

    private fun showDialog(weather: WeatherData) {
        val dialog = Dialog(this)
        val detailDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.detail_dialog, null, false) as DetailDialogBinding
        detailDialogBinding.weatherData = weather
        dialog.setContentView(detailDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
    }
}