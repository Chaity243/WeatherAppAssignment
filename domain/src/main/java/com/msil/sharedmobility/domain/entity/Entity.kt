package com.msil.sharedmobility.domain.entity

import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Shivang Goel on 08/08/2019.
 */

sealed class Entity {
    /**
     * Album entity
     */
    data class Album(
        val id: Long,
        val title: String,
        val userId: Long
    ) : Entity()


    data class ErrorEntity(val errorMessages: List<Error>): Entity()

    data class Error(val errorCode: String, val errorMessages: String):Entity()

    /**
     * User Details Entity
     */
    data class UserDetails(
        val id: Int,
        val name: String
    ) : Entity()

    data class OTP(
        val otp: String
    ):Entity()

    data class ValidateOtp(
        val error: Boolean,val errorMessages: Any?,val data : Any?
    ):Entity()


    data class CommonResponse <T>(
        val error: Boolean,val errorMessages: Any?,val data : T
    ):Entity()

    data class RegisterUserData (
        val token: String,val id: String
    ):Entity()

    data class CityResponseList (
        val cityCode: String,val id: Int,val cityName: String,val imageURL1: String,val imageURL2: String,val popularityIndex: Int,val priority: Int
    ):Entity()

    data class RegisterUser(val userId:String, val message: String):Entity()


    data class AddUserDetail(val userId:Int, val gender: String,val dob: String):Entity()
}