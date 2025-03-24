package com.example.recycleview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tìm RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<Item> dataList = new ArrayList<>();
        dataList.add(new Item(R.drawable.datastructure, "Data Structure"));
        dataList.add(new Item(R.drawable.c_plus, "C++"));
        dataList.add(new Item(R.drawable.c_sharp, "C#"));
        dataList.add(new Item(R.drawable.java_script, "JavaScript"));
        dataList.add(new Item(R.drawable.java, "Java"));
        dataList.add(new Item(R.drawable.c_langue, "C-Language"));
        dataList.add(new Item(R.drawable.datastructure, "Data Structure"));
        dataList.add(new Item(R.drawable.c_plus, "C++"));
        dataList.add(new Item(R.drawable.c_sharp, "C#"));
        dataList.add(new Item(R.drawable.java_script, "JavaScript"));
        dataList.add(new Item(R.drawable.java, "Java"));
        dataList.add(new Item(R.drawable.c_langue, "C-Language"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter adapter = new MyAdapter(this, dataList);
        // Gán Adapter
        recyclerView.setAdapter(adapter);
    }
}