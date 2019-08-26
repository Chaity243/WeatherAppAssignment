package com.msil.sharedmobility.domain.usecases.auth

import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.repository.auth.AuthRepository
import io.reactivex.Maybe
import io.reactivex.Single

class AuthUseCaseImpl(private val authRepository: AuthRepository) : AuthUseCase {


    override fun addUserDetails(userId: Int, gender: String, dob: String): Single<ResultState<Entity.CommonResponse<Any>>> {
        return authRepository.addUserDetails(userId, gender, dob)
    }

    override fun getOtp(mobileNumber: String, appId: String): Maybe<ResultState<Entity.CommonResponse<Any>>> {
        return authRepository.getOtp(mobileNumber, appId)
    }

    override fun validateOtp(mobileNumber: String, otp: String): Single<ResultState<Entity.ValidateOtp>> {
        return authRepository.validateOtp(mobileNumber, otp)
    }

    override fun registerUser(
        name: String,
        email: String,
        mobileNumber: String,
        password: String
    ): Single<ResultState<Entity.CommonResponse<Entity.RegisterUserData>>> {
        return authRepository.registerUser(name, email, mobileNumber, password)
    }

}