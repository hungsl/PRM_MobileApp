package com.example.recycleview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;

import com.example.recycleview.api.ApiService;
import com.example.recycleview.api.RetrofitClient;
import com.example.recycleview.login.StoreLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MaterialButton directionsButton;
    private ImageButton backButton;
    private LatLng storeLocation; // Lưu trữ vị trí từ API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Ánh xạ
        directionsButton = findViewById(R.id.directionsButton);
        backButton = findViewById(R.id.backButton);

        // Khởi tạo bản đồ
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Gọi API để lấy vị trí cửa hàng
        ApiService apiService = RetrofitClient.getApiService();
        if (apiService == null) {
            Log.e("MapActivity", "ApiService is null!");
            finish();
            return;
        }

        Call<List<StoreLocation>> call = apiService.getStoreLocations();
        call.enqueue(new Callback<List<StoreLocation>>() {
            @Override
            public void onResponse(Call<List<StoreLocation>> call, Response<List<StoreLocation>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    StoreLocation location = response.body().get(0); // Lấy vị trí đầu tiên
                    storeLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.d("MapActivity", "Store location: " + storeLocation.toString());

                    // Cập nhật bản đồ nếu đã sẵn sàng
                    if (mMap != null) {
                        updateMap();
                    }
                } else {
                    Log.e("MapActivity", "Failed to fetch store location: " + response.code());
                    // Dùng dữ liệu cứng làm fallback
                    storeLocation = new LatLng(10.7769, 106.7009);
                    if (mMap != null) {
                        updateMap();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StoreLocation>> call, Throwable t) {
                Log.e("MapActivity", "Error fetching store location: " + t.getMessage());
                // Dùng dữ liệu cứng làm fallback
                storeLocation = new LatLng(10.7769, 106.7009);
                if (mMap != null) {
                    updateMap();
                }
            }
        });

        // Xử lý nút Get Directions
        directionsButton.setOnClickListener(v -> {
            if (storeLocation != null) {
                String uri = "google.navigation:q=" + storeLocation.latitude + "," + storeLocation.longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            } else {
                Log.e("MapActivity", "Store location is null!");
            }
        });

        // Xử lý nút Back
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Cập nhật bản đồ nếu đã có dữ liệu từ API
        if (storeLocation != null) {
            updateMap();
        }
    }

    private void updateMap() {
        mMap.addMarker(new MarkerOptions()
                .position(storeLocation)
                .title("Store Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15f));
    }
}
