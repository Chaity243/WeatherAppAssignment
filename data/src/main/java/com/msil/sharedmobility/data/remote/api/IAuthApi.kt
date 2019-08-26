package com.msil.sharedmobility.data.remote.api

import com.msil.sharedmobility.data.remote.dto.AuthDto
import com.msil.sharedmobility.data.remote.request.AuthRequest
import com.msil.sharedmobility.domain.entity.Entity
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {
    /*@GET("restaurant/")
    fun getRestaurantList(@Query("lat") lat: Double, @Query("lng") lng: Double,
                          @Query("offset") offset: Int, @Query("limit") limit: Int): Single<List<RestaurantResponse>>

    @GET("restaurant/{id}")
    fun getRestaurant(@Path("id") id: Int): Single<RestaurantResponse>*/

    @POST("dev/sendotp")
    fun getOtp(@Body requestOtp: AuthRequest.RequestOtp): Maybe<AuthDto.CommonResponse<Any>>

    @POST("dev/validateotp")
    fun validateOtp(@Body requestValidateOtp: AuthRequest.RequestValidateOtp): Single<AuthDto.ValidateOtp>

    @POST("dev/register")
    fun registerUser(@Body requestRegisterUser: AuthRequest.RequestRegisterUser):Single<AuthDto.CommonResponse<Entity.RegisterUserData>>

    @POST("dev/update")
    fun addUserDetail(@Body requestAddUserDetail: AuthRequest.RequestAddUserDetail):Single<AuthDto.CommonResponse<Any>>
}