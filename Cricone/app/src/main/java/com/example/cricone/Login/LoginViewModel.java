package com.example.cricone.Login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cricone.Network.Source;
import com.example.cricone.Responses.LoginResponse;

public class LoginViewModel extends ViewModel {

    Source source = Source.getInstance();
    MutableLiveData<LoginResponse> loginData;
     public enum LoginStatus{
         NOT_LOGGED_IN,LOGIN_PROGRESS,LOGIN_COMPLETED
    }

    public void initialize(){
         loginStatus.setValue(LoginStatus.NOT_LOGGED_IN);
    }
    MutableLiveData loginStatus = new MutableLiveData<LoginResponse>();


    public LiveData<LoginResponse>  performLogin(String email , String password) {
        // loginStatus.setValue(LoginStatus.LOGIN_PROGRESS);
      //  loginData.setValue(source.login(email,password));
        loginData = source.login(email, password);
//        Log.e("Inside ViewModel",loginData.getValue().getApiText());
        return loginData;
    }

    public LiveData<LoginResponse>  performRegister(String email , String password,String username) {

        loginData = source.register(username,password,email);
        return loginData;
    }


}
