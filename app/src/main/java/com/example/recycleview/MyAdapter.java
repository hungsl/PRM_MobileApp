package com.example.recycleview;

import android.content.Context;
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

    // Constructor để nhận dữ liệu
    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    // ViewHolder ánh xạ các view trong item_layout
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Lấy dữ liệu của từng item
        Item currentItem = items.get(position);

        // Gán dữ liệu cho TextView và ImageView
        holder.textView.setText(currentItem.getText());
        holder.imageView.setImageResource(currentItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return items.size(); // Số lượng item
    }
}
