package com.example.recycleview;
import android.content.SharedPreferences;
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

    // Thông tin người dùng từ API
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

        // Lấy thông tin người dùng từ API
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token != null) {
            Log.d("Firebase", "Token đã được lưu: " + token);
        } else {
            Log.e("Firebase", "Token chưa được lưu!");
            // Xử lý trường hợp không có token (ví dụ: quay lại màn hình đăng nhập)
            finish();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        Call<UserResponse> call = apiService.getUser("Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse user = response.body();
                    currentUser = user.getUsername();
                    userRole = user.getRole();
                    Log.d("ChatActivity", "User: " + currentUser + ", Role: " + userRole);

                    // Chỉ khởi tạo các thành phần phụ thuộc sau khi có dữ liệu
                    setupChat();
                } else {
                    Log.e("ChatActivity", "Failed to fetch user data: " + response.code());
                    // Xử lý lỗi (ví dụ: hiển thị thông báo và quay lại)
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("ChatActivity", "Error fetching user data: " + t.getMessage());
                // Xử lý lỗi (ví dụ: hiển thị thông báo và quay lại)
                finish();
            }
        });

        // Xử lý nút Back
        backButton.setOnClickListener(v -> finish());
    }

    private void setupChat() {
        // Thiết lập RecyclerView
        chatAdapter = new ChatAdapter(this, messageList, currentUser);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);

        // Tạo room chat giữa user và admin
        String roomId = userRole.equals("user") ? currentUser + "_admin" : "admin_" + currentUser;
        chatRef = FirebaseDatabase.getInstance().getReference("chat_rooms").child(roomId).child("messages");

        // Lắng nghe tin nhắn mới
        chatRef.addChildEventListener(new ChildEventListener() {
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
        });

        // Xử lý nút Send
        sendButton.setOnClickListener(v -> {
            String messageText = messageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                String sender = userRole.equals("user") ? currentUser : "admin";
                Message message = new Message(messageText, sender, System.currentTimeMillis());
                chatRef.push().setValue(message);
                messageEditText.setText("");
            }
        });
    }
}