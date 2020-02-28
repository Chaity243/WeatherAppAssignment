package com.test.weatherassignment.network.response

import com.google.gson.annotations.SerializedName
import com.test.weatherassignment.network.response.model.*

data class WeatherHourlyResponse(

        @field:SerializedName("cod")
        val cod: Int? = null,

        @field:SerializedName("message")
        val message: String? = null,

        @field:SerializedName("list")
        val list: List<WeatherResponseObject>? = null,

        @field:SerializedName("city")
        val city: city? = null
)

data class city(
        @field:SerializedName("id")
        val id: Int? = null,
        @field:SerializedName("name")
        val name: String? = null,
        @field:SerializedName("coord")
        val coord: Coord? = null,
        @field:SerializedName("country")
        val country: String? = null
)