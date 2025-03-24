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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.productName.setText(cartItem.getProductName());
        holder.productPrice.setText(String.format("$%.2f", cartItem.getPrice()));
        holder.cartQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Load image using Glide
        Glide.with(context)
                .load(cartItem.getProductImage())
                .placeholder(R.drawable.placeholderimgproductdetail)
                .into(holder.productImage);

        // Handle quantity change
        holder.cartQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                try {
                    int quantity = Integer.parseInt(charSequence.toString());
                    cartItem.setQuantity(quantity);
                    ((CartActivity) context).updateCartTotal();
                } catch (NumberFormatException e) {
                    cartItem.setQuantity(0); // Default value for invalid input
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Remove item from cart
        holder.removeButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            ((CartActivity) context).updateCartTotal();  // Update total in activity
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        EditText cartQuantity;
        ImageView productImage;
        ImageButton removeButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cartProductName);
            productPrice = itemView.findViewById(R.id.cartPrice);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            productImage = itemView.findViewById(R.id.cartImageView);
            removeButton = itemView.findViewById(R.id.removeButton);
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


