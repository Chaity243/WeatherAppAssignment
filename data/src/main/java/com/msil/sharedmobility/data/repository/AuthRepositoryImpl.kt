package com.msil.sharedmobility.data.repository

import com.msil.shadata.common.extension.applyIoScheduler
import com.msil.sharedmobility.data.mapper.map
import com.msil.sharedmobility.data.remote.api.IAuthApi
import com.msil.sharedmobility.data.remote.dto.ErrorDto
import com.msil.sharedmobility.data.remote.request.AuthRequest
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.domain.repository.auth.AuthRepository
import io.reactivex.Maybe
import io.reactivex.Single
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class AuthRepositoryImpl(val api: IAuthApi) : BaseRepositoryImpl(), AuthRepository {
    override fun addUserDetails(userId: Int, gender: String, dob: String): Single<ResultState<Entity.CommonResponse<Any>>> {
        return api.addUserDetail(AuthRequest.RequestAddUserDetail(userId, gender, dob))
            .applyIoScheduler().map {

                ResultState.Success(it.map()) as ResultState<Entity.CommonResponse<Any>>
            }.onErrorReturn { it ->
                if (it is HttpException) {

                    var error: ErrorDto.ErrorResponse = getError(it.response()!!.errorBody()!!)!!
                    var e: Entity.ErrorEntity = error.map()

                    ResultState.Error<ResultState<List<Entity.Album>>>(it, e)
                }
                ResultState.Error(it, null)
            }
    }

    override fun getOtp(mobileNumber: String, appId: String): Maybe<ResultState<Entity.CommonResponse<Any>>> {

        return api.getOtp(AuthRequest.RequestOtp(mobileNumber))
            .applyIoScheduler().map {
                println(it.toString())

                ResultState.Success(it.map()) as ResultState<Entity.CommonResponse<Any>>
            }.onErrorReturn { it ->

                if (it is HttpException) {

                    var error: ErrorDto.ErrorResponse = getError(it.response()!!.errorBody()!!)!!
                    var e: Entity.ErrorEntity = error.map()

                    ResultState.Error<ResultState<List<Entity.Album>>>(it, e)
                }
                ResultState.Error(it, null)
            }
    }

    override fun validateOtp(mobileNumber: String, otp: String): Single<ResultState<Entity.ValidateOtp>> {
        return api.validateOtp(AuthRequest.RequestValidateOtp(mobileNumber, otp))
            .applyIoScheduler()
            .map {
                ResultState.Success(it.map()) as ResultState<Entity.ValidateOtp>
            }
            .onErrorReturn { it ->
                if (it is HttpException) {

                    var error: ErrorDto.ErrorResponse = getError(it.response()!!.errorBody()!!)!!
                    var e: Entity.ErrorEntity = error.map()

                    ResultState.Error<ResultState<List<Entity.Album>>>(it, e)
                }
                ResultState.Error(it, null)
            }


    }
    private fun getErrorMessage(responseBody: ResponseBody): String? {
        try {
            val jsonObject = JSONObject(responseBody.string())
            return (jsonObject.getString("error"))
        } catch (e: Exception) {
            return e.message
        }

    }

    override fun registerUser(
        name: String,
        email: String,
        mobileNumber: String,
        password: String
    ): Single<ResultState<Entity.CommonResponse<Entity.RegisterUserData>>> {
        return api.registerUser(AuthRequest.RequestRegisterUser(name, email, mobileNumber, password))
            .applyIoScheduler()
            .map {
                println(it.toString())
                //println(it.message)
                ResultState.Success(it.map()) as ResultState<Entity.CommonResponse<Entity.RegisterUserData>>
            }
            .onErrorReturn { it ->
                if (it is HttpException) {

                    var error: ErrorDto.ErrorResponse = getError(it.response()!!.errorBody()!!)!!
                    var e: Entity.ErrorEntity = error.map()

                    ResultState.Error<ResultState<List<Entity.Album>>>(it, e)
                }
                ResultState.Error(it, null)
            }
    }

}