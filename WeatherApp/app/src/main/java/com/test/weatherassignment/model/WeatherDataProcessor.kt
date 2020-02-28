package com.test.weatherassignment.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.test.weatherassignment.network.NetworkRepository
import com.test.weatherassignment.network.response.WeatherHourlyResponse
import com.test.weatherassignment.network.response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WeatherDataProcessor(val repository: WeatherDataRepository = WeatherDataRepository(), val networkRepository: NetworkRepository = NetworkRepository()) {

    interface OnWeatherDataListener {
        fun onSuccess(weatherData: WeatherData)
    }

    interface OnWeatherHourlyDataListener {
        fun onSuccessHourly(weatherHourlyData: WeatherHourlyResponse)
    }

    fun saveWeatherData(tc: WeatherData) {
        repository.saveWeatherData(tc)
    }

    fun clearWeatherData() {
        repository.clearWeatherData()
    }

    fun mergeLocalDataList(dataList: List<WeatherData>) {
        repository.mergeLocalDataList(dataList)
    }

    fun loadWeatherDataByDt(createDate: Date): WeatherData? {
        return repository.loadWeatherDataByDt(createDate)
    }

    fun loadWeatherData(): LiveData<List<WeatherData>> {
        return repository.loadWeatherData()
    }

    fun getWeatherData(filterText:String,onWeatherDataListener: OnWeatherDataListener) {
        val call = networkRepository.weatherService.getWeather(filterText, networkRepository.appId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                if (response != null && response.isSuccessful) {
                    val weatherItem = response.body()
                    onWeatherDataListener.onSuccess(WeatherData(weatherItem?.name!!, weatherItem.sys?.country!!, weatherItem.weather?.get(0)?.description!!, weatherItem.main?.temp!!, weatherItem.dt.toString()))
                } else {
                    onFailure(call, Throwable("Bad Response"))
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Log.e("Response Failure", t?.message, t)
            }
        })
    }

    fun getHourlyWeatherData(filterText:String,onWeatherDataListener: OnWeatherHourlyDataListener) {
        val call = networkRepository.weatherService.getHourlyWeather(filterText, networkRepository.appId)
        call.enqueue(object : Callback<WeatherHourlyResponse> {
            override fun onResponse(call: Call<WeatherHourlyResponse>?, response: Response<WeatherHourlyResponse>?) {
                if (response != null && response.isSuccessful) {
                    val weatherItem = response.body()
                    weatherItem?.let { onWeatherDataListener.onSuccessHourly(it) }
                } else {
                    onFailure(call, Throwable("Bad Response"))
                }
            }

            override fun onFailure(call: Call<WeatherHourlyResponse>?, t: Throwable?) {
                Log.e("Response Failure", t?.message, t)
            }
        })
    }


}