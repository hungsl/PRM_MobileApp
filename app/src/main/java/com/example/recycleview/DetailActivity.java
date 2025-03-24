//package com.example.recycleview;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.util.Log;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.button.MaterialButton;
//
//public class DetailActivity extends AppCompatActivity {
//    private ImageView detailImageView;
//    private TextView detailProductName;
//    private TextView detailBriefDescription;
//    private TextView detailFullDescription;
//    private TextView detailTechSpecs;
//    private TextView detailPrice;
//
//    private TextView quantityTextView;
//    private MaterialButton decreaseButton, increaseButton, backButton, addToCartButton;
//    private int quantity = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//
//        // Ánh xạ các view
//        detailImageView = findViewById(R.id.detailImageView);
//        detailProductName = findViewById(R.id.detailProductName);
//        detailBriefDescription = findViewById(R.id.detailBriefDescription);
//        detailFullDescription = findViewById(R.id.detailFullDescription);
//        detailTechSpecs = findViewById(R.id.detailTechSpecs);
//        detailPrice = findViewById(R.id.detailPrice);
//
//        quantityTextView = findViewById(R.id.quantityTextView);
//        decreaseButton = findViewById(R.id.decreaseButton);
//        increaseButton = findViewById(R.id.increaseButton);
//
//        addToCartButton = findViewById(R.id.addToCartButton);
//        backButton = findViewById(R.id.backButton);
//
//        // Lấy dữ liệu từ Intent
//        Intent intent = getIntent();
//        int imageResId = intent.getIntExtra("imageResId", 0);
//        String productName = intent.getStringExtra("productName");
//        String briefDescription = intent.getStringExtra("briefDescription");
//        String fullDescription = intent.getStringExtra("fullDescription");
//        String techSpecs = intent.getStringExtra("techSpecs");
//        double price = intent.getDoubleExtra("price", 0.0);
//
//        // Hiển thị dữ liệu
//        detailImageView.setImageResource(imageResId);
//        detailProductName.setText(productName);
//        detailBriefDescription.setText(briefDescription);
//        detailFullDescription.setText(fullDescription);
//        detailTechSpecs.setText(techSpecs);
//        detailPrice.setText(String.format("$%.2f", price));
//
//        decreaseButton.setOnClickListener(v -> {
//            if (quantity > 1) { // Không cho giảm dưới 1
//                quantity--;
//                quantityTextView.setText(String.valueOf(quantity));
//            }
//        });
//
//        // Logic nút tăng
//        increaseButton.setOnClickListener(v -> {
//            quantity++; // Có thể thêm giới hạn tối đa nếu muốn
//            quantityTextView.setText(String.valueOf(quantity));
//        });
//        // Logic nút Quay về
//        backButton.setOnClickListener(v -> finish());
//
//        // Logic nút Add to Cart
//        if (addToCartButton == null) {
//            Log.e("DetailActivity", "Button addToCartButton is null!");
//        } else {
//            addToCartButton.setOnClickListener(v -> {
//                // Tạo CartItem với thông tin sản phẩm và số lượng
//                CartItem cartItem = new CartItem(imageResId, productName, price, quantity);
//
//                // Thêm vào danh sách giỏ hàng trong CartActivity
//                CartActivity.getCartItems().add(cartItem);
//
//                // Hiển thị thông báo
//                Toast.makeText(this, productName + " added to cart! Quantity: " + quantity, Toast.LENGTH_SHORT).show();
//            });
//        }
//    }
//}
//
//package com.example.recycleview;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.example.recycleview.api.ApiService;
//import com.example.recycleview.api.RetrofitClient;
//import com.google.android.material.button.MaterialButton;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DetailActivity extends AppCompatActivity {
//    private ImageView detailImageView;
//    private TextView detailProductName, detailBriefDescription, detailFullDescription, detailTechSpecs, detailPrice;
//    private MaterialButton backButton, addToCartButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//
//        // Map UI elements
//        detailImageView = findViewById(R.id.detailImageView);
//        detailProductName = findViewById(R.id.detailProductName);
//        detailBriefDescription = findViewById(R.id.detailBriefDescription);
//        detailFullDescription = findViewById(R.id.detailFullDescription);
//        detailTechSpecs = findViewById(R.id.detailTechSpecs);
//        detailPrice = findViewById(R.id.detailPrice);
//        backButton = findViewById(R.id.backButton);
//        addToCartButton = findViewById(R.id.addToCartButton);
//
//        int productId = getIntent().getIntExtra("productId", 0);
//        if (productId > 0) {
//            fetchProductDetail(productId);
//        }
//
//        backButton.setOnClickListener(v -> finish());
//    }
//
//    private void fetchProductDetail(int productId) {
//        ApiService apiService = RetrofitClient.getApiService();
//        Call<ProductDetailDTO> call = apiService.getProductDetail(productId);
//
//        call.enqueue(new Callback<ProductDetailDTO>() {
//            @Override
//            public void onResponse(Call<ProductDetailDTO> call, Response<ProductDetailDTO> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ProductDetailDTO product = response.body();
//                    displayProductDetails(product);
//                } else {
//                    Toast.makeText(DetailActivity.this, "Failed to load product details", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductDetailDTO> call, Throwable t) {
//                Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("DetailActivity", "API Call Failure", t);
//            }
//        });
//    }
//
//    private void displayProductDetails(ProductDetailDTO product) {
//        detailProductName.setText(product.getProductName());
//        detailBriefDescription.setText(product.getBriefDescription());
//        detailFullDescription.setText(product.getFullDescription());
//        detailTechSpecs.setText(product.getTechnicalSpecifications());
//        detailPrice.setText(String.format("$%.2f", product.getPrice()));
//
//        Glide.with(this)
//                .load(product.getImageUrl())
//                .placeholder(R.drawable.placeholderimgproductdetail) // Placeholder image
//                .error(R.drawable.placeholderimgproductdetail) // Error image
//                .into(detailImageView);
//    }
//
////    decreaseButton.setOnClickListener(v -> {
////            if (quantity > 1) { // Không cho giảm dưới 1
////                quantity--;
////                quantityTextView.setText(String.valueOf(quantity));
////            }
////        });
////
////        // Logic nút tăng
////        increaseButton.setOnClickListener(v -> {
////            quantity++; // Có thể thêm giới hạn tối đa nếu muốn
////            quantityTextView.setText(String.valueOf(quantity));
////        });
////        // Logic nút Quay về
////        backButton.setOnClickListener(v -> finish());
////
////        // Logic nút Add to Cart
////        if (addToCartButton == null) {
////            Log.e("DetailActivity", "Button addToCartButton is null!");
////        } else {
////            addToCartButton.setOnClickListener(v -> {
////                // Tạo CartItem với thông tin sản phẩm và số lượng
////                CartItem cartItem = new CartItem(imageResId, productName, price, quantity);
////
////                // Thêm vào danh sách giỏ hàng trong CartActivity
////                CartActivity.getCartItems().add(cartItem);
////
////                // Hiển thị thông báo
////                Toast.makeText(this, productName + " added to cart! Quantity: " + quantity, Toast.LENGTH_SHORT).show();
////            });
////        }
//}
package com.example.recycleview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.recycleview.api.ApiService;
import com.example.recycleview.api.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImageView;
    private TextView detailProductName, detailBriefDescription, detailFullDescription, detailTechSpecs, detailPrice, detailCategory, quantityTextView;
    private MaterialButton backButton, addToCartButton, increaseButton, decreaseButton;
    private int quantity = 1; // Default quantity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Map UI elements
        detailImageView = findViewById(R.id.detailImageView);
        detailProductName = findViewById(R.id.detailProductName);
        detailBriefDescription = findViewById(R.id.detailBriefDescription);
        detailFullDescription = findViewById(R.id.detailFullDescription);
        detailTechSpecs = findViewById(R.id.detailTechSpecs);
        detailPrice = findViewById(R.id.detailPrice);
        detailCategory = findViewById(R.id.detailCategory);
        backButton = findViewById(R.id.backButton);
        addToCartButton = findViewById(R.id.addToCartButton);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        quantityTextView = findViewById(R.id.quantityTextView);

        int productId = getIntent().getIntExtra("productId", 0);
        if (productId > 0) {
            fetchProductDetail(productId);
        }

        // Back button logic
        backButton.setOnClickListener(v -> finish());

        // Decrease quantity logic
        decreaseButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityTextView.setText(String.valueOf(quantity));
            }
        });

        // Increase quantity logic
        increaseButton.setOnClickListener(v -> {
            quantity++;
            quantityTextView.setText(String.valueOf(quantity));
        });

        // Add to Cart logic
//        addToCartButton.setOnClickListener(v -> {
//            try {
//                String productName = detailProductName.getText().toString();
//                double price = Double.parseDouble(detailPrice.getText().toString().replace("$", ""));
//                int productIdFromTextView = Integer.parseInt(detailProductName.getTag().toString()); // Use tag to store ID
//
//                CartItem cartItem = new CartItem(productIdFromTextView, productName, price, quantity);
//                CartActivity.getCartItems().add(cartItem);
//
//                Toast.makeText(this, productName + " added to cart! Quantity: " + quantity, Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                Toast.makeText(this, "Error adding to cart", Toast.LENGTH_SHORT).show();
//                Log.e("DetailActivity", "Error adding item to cart", e);
//            }
//        });

        addToCartButton.setOnClickListener(v -> {
            try {
                String productName = detailProductName.getText().toString();
                double price = Double.parseDouble(detailPrice.getText().toString().replace("$", ""));
                int productIdFromTextView = Integer.parseInt(detailProductName.getTag().toString()); // Use tag to store ID

                // Create CartItem object
                CartItem cartItem = new CartItem(productIdFromTextView, productName, price, quantity);

                // Get the JWT token from shared preferences (or wherever you store it)
                SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                String jwtToken = preferences.getString("jwt_token", "");

                // Make the API call to save the cart item
                ApiService apiService = RetrofitClient.getApiService();
                Call<Void> call = apiService.addToCart("Bearer " + jwtToken, cartItem);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DetailActivity.this, productName + " added to cart! Quantity: " + quantity, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, "Failed to add item to cart.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(this, "Error adding to cart", Toast.LENGTH_SHORT).show();
                Log.e("DetailActivity", "Error adding item to cart", e);
            }
        });

    }

    private void fetchProductDetail(int productId) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<ProductDetailDTO> call = apiService.getProductDetail(productId);

        call.enqueue(new Callback<ProductDetailDTO>() {
            @Override
            public void onResponse(Call<ProductDetailDTO> call, Response<ProductDetailDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailDTO product = response.body();
                    displayProductDetails(product);
                } else {
                    Toast.makeText(DetailActivity.this, "Failed to load product details", Toast.LENGTH_SHORT).show();
                    Log.e("DetailActivity", "API Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProductDetailDTO> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DetailActivity", "API Call Failure", t);
            }
        });
    }

    private void displayProductDetails(ProductDetailDTO product) {
        detailProductName.setText(product.getProductName());
        detailProductName.setTag(product.getProductId()); // Store productId in tag for safe retrieval
        detailBriefDescription.setText(product.getBriefDescription());
        detailFullDescription.setText(product.getFullDescription());
        detailTechSpecs.setText(product.getTechnicalSpecifications());
        detailPrice.setText(String.format("$%.2f", product.getPrice()));
        detailCategory.setText(product.getCategoryName());

        Glide.with(this)
                .load(product.getImageUrl())
                .placeholder(R.drawable.placeholderimgproductdetail)
                .error(R.drawable.placeholderimgproductdetail)
                .into(detailImageView);
    }
}
