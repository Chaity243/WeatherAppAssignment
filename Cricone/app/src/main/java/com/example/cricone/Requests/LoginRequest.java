package com.example.cricone.Requests;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("s")
    public String s;

    public LoginRequest(String username, String password, String s) {
        this.username = username;
        this.password = password;
        this.s = s;
    }
}
