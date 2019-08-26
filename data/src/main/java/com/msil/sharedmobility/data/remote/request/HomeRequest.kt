package com.msil.sharedmobility.data.remote.request

sealed class HomeRequest {
     class RequestCityList(): HomeRequest()
}