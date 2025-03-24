package com.example.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recycleview.api.ApiService;
import com.example.recycleview.api.RetrofitClient;
import com.example.recycleview.model.LoginRequest;
import com.example.recycleview.model.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameInput, passwordInput;
    private Button loginButton;
    private TextView forgotPasswordText, signUpText;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo ApiService
        apiService = RetrofitClient.getApiService();

        // Ánh xạ các view từ layout
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordText = findViewById(R.id.forgot_password_text);
        signUpText = findViewById(R.id.signup_text);

        // Xử lý sự kiện nút Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    usernameInput.setError("Please enter username");
                    passwordInput.setError("Please enter password");
                } else {
                    performLogin(username, password);
                }
            }
        });

        // Xử lý sự kiện Forgot Password (chưa triển khai)
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Triển khai Forgot Password sau
                Toast.makeText(LoginActivity.this, "Forgot Password clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện Sign Up (chưa triển khai)
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Triển khai Sign Up sau
                Toast.makeText(LoginActivity.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performLogin(String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        Call<LoginResponse> call = apiService.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    Toast.makeText(LoginActivity.this, "Login successful! Token: " + token, Toast.LENGTH_SHORT).show();

                    // Chuyển sang MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Đóng LoginActivity
                } else {
                    // In phản hồi thô để kiểm tra
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        android.util.Log.e("LoginError", "Response Code: " + response.code() + ", Error: " + errorBody);
                        Toast.makeText(LoginActivity.this, "Login failed: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                android.util.Log.e("LoginError", "Failure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}