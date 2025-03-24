package com.example.recycleview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Item> items;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView price;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.price);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.textView.setText(currentItem.getProductName());
        holder.imageView.setImageResource(currentItem.getImageResId());
        holder.price.setText(String.valueOf(currentItem.getPrice()));
        // Thêm sự kiện click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("imageResId", currentItem.getImageResId());
                intent.putExtra("productName", currentItem.getProductName());
                intent.putExtra("briefDescription", currentItem.getBriefDescription());
                intent.putExtra("fullDescription", currentItem.getFullDescription());
                intent.putExtra("techSpecs", currentItem.getTechnicalSpecifications());
                intent.putExtra("price", currentItem.getPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
