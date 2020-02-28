package com.test.weatherassignment.network.response

import com.google.gson.annotations.SerializedName
import com.test.weatherassignment.network.response.model.*

data class WeatherResponseObject(

        @field:SerializedName("dt")
        val dt: Int? = null,

        @field:SerializedName("weather")
        val weather: List<WeatherItem?>? = null,


        @field:SerializedName("main")
        val main: Main? = null,

        @field:SerializedName("clouds")
        val clouds: Clouds? = null,

        @field:SerializedName("sys")
        val sys: Sys? = null,

        @field:SerializedName("wind")
        val wind: Wind? = null,

        @field:SerializedName("dt_txt")
        val dt_txt: String? = null
)