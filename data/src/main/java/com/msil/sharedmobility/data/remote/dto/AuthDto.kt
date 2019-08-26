package com.msil.sharedmobility.data.remote.dto

import com.google.gson.annotations.SerializedName

sealed class AuthDto{

    data class CommonResponse<T>(val error: Boolean,val errorMessages: List<Error>,val data : T?):AuthDto()

    data class ValidateOtp(val error: Boolean,val errorMessages: Any?,val data : Any?):AuthDto()

    data class RegisterUser(val userId:String, val message:String):AuthDto()

    data class AddUserDetail(val userId: Int,
                             val  gender: String,
                             val dob: String):AuthDto()
}