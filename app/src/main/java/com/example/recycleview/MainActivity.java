package com.example.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tìm RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        List<Item> dataList = new ArrayList<>();
        dataList.add(new Item(R.drawable.datastructure, "Data Structure",
                "Basic data structure concepts",
                "This course covers arrays, linked lists, stacks, queues, and more.",
                "Duration: 4 weeks\nLevel: Beginner", 49.99));
        dataList.add(new Item(R.drawable.c_plus, "C++",
                "Learn C++ programming",
                "In-depth C++ programming with OOP concepts.",
                "Duration: 6 weeks\nLevel: Intermediate", 59.99));
        dataList.add(new Item(R.drawable.java, "Java",
                "Java programming language",
                "Comprehensive Java course for all levels.",
                "Duration: 8 weeks\nLevel: All levels", 69.99));
        dataList.add(new Item(R.drawable.java_script, "JavaScript",
                "Master JavaScript basics",
                "Learn variables, functions, and DOM manipulation in JavaScript.",
                "Duration: 5 weeks\nLevel: Beginner", 54.99));
        dataList.add(new Item(R.drawable.java, "Java",
                "Java programming language",
                "Comprehensive Java course for all levels.",
                "Duration: 8 weeks\nLevel: All levels", 69.99));
        dataList.add(new Item(R.drawable.c_langue, "C-Language",
                "Introduction to C programming",
                "Covers basics of C including pointers and memory management.",
                "Duration: 6 weeks\nLevel: Intermediate", 59.99));
        dataList.add(new Item(R.drawable.datastructure, "Data Structure",
                "Basic data structure concepts",
                "This course covers arrays, linked lists, stacks, queues, and more.",
                "Duration: 4 weeks\nLevel: Beginner", 49.99));
        dataList.add(new Item(R.drawable.c_plus, "C++",
                "Learn C++ programming",
                "In-depth C++ programming with OOP concepts.",
                "Duration: 6 weeks\nLevel: Intermediate", 59.99));
        dataList.add(new Item(R.drawable.c_sharp, "C#",
                "C# programming essentials",
                "Build applications with C# and .NET framework.",
                "Duration: 7 weeks\nLevel: Intermediate", 64.99));
        dataList.add(new Item(R.drawable.java_script, "JavaScript",
                "Master JavaScript basics",
                "Learn variables, functions, and DOM manipulation in JavaScript.",
                "Duration: 5 weeks\nLevel: Beginner", 54.99));
        dataList.add(new Item(R.drawable.java, "Java",
                "Java programming language",
                "Comprehensive Java course for all levels.",
                "Duration: 8 weeks\nLevel: All levels", 69.99));
        dataList.add(new Item(R.drawable.c_langue, "C-Language",
                "Introduction to C programming",
                "Covers basics of C including pointers and memory management.",
                "Duration: 6 weeks\nLevel: Intermediate", 59.99));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter adapter = new MyAdapter(this, dataList);
        // Gán Adapter
        recyclerView.setAdapter(adapter);

        // Thiết lập Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {

                return true;
            } else if (itemId == R.id.nav_cart) {
                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            } else if (itemId == R.id.nav_user) {
                Toast.makeText(MainActivity.this, "User tab clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }
}