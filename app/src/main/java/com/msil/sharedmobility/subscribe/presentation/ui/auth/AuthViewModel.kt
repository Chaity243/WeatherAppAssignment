package com.msil.sharedmobility.subscribe.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.usecases.auth.AuthUseCase
import com.msil.sharedmobility.subscribe.presentation.base.BaseViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authUseCase: AuthUseCase) : BaseViewModel() {

    private var getOtpLiveData: MutableLiveData<ResultState<Entity.CommonResponse<Any>>> = MutableLiveData();
    private var validateOtpLiveData: MutableLiveData<ResultState<Entity.ValidateOtp>> = MutableLiveData();

    private var registerUserLiveData: MutableLiveData<ResultState<Entity.CommonResponse<Entity.RegisterUserData>>> = MutableLiveData();
    private var addUserLiveData: MutableLiveData<ResultState<Entity.CommonResponse<Any>>> = MutableLiveData();



    fun getOtpLiveData(): MutableLiveData<ResultState<Entity.CommonResponse<Any>>> {
        return getOtpLiveData
    }

    fun validateLiveData(): MutableLiveData<ResultState<Entity.ValidateOtp>> {
        return validateOtpLiveData
    }

    fun getAddUserLiveData(): MutableLiveData<ResultState<Entity.CommonResponse<Any>>> {
        return addUserLiveData
    }


    fun registerUserLiveData(): MutableLiveData<ResultState<Entity.CommonResponse<Entity.RegisterUserData>>> {
        return registerUserLiveData
    }

    internal fun getOtp(mobileNumber: String, appId: String) {
        authUseCase.getOtp(mobileNumber, appId).toFlowable().subscribe {
            getOtpLiveData.postValue(it)
        }.track()
    }

    internal fun validateOtp(mobileNumber: String, otp: String){
        authUseCase.validateOtp(mobileNumber, otp).toFlowable().subscribe {
            validateOtpLiveData.postValue(it)
        }.track()
    }

    internal fun registerUser(name:String, email: String, mobileNumber: String, password: String){
        authUseCase.registerUser(name, email, mobileNumber, password).toFlowable().subscribe{
            registerUserLiveData.postValue(it)
        }.track()
    }

    internal fun addUserDetails(userId:Int, gender: String, dob: String){


        authUseCase.addUserDetails(userId, gender, dob).toFlowable().subscribe{
            addUserLiveData.postValue(it)
        }.track()
    }
}