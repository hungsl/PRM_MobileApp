//package com.example.recycleview;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.button.MaterialButton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartActivity extends AppCompatActivity {
//    private RecyclerView cartRecyclerView;
//    private TextView cartTotalTextView;
//    private MaterialButton clearCartButton;
//    private MaterialButton purchaseCartButton;
//    private static List<CartItem> cartItems = new ArrayList<>();
//    private CartAdapter cartAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//        Button backButton = findViewById(R.id.backButton);
//        // Ánh xạ các view
//        cartRecyclerView = findViewById(R.id.cartRecyclerView);
//        cartTotalTextView = findViewById(R.id.cartTotalTextView);
//        clearCartButton = findViewById(R.id.clearCartButton);
//        purchaseCartButton = findViewById(R.id.purchaseCartButton);
//
//        // Thiết lập RecyclerView
//        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        cartAdapter = new CartAdapter(this, cartItems);
//        cartRecyclerView.setAdapter(cartAdapter);
//        // Cập nhật tổng tiền
//        updateCartTotal();
//
//        // Xử lý nút xóa toàn bộ giỏ hàng
//        clearCartButton.setOnClickListener(v -> {
//            cartItems.clear();
//            cartAdapter.notifyDataSetChanged();
//            updateCartTotal();
//        });
//        // Xử lý sự kiện khi bấm Thanh toán
//        purchaseCartButton.setOnClickListener(v -> {
//            if (cartItems.isEmpty()) {
//                Toast.makeText(this, "Cart emtpy!", Toast.LENGTH_SHORT).show();
//            } else {
//                // Chuyển sang màn hình thanh toán (hoặc mở dialog)
////                Intent intent = new Intent(this, CheckoutActivity.class);
////                startActivity(intent);
//            }
//        });
////        // Xử lý sự kiện khi bấm nút back
//        backButton.setOnClickListener(v -> finish());
//    }
//
//    // Hàm cập nhật tổng tiền giỏ hàng
//    public void updateCartTotal() {
//        double total = 0;
//        for (CartItem item : cartItems) {
//            total += item.getPrice() * item.getQuantity();
//        }
//        cartTotalTextView.setText(String.format("Total: $%.2f", total));
//    }
//
//    // Getter để các Activity khác có thể thêm sản phẩm vào giỏ hàng
//    public static List<CartItem> getCartItems() {
//        return cartItems;
//    }
//}


//package com.example.recycleview;
//
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.recycleview.api.ApiService;
//import com.example.recycleview.api.RetrofitClient;
//import com.google.android.material.button.MaterialButton;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class CartActivity extends AppCompatActivity {
//    private RecyclerView cartRecyclerView;
//    private TextView cartTotalTextView;
//    private MaterialButton clearCartButton;
//    private MaterialButton purchaseCartButton;
//    private List<CartItem> cartItems;
//    private String jwtToken;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        // Initialize views
//        cartRecyclerView = findViewById(R.id.cartRecyclerView);
//        cartTotalTextView = findViewById(R.id.cartTotalTextView);
//        clearCartButton = findViewById(R.id.clearCartButton);
//        purchaseCartButton = findViewById(R.id.purchaseCartButton);
//        Button backButton = findViewById(R.id.backButton);
//
//        // Set up RecyclerView
//        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Get JWT token from SharedPreferences
//        jwtToken = "Bearer " + getSharedPreferences("user_prefs", MODE_PRIVATE).getString("jwt_token", "");
//
//        // Fetch the cart data
//        fetchCartData();
//
//        // Set listeners for buttons
//        clearCartButton.setOnClickListener(v -> clearCart());
//        purchaseCartButton.setOnClickListener(v -> checkout());
//        backButton.setOnClickListener(v -> finish());
//    }
//
//    private void fetchCartData() {
//        ApiService apiService = RetrofitClient.getApiService();
//
//        // Make the network request with the Authorization header
//        Call<List<CartItem>> call = apiService.getCartItems(jwtToken);
//        call.enqueue(new Callback<List<CartItem>>() {
//            @Override
//            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
//                if (response.isSuccessful()) {
//                    // Get the cart items from the response and update the RecyclerView
//                    cartItems = response.body();
//                    updateCartTotal();
//                } else {
//                    Toast.makeText(CartActivity.this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CartItem>> call, Throwable t) {
//                Toast.makeText(CartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Method to update the total price of the cart
//    public void updateCartTotal() {
//        double total = 0;
//        for (CartItem item : cartItems) {
//            total += item.getPrice() * item.getQuantity();
//        }
//        cartTotalTextView.setText(String.format("Total: $%.2f", total));
//    }
//
//    // Method to clear the cart
//    private void clearCart() {
//        cartItems.clear();
//        updateCartTotal();
//    }
//
//    // Method to handle the checkout process (you can navigate to a CheckoutActivity or show a dialog)
//    private void checkout() {
//        if (cartItems.isEmpty()) {
//            Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
//        } else {
//            // Proceed to checkout (start a new activity or show a dialog)
//            // Intent intent = new Intent(this, CheckoutActivity.class);
//            // startActivity(intent);
//        }
//    }
//}



package com.example.recycleview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.api.ApiService;
import com.example.recycleview.api.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private TextView cartTotalTextView;
    private MaterialButton clearCartButton;
    private MaterialButton purchaseCartButton;
    private List<CartItem> cartItems;
    private String jwtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartTotalTextView = findViewById(R.id.cartTotalTextView);
        clearCartButton = findViewById(R.id.clearCartButton);
        purchaseCartButton = findViewById(R.id.purchaseCartButton);
        Button backButton = findViewById(R.id.backButton);

        // Set up RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get JWT token from SharedPreferences
        jwtToken = "Bearer " + getSharedPreferences("user_prefs", MODE_PRIVATE).getString("jwt_token", "");

        // Fetch the cart data
        fetchCartData();

        // Set listeners for buttons
        clearCartButton.setOnClickListener(v -> clearCart());
        purchaseCartButton.setOnClickListener(v -> checkout());
        backButton.setOnClickListener(v -> finish());
    }

    private void fetchCartData() {
        ApiService apiService = RetrofitClient.getApiService();

        // Make the network request with the Authorization header
        Call<List<CartItem>> call = apiService.getCartItems(jwtToken);
        call.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the cart items from the response and update the RecyclerView
                    cartItems = response.body();
                    updateCartTotal();

                    // Set up adapter for RecyclerView
                    CartAdapter cartAdapter = new CartAdapter(CartActivity.this, cartItems);
                    cartRecyclerView.setAdapter(cartAdapter);
                } else {
                    Toast.makeText(CartActivity.this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to update the total price of the cart
    private void clearCart() {
        if (cartItems != null) {
            cartItems.clear();
            updateCartTotal();
            cartRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void updateCartTotal() {
        double total = 0;
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                total += item.getPrice() * item.getQuantity();
            }
        }
        cartTotalTextView.setText(String.format("Total: $%.2f", total));
    }

    // Method to handle the checkout process (you can navigate to a CheckoutActivity or show a dialog)
    private void checkout() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
        } else {
            // Proceed to checkout (start a new activity or show a dialog)
            // Intent intent = new Intent(this, CheckoutActivity.class);
            // startActivity(intent);
            Toast.makeText(this, "Proceeding to checkout", Toast.LENGTH_SHORT).show();
        }
    }
}

