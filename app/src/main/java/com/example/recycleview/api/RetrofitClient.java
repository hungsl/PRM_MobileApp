package com.example.recycleview.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder().build())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}