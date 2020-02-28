package com.example.cricone.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.cricone.Requests.LoginRequest;
import com.example.cricone.Responses.LoginResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Source {

    private static Source instance;

    private Source(){}

    public static synchronized Source getInstance(){
        if(instance == null){
            instance = new Source();
        }
        return instance;
    }

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


    public MutableLiveData<LoginResponse> login(String userName, String userPassword){
       /* Map<String,String> params = new HashMap<String, String>();
        params.put("username", userName);
        params.put("password", userPassword);
        params.put("s", "1");*/


        MutableLiveData<LoginResponse> loginData = new MutableLiveData<>();
        HashMap<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password",userPassword);
        map.put("s","1");
        apiInterface.loginUser(map)
                .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    loginData.setValue(response.body());


                    Log.e("Sucessfull",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginData.setValue(null);
                Log.e("ERROR ON Failure",t.toString());

            }
        });


        return loginData;
    }

    public MutableLiveData<LoginResponse> register(String userName, String userPassword,String email){
       /* Map<String,String> params = new HashMap<String, String>();
        params.put("username", userName);
        params.put("password", userPassword);
        params.put("s", "1");*/


        MutableLiveData<LoginResponse> loginData = new MutableLiveData<>();
        HashMap<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password",userPassword);
        map.put("email",email);
        map.put("confirm_password",userPassword);
        map.put("s","1");
        apiInterface.register(map)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call,
                                           Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            loginData.setValue(response.body());


                            Log.e("Sucessfull",response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        loginData.setValue(null);
                        Log.e("ERROR ON Failure",t.toString());

                    }
                });


        return loginData;
    }




}



