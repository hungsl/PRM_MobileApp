//package com.example.recycleview;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
//    private Context context;
//    private List<CartItem> cartItems;
//
//    public CartAdapter(Context context, List<CartItem> cartItems) {
//        this.context = context;
//        this.cartItems = cartItems;
//    }
//
//    @Override
//    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // Inflate the cart item layout
//        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
//        return new CartViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(CartViewHolder holder, int position) {
//        // Get the cart item at the current position
//        CartItem cartItem = cartItems.get(position);
//
//        // Set the product name, price, and quantity
//        holder.cartProductName.setText(cartItem.getProductName());
//        holder.cartPrice.setText(String.format("$%.2f", cartItem.getPrice()));
//        holder.cartQuantity.setText(String.valueOf(cartItem.getQuantity()));
//
//        // Load the product image using Glide (make sure you have added Glide dependency)
//        Glide.with(context)
//                .load(cartItem.getImageUrl()) // Assuming CartItem has an image URL field
//                .into(holder.cartImageView);
//
//        // Set up the remove button
//        holder.removeButton.setOnClickListener(v -> {
//            // Remove the cart item from the list
//            cartItems.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, cartItems.size());
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return cartItems.size();
//    }
//
//    // ViewHolder to bind the layout's views
//    public class CartViewHolder extends RecyclerView.ViewHolder {
//        ImageView cartImageView;
//        TextView cartProductName;
//        TextView cartPrice;
//        EditText cartQuantity;
//        ImageButton removeButton;
//
//        public CartViewHolder(View itemView) {
//            super(itemView);
//            cartImageView = itemView.findViewById(R.id.cartImageView);
//            cartProductName = itemView.findViewById(R.id.cartProductName);
//            cartPrice = itemView.findViewById(R.id.cartPrice);
//            cartQuantity = itemView.findViewById(R.id.cartQuantity);
//            removeButton = itemView.findViewById(R.id.removeButton);
//        }
//    }
//}



package com.example.recycleview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleview.api.ApiService;
import com.example.recycleview.api.RetrofitClient;
import com.example.recycleview.login.UpdateQuantityRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.productName.setText(cartItem.getProductName());
        holder.productPrice.setText(String.format("$%.2f", cartItem.getPrice()));
        // Hiển thị số lượng
        holder.cartQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Hiển thị tổng giá của item (giá x số lượng)
        double itemTotal = cartItem.getPrice() * cartItem.getQuantity();
        holder.cartItemTotal.setText("Total: " + String.format("$%.2f", itemTotal));

        // Load image using Glide
        Glide.with(context)
                .load(cartItem.getProductImage())
                .placeholder(R.drawable.placeholderimgproductdetail)
                .error(R.drawable.placeholderimgproductdetail)
                .into(holder.productImage);

        // Hàm cập nhật số lượng (dùng chung cho cả nhập tay và nút tăng/giảm)
        Runnable updateQuantity = () -> {
            try {
                int quantity = Integer.parseInt(holder.cartQuantity.getText().toString());
                if (quantity < 0) {
                    quantity = 0;
                    holder.cartQuantity.setText("0");
                }
                cartItem.setQuantity(quantity);

                // Cập nhật tổng giá của item
                double updatedItemTotal = cartItem.getPrice() * quantity;
                holder.cartItemTotal.setText("Total: " + String.format("$%.2f", updatedItemTotal));

                // Vô hiệu hóa EditText và các nút trong khi gọi API
                holder.cartQuantity.setEnabled(false);
                holder.decreaseQuantityButton.setEnabled(false);
                holder.increaseQuantityButton.setEnabled(false);

                // Gọi API để cập nhật số lượng trên backend
                ApiService apiService = RetrofitClient.getApiService();
                UpdateQuantityRequest request = new UpdateQuantityRequest(cartItem.getCartItemId(), quantity);
                Call<Void> call = apiService.updateQuantity(request);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        holder.cartQuantity.setEnabled(true);
                        holder.decreaseQuantityButton.setEnabled(true);
                        holder.increaseQuantityButton.setEnabled(true);
                        if (response.isSuccessful()) {
                            ((CartActivity) context).updateCartTotal();
                        } else {
                            cartItem.setQuantity(cartItem.getQuantity());
                            holder.cartQuantity.setText(String.valueOf(cartItem.getQuantity()));
                            Toast.makeText(context, "Failed to update quantity: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        holder.cartQuantity.setEnabled(true);
                        holder.decreaseQuantityButton.setEnabled(true);
                        holder.increaseQuantityButton.setEnabled(true);
                        cartItem.setQuantity(cartItem.getQuantity());
                        holder.cartQuantity.setText(String.valueOf(cartItem.getQuantity()));
                        Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (NumberFormatException e) {
                cartItem.setQuantity(0);
                holder.cartQuantity.setText("0");

                double updatedItemTotal = cartItem.getPrice() * 0;
                holder.cartItemTotal.setText("Total: " + String.format("$%.2f", updatedItemTotal));

                ((CartActivity) context).updateCartTotal();
            }
        };

        // Handle quantity change (nhập tay)
        holder.cartQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {}

            @Override
            public void afterTextChanged(Editable editable) {
                updateQuantity.run();
            }
        });

        // Handle decrease quantity
        holder.decreaseQuantityButton.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.cartQuantity.getText().toString());
            if (currentQuantity > 0) {
                currentQuantity--;
                holder.cartQuantity.setText(String.valueOf(currentQuantity));
                // updateQuantity sẽ được gọi qua TextWatcher
            }
        });

        // Handle increase quantity
        holder.increaseQuantityButton.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.cartQuantity.getText().toString());
            currentQuantity++;
            holder.cartQuantity.setText(String.valueOf(currentQuantity));
            // updateQuantity sẽ được gọi qua TextWatcher
        });

        // Remove item from cart
        holder.removeButton.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition(); // Lấy vị trí hiện tại
            if (currentPosition != RecyclerView.NO_POSITION) { // Kiểm tra vị trí hợp lệ
                CartItem itemToRemove = cartItems.get(currentPosition);
                int cartItemId = itemToRemove.getCartItemId();
                ApiService apiService = RetrofitClient.getApiService();
                Call<Void> call = apiService.removeCartItem(cartItemId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            cartItems.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                            notifyItemRangeChanged(currentPosition, cartItems.size());
                            ((CartActivity) context).updateCartTotal();
                            Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to remove item: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, cartItemTotal;
        EditText cartQuantity;
        ImageView productImage;
        ImageButton removeButton;
        ImageButton decreaseQuantityButton; // Thêm nút giảm
        ImageButton increaseQuantityButton; // Thêm nút tăng

        public CartViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cartProductName);
            productPrice = itemView.findViewById(R.id.cartPrice);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            cartItemTotal = itemView.findViewById(R.id.cartItemTotal);
            productImage = itemView.findViewById(R.id.cartImageView);
            removeButton = itemView.findViewById(R.id.removeButton);
            decreaseQuantityButton = itemView.findViewById(R.id.decreaseQuantityButton); // Ánh xạ nút giảm
            increaseQuantityButton = itemView.findViewById(R.id.increaseQuantityButton); // Ánh xạ nút tăng
        }
    }

    // Method to update the cart total (in your activity)
    public void updateCartTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        // Update total TextView in the activity
    }
}


