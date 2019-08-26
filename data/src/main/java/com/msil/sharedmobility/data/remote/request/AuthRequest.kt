package com.msil.sharedmobility.data.remote.request

sealed class AuthRequest {

    data class RequestOtp(val mobileNo: String): AuthRequest()

    data class RequestValidateOtp(val mobileNo: String, val otp: String): AuthRequest()

    data class RequestRegisterUser( val name: String,
                                    val email: String,
                                    val mobileNo: String,
                                    val password: String):AuthRequest()


    data class RequestAddUserDetail( val userId: Int,
                                     val  gender: String,
                                     val dob: String):AuthRequest()


}