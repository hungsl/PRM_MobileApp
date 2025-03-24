package com.example.recycleview.api;

import com.example.recycleview.model.LoginRequest;
import com.example.recycleview.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/User/Login")
    Call<LoginResponse> login(@Body LoginRequest request);
}