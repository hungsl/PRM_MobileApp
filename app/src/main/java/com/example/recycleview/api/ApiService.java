package com.example.recycleview.api;

import com.example.recycleview.CartItem;
import com.example.recycleview.Item;
import com.example.recycleview.ProductDetailDTO;
import com.example.recycleview.RequestAddtoCartDTO;
import com.example.recycleview.login.LoginRequest;
import com.example.recycleview.login.LoginResponse;

import java.util.List;

import com.example.recycleview.login.StoreLocation;
import com.example.recycleview.login.UpdateQuantityRequest;
import com.example.recycleview.login.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/User/Login")
    Call<LoginResponse> login(@Body LoginRequest request);
    @GET("api/Product")
    Call<List<Item>> getProducts();
    @GET("api/Product/{id}")
    Call<ProductDetailDTO> getProductDetail(@Path("id") int productId);
    @POST("api/Cart/AddToCart")  // Replace with your actual API endpoint
    Call<Void> addToCart(
            @Body RequestAddtoCartDTO requestAddtoCartDTO // The CartItem object to send to the backend
    );
    @GET("api/Cart/cartItems/{userName}")  // Change this to your actual endpoint
    Call<List<CartItem>> getCartItems(@Path("userName") String userName);

    // Thêm API để xóa một mục trong giỏ hàng
    @DELETE("api/Cart/RemoveItem/{cartItemId}")
    Call<Void> removeCartItem(@Path("cartItemId") int cartItemId);

    // Thêm API để cập nhật số lượng
    @PUT("api/Cart/UpdateQuantity")
    Call<Void> updateQuantity(@Body UpdateQuantityRequest request);

    // Thêm phương thức DELETE để xóa giỏ hàng
    @DELETE("api/Cart/ClearCart/{userName}")
    Call<Void> clearCart(@Path("userName") String userName);

    @GET("api/User/GetUser")
    Call<UserResponse> getUser(@Header("Authorization") String token);
    @GET("api/StoreLocation")
    Call<List<StoreLocation>> getStoreLocations();
}