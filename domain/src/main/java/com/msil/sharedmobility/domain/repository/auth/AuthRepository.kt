package com.msil.sharedmobility.domain.repository.auth

import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.repository.BaseRepository
import io.reactivex.Maybe
import io.reactivex.Single

interface AuthRepository : BaseRepository {

    fun getOtp(mobileNumber: String, appId: String): Maybe<ResultState<Entity.CommonResponse<Any>>>

    fun validateOtp(mobileNumber: String, otp: String): Single<ResultState<Entity.ValidateOtp>>

    fun registerUser(
        name: String,
        email: String,
        mobileNumber: String,
        password: String
    ): Single<ResultState<Entity.CommonResponse<Entity.RegisterUserData>>>


    fun addUserDetails(
        userId: Int,
        gender: String,
        dob: String
    ): Single<ResultState<Entity.CommonResponse<Any>>>
}