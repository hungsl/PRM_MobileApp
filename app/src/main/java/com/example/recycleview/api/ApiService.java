package com.example.recycleview.api;

import com.example.recycleview.login.LoginRequest;
import com.example.recycleview.login.LoginResponse;
import com.example.recycleview.login.RegisterRequest;
import com.example.recycleview.login.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/User/Login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/User/Register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

}