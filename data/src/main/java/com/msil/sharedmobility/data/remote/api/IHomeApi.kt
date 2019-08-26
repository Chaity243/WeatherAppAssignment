package com.msil.sharedmobility.data.remote.api

import com.msil.sharedmobility.data.remote.dto.HomeDto
import com.msil.sharedmobility.data.remote.request.HomeRequest

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface IHomeApi {
    /**
     * Get all albums
     */
    @GET("albums")
    fun getAlbums(
        @Query("_start") page: Int,
        @Query("_limit") pageSize: Int
    ): Single<List<HomeDto.Home>>

    @GET("cities/2")
    fun getCityList():Single<HomeDto.CommonResponse<Any>>
}