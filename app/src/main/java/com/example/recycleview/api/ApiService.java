package com.example.recycleview.api;

import com.example.recycleview.login.LoginRequest;
import com.example.recycleview.login.LoginResponse;
import com.example.recycleview.login.StoreLocation;
import com.example.recycleview.login.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/User/Login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("api/User/GetUser")
    Call<UserResponse> getUser(@Header("Authorization") String token);
    @GET("api/StoreLocation")
    Call<List<StoreLocation>> getStoreLocations();
}