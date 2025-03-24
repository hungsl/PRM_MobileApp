package com.example.recycleview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MaterialButton directionsButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Ánh xạ nút Get Directions
        directionsButton = findViewById(R.id.directionsButton);
        backButton = findViewById(R.id.backButton);
        // Khởi tạo bản đồ
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Xử lý nút Get Directions
        directionsButton.setOnClickListener(v -> {
            // Dữ liệu cứng: vị trí cửa hàng
            LatLng storeLocation = new LatLng(10.7769, 106.7009); // Ví dụ: Ho Chi Minh City
            String uri = "google.navigation:q=" + storeLocation.latitude + "," + storeLocation.longitude;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps"); // Mở Google Maps
            startActivity(intent);
        });

        // Xử lý nút Back
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Dữ liệu cứng: vị trí cửa hàng (Ho Chi Minh City)
        LatLng storeLocation = new LatLng(10.7769, 106.7009);
        mMap.addMarker(new MarkerOptions()
                .position(storeLocation)
                .title("Store Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15f)); // Zoom level 15
    }
}
