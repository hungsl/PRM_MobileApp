package com.example.recycleview;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recycleview.login.UserResponse;
import retrofit2.Callback;

import com.example.recycleview.api.ApiService;
import com.example.recycleview.api.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private MaterialButton sendButton;
    private ImageButton backButton;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private DatabaseReference chatRef;
    private ChildEventListener chatListener;

    private String currentUser;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Ánh xạ
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        backButton = findViewById(R.id.backButton);

        // Khởi tạo danh sách tin nhắn
        messageList = new ArrayList<>();

        // Cuộn RecyclerView khi bàn phím hiện lên
        chatRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            chatRecyclerView.getWindowVisibleDisplayFrame(r);
            int screenHeight = chatRecyclerView.getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) {
                if (!messageList.isEmpty()) {
                    chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
                }
            }
        });

        // Lấy token từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            Log.e("ChatActivity", "Token is null!");
            finish();
            return;
        }
        Log.d("ChatActivity", "Token: " + token);

        // Gọi API
        ApiService apiService = RetrofitClient.getApiService();
        if (apiService == null) {
            Log.e("ChatActivity", "ApiService is null!");
            finish();
            return;
        }

        try {
            Call<UserResponse> call = apiService.getUser("Bearer " + token);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        UserResponse user = response.body();
                        currentUser = user.getUsername();
                        userRole = user.getRole();
                        Log.d("ChatActivity", "User: " + currentUser + ", Role: " + userRole);

                        setupChat();
                    } else {
                        Log.e("ChatActivity", "Failed to fetch user data: " + response.code());
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e("ChatActivity", "Error fetching user data: " + t.getMessage());
                    finish();
                }
            });
        } catch (Exception e) {
            Log.e("ChatActivity", "Exception in API call: " + e.getMessage(), e);
            finish();
        }

        // Xử lý nút Back
        backButton.setOnClickListener(v -> finish());
    }

    private void setupChat() {
        chatAdapter = new ChatAdapter(this, messageList, currentUser);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);

        // Sử dụng room chung "global_chat" cho tất cả user và admin
        chatRef = FirebaseDatabase.getInstance().getReference("chat_rooms").child("global_chat").child("messages");

        chatListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Message message = dataSnapshot.getValue(Message.class);
                messageList.add(message);
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ChatActivity", "Firebase error: " + databaseError.getMessage());
            }
        };
        chatRef.addChildEventListener(chatListener);

        sendButton.setOnClickListener(v -> {
            String messageText = messageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                // Gửi tin nhắn với sender là currentUser và role là userRole
                Message message = new Message(messageText, currentUser, userRole, System.currentTimeMillis());
                chatRef.push().setValue(message);
                messageEditText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (chatRef != null && chatListener != null) {
            chatRef.removeEventListener(chatListener);
            Log.d("ChatActivity", "Removed Firebase listener");
        }
    }
}