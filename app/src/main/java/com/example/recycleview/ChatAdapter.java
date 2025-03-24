package com.example.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private static final int VIEW_TYPE_USER = 1; // Tin nhắn của người dùng hiện tại
    private static final int VIEW_TYPE_OTHER_USER = 2; // Tin nhắn của user khác
    private static final int VIEW_TYPE_ADMIN = 3; // Tin nhắn của admin

    private Context context;
    private List<Message> messageList;
    private String currentUser;

    public ChatAdapter(Context context, List<Message> messageList, String currentUser) {
        this.context = context;
        this.messageList = messageList;
        this.currentUser = currentUser;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message.getSender().equals(currentUser)) {
            return VIEW_TYPE_USER; // Tin nhắn của người dùng hiện tại
        } else if (message.getRole().equals("admin")) {
            return VIEW_TYPE_ADMIN; // Tin nhắn của admin
        } else {
            return VIEW_TYPE_OTHER_USER; // Tin nhắn của user khác
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_USER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_user, parent, false);
        } else if (viewType == VIEW_TYPE_ADMIN) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_admin, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_other_user, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.textViewMessage.setText(message.getText());
        holder.textViewSender.setText(message.getSender());
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(message.getTimestamp()));
        holder.textViewTimestamp.setText(time);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage, textViewTimestamp, textViewSender;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            textViewSender = itemView.findViewById(R.id.textViewSender);
        }
    }
}