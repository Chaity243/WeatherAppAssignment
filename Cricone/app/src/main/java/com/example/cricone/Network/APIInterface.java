package com.example.cricone.Network;

import com.example.cricone.Requests.LoginRequest;
import com.example.cricone.Responses.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface APIInterface {
   /* @POST("app_api.php?type=user_login")
    @FormUrlEncoded
    Call<LoginResponse> loginUser(@Body LoginRequest request);
*/


    @POST("app_api.php?type=user_login")
    @FormUrlEncoded
    Call<LoginResponse> loginUser(@FieldMap Map<String,String> params);

    @POST("app_api.php?type=user_registration")
    @FormUrlEncoded
    Call<LoginResponse> register(@FieldMap Map<String,String> params);


}
