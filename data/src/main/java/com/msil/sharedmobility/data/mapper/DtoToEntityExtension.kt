package com.msil.sharedmobility.data.mapper

import com.msil.sharedmobility.data.remote.dto.HomeDto
import com.msil.sharedmobility.data.remote.dto.AuthDto
import com.msil.sharedmobility.data.remote.dto.ErrorDto
import com.msil.sharedmobility.domain.entity.Entity

/**
 * Created by Shivang Goel on 08/09/2019.
 */
/**
 * Extension class to map album dto to album entity
 */
fun HomeDto.Home.map() = Entity.Album(
    id = id,
    userId = userId,
    title = title
)

fun ErrorDto.ErrorResponse.map() = Entity.ErrorEntity(
    errorMessages.map {
        it.map()
    }
)

fun ErrorDto.Error.map() = Entity.Error(
    errorCode = errorCode,
    errorMessages = errorMessages
)

//fun map(response: ErrorDto.Error): Entity.Error {
//    return Entity.Error(
//        errorCode = error()
//    )
//}


/*fun AuthDto.OTP.map() = Entity.OTP(
    otp = otp
)*/

fun <T>HomeDto.CommonResponse<T>.map() = Entity.CommonResponse(
    error = error,
    errorMessages =  errorMessages,
    data = data
)

fun <T>AuthDto.CommonResponse<T>.map() = Entity.CommonResponse(
    error = error,
    errorMessages =  errorMessages,
    data = data
)
fun AuthDto.ValidateOtp.map() = Entity.ValidateOtp(
    error = error,
    errorMessages =  errorMessages,
    data = data
)

fun AuthDto.RegisterUser.map() = Entity.RegisterUser(
    userId = userId,
    message = message
)


fun AuthDto.AddUserDetail.map() = Entity.AddUserDetail(
    userId = userId,
    gender = gender,
    dob = dob
)
