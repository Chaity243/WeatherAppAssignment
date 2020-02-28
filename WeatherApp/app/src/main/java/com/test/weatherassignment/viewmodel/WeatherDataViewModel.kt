package com.test.weatherassignment.viewmodel;

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.test.weatherassignment.model.WeatherData
import com.test.weatherassignment.model.WeatherDataProcessor
import com.test.weatherassignment.network.response.WeatherHourlyResponse
import com.test.weatherassignment.utils.DatabaseHelper

class WeatherDataViewModel @JvmOverloads constructor(app: Application, val weatherDataProcessor: WeatherDataProcessor = WeatherDataProcessor()) : ObservableViewModel(app), WeatherDataProcessor.OnWeatherDataListener,
    WeatherDataProcessor.OnWeatherHourlyDataListener {


    var weatherHourlyDataValue:MutableLiveData<WeatherHourlyResponse> = MutableLiveData()
    var lastWeatherData = WeatherData()
    var weatherDataListener: WeatherDataProcessor.OnWeatherDataListener? = null
    var weatherDataHourlyListener: WeatherDataProcessor.OnWeatherHourlyDataListener? = null
    val db by lazy { DatabaseHelper(app) }

    init {
        weatherDataListener = this
        weatherDataHourlyListener=this
        //weatherDataProcessor.getWeatherData("Delhi",this)
        weatherDataProcessor.getHourlyWeatherData("Delhi",this)

    }

    override fun onSuccessHourly(weatherHourlyData: WeatherHourlyResponse) {
        println("Reached ViewModel here $weatherHourlyData")
        weatherHourlyDataValue.postValue(weatherHourlyData)
    }

    override fun onSuccess(weatherData: WeatherData) {
        weatherDataProcessor.saveWeatherData(weatherData)
        updateOutputs(weatherData)
        //db.insertData(weatherData)
    }

    fun updateOutputs(wd: WeatherData) {
        lastWeatherData = wd
        notifyChange()
    }

    fun loadSavedWeatherDataSummaries(): LiveData<List<WeatherDataSummaryItem>> {
        weatherDataProcessor.mergeLocalDataList(db.readData())
        return Transformations.map(weatherDataProcessor.loadWeatherData()) { weatherDataObjects ->
            weatherDataObjects.map {
                WeatherDataSummaryItem(it.temp,
                        it.dateCreated)
            }
        }
    }
}