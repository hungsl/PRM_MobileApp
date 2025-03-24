package com.example.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {
    private ImageView detailImageView;
    private TextView detailProductName;
    private TextView detailBriefDescription;
    private TextView detailFullDescription;
    private TextView detailTechSpecs;
    private TextView detailPrice;

    private TextView quantityTextView;
    private MaterialButton decreaseButton, increaseButton, backButton, addToCartButton;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ánh xạ các view
        detailImageView = findViewById(R.id.detailImageView);
        detailProductName = findViewById(R.id.detailProductName);
        detailBriefDescription = findViewById(R.id.detailBriefDescription);
        detailFullDescription = findViewById(R.id.detailFullDescription);
        detailTechSpecs = findViewById(R.id.detailTechSpecs);
        detailPrice = findViewById(R.id.detailPrice);

        quantityTextView = findViewById(R.id.quantityTextView);
        decreaseButton = findViewById(R.id.decreaseButton);
        increaseButton = findViewById(R.id.increaseButton);

        addToCartButton = findViewById(R.id.addToCartButton);
        backButton = findViewById(R.id.backButton);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int imageResId = intent.getIntExtra("imageResId", 0);
        String productName = intent.getStringExtra("productName");
        String briefDescription = intent.getStringExtra("briefDescription");
        String fullDescription = intent.getStringExtra("fullDescription");
        String techSpecs = intent.getStringExtra("techSpecs");
        double price = intent.getDoubleExtra("price", 0.0);

        // Hiển thị dữ liệu
        detailImageView.setImageResource(imageResId);
        detailProductName.setText(productName);
        detailBriefDescription.setText(briefDescription);
        detailFullDescription.setText(fullDescription);
        detailTechSpecs.setText(techSpecs);
        detailPrice.setText(String.format("$%.2f", price));

        decreaseButton.setOnClickListener(v -> {
            if (quantity > 1) { // Không cho giảm dưới 1
                quantity--;
                quantityTextView.setText(String.valueOf(quantity));
            }
        });

        // Logic nút tăng
        increaseButton.setOnClickListener(v -> {
            quantity++; // Có thể thêm giới hạn tối đa nếu muốn
            quantityTextView.setText(String.valueOf(quantity));
        });
        // Logic nút Quay về
        backButton.setOnClickListener(v -> finish());

        // Logic nút Add to Cart
        if (addToCartButton == null) {
            Log.e("DetailActivity", "Button addToCartButton is null!");
        } else {
            addToCartButton.setOnClickListener(v -> {
                // Tạo CartItem với thông tin sản phẩm và số lượng
                CartItem cartItem = new CartItem(imageResId, productName, price, quantity);

                // Thêm vào danh sách giỏ hàng trong CartActivity
                CartActivity.getCartItems().add(cartItem);

                // Hiển thị thông báo
                Toast.makeText(this, productName + " added to cart! Quantity: " + quantity, Toast.LENGTH_SHORT).show();
            });
        }
    }
}