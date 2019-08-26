package com.msil.sharedmobility.data.remote.dto

import com.google.gson.annotations.SerializedName

sealed class HomeDto{

data class BaseDto(@SerializedName("error_code") val errorCode: Int):HomeDto()

    data class Home(
        @SerializedName("id") val id: Long,
        @SerializedName("userId") val userId: Long,
        @SerializedName("title") val title: String,
        val baseDto: BaseDto
    ) : HomeDto()

    data class CommonResponse<T>(val error: Boolean,val errorMessages: Any?,val data : T?):HomeDto()

}