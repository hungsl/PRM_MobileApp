package com.example.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private TextView cartTotalTextView;
    private MaterialButton clearCartButton;
    private MaterialButton purchaseCartButton;
    private static List<CartItem> cartItems = new ArrayList<>();
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Button backButton = findViewById(R.id.backButton);
        // Ánh xạ các view
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartTotalTextView = findViewById(R.id.cartTotalTextView);
        clearCartButton = findViewById(R.id.clearCartButton);
        purchaseCartButton = findViewById(R.id.purchaseCartButton);

        // Thiết lập RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(cartAdapter);
        // Cập nhật tổng tiền
        updateCartTotal();

        // Xử lý nút xóa toàn bộ giỏ hàng
        clearCartButton.setOnClickListener(v -> {
            cartItems.clear();
            cartAdapter.notifyDataSetChanged();
            updateCartTotal();
        });
        // Xử lý sự kiện khi bấm Thanh toán
        purchaseCartButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart emtpy!", Toast.LENGTH_SHORT).show();
            } else {
                // Chuyển sang màn hình thanh toán (hoặc mở dialog)
//                Intent intent = new Intent(this, CheckoutActivity.class);
//                startActivity(intent);
            }
        });
//        // Xử lý sự kiện khi bấm nút back
        backButton.setOnClickListener(v -> finish());
    }

    // Hàm cập nhật tổng tiền giỏ hàng
    public void updateCartTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        cartTotalTextView.setText(String.format("Total: $%.2f", total));
    }

    // Getter để các Activity khác có thể thêm sản phẩm vào giỏ hàng
    public static List<CartItem> getCartItems() {
        return cartItems;
    }
}