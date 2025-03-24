package com.example.recycleview.api;

import com.example.recycleview.CartItem;
import com.example.recycleview.Item;
import com.example.recycleview.ProductDetailDTO;
import com.example.recycleview.login.LoginRequest;
import com.example.recycleview.login.LoginResponse;

import java.util.List;

import com.example.recycleview.login.StoreLocation;
import com.example.recycleview.login.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/User/Login")
    Call<LoginResponse> login(@Body LoginRequest request);
    @GET("api/Product")
    Call<List<Item>> getProducts();
    @GET("api/Product/{id}")
    Call<ProductDetailDTO> getProductDetail(@Path("id") int productId);
    @POST("cart/add")  // Replace with your actual API endpoint
    Call<Void> addToCart(
            @Header("Authorization") String authToken, // JWT token for authorization
            @Body CartItem cartItem // The CartItem object to send to the backend
    );
    @GET("cart/items")  // Change this to your actual endpoint
    Call<List<CartItem>> getCartItems(@Header("Authorization") String authToken);


    @GET("api/User/GetUser")
    Call<UserResponse> getUser(@Header("Authorization") String token);
    @GET("api/StoreLocation")
    Call<List<StoreLocation>> getStoreLocations();
}