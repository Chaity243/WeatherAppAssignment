package com.msil.sharedmobility.data.remote.dto

sealed class ErrorDto {

    data class ErrorResponse(val error: Boolean,val errorMessages: List<Error>,val data : Any?):ErrorDto()

    data class Error(val errorCode: String, val errorMessages: String):ErrorDto()


}