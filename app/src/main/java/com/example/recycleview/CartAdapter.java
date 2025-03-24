package com.example.recycleview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImageView;
        TextView cartProductName;
        TextView cartPrice;
        EditText cartQuantity;
        ImageButton removeButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            cartImageView = itemView.findViewById(R.id.cartImageView);
            cartProductName = itemView.findViewById(R.id.cartProductName);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem currentItem = cartItems.get(position);
        holder.cartImageView.setImageResource(currentItem.getImageResId());
        holder.cartProductName.setText(currentItem.getProductName());
        holder.cartPrice.setText(String.format("$%.2f", currentItem.getPrice()));
        holder.cartQuantity.setText(String.valueOf(currentItem.getQuantity()));

        // Cập nhật số lượng khi người dùng thay đổi
        holder.cartQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int newQuantity = Integer.parseInt(s.toString());
                    currentItem.setQuantity(newQuantity);
                    ((CartActivity) context).updateCartTotal();
                } catch (NumberFormatException e) {
                    currentItem.setQuantity(1); // Default về 1 nếu nhập sai
                }
            }
        });

        // Xóa sản phẩm khỏi giỏ hàng
        holder.removeButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyDataSetChanged();
            ((CartActivity) context).updateCartTotal();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
