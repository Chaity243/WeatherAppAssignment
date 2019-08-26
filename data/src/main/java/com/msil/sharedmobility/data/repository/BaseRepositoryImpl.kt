package com.msil.sharedmobility.data.repository

import com.google.gson.GsonBuilder
import com.msil.sharedmobility.data.mapper.map
import com.msil.sharedmobility.data.remote.dto.ErrorDto
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import okhttp3.ResponseBody
import retrofit2.HttpException


abstract class BaseRepositoryImpl {


    internal fun handleErrorReturn(throwable: Throwable): ResultState.Error<ResultState<List<Entity.Album>>> {
        return when (throwable) {
            is HttpException -> {
                var error: ErrorDto.ErrorResponse = getError(throwable.response()!!.errorBody()!!)!!
//                var e: Entity.ErrorEntity = error.map()
                ResultState.Error<ResultState<List<Entity.Album>>>(throwable, error.map())
            }
            else -> {
                ResultState.Error<ResultState<List<Entity.Album>>>(throwable, null)
            }
        }
    }
    internal fun getError(responseBody: ResponseBody): ErrorDto.ErrorResponse {
        val gsonBuilder = GsonBuilder()
//        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        val gson = gsonBuilder.create()
        return gson.fromJson(responseBody.toString(), ErrorDto.ErrorResponse::class.java)
    }
}