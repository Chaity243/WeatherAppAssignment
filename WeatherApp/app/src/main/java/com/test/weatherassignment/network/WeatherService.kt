package com.test.weatherassignment.network

import com.test.weatherassignment.network.response.WeatherHourlyResponse
import com.test.weatherassignment.network.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by chaitanya on 14.08.2018.
 */
interface WeatherService {

    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") id: String, @Query("appid") appId: String): Call<WeatherResponse>

    @GET("/data/2.5/forecast")
    fun getHourlyWeather(@Query("q") id: String, @Query("appid") appId: String): Call<WeatherHourlyResponse>
}