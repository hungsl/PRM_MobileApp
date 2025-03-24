package com.example.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.api.ApiService;
import com.example.recycleview.api.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Item> dataList;
    private Spinner sortSpinner;
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sortSpinner = findViewById(R.id.sortSpinner);
        setupSortSpinner();

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Item>> call = apiService.getProducts();

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataList = response.body();
                    adapter = new MyAdapter(MainActivity.this, dataList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }

                //        // Thiết lập Bottom Navigation
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.nav_home) {
//                return true;
//            } else if (itemId == R.id.nav_cart) {
//                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
//                startActivity(cartIntent);
//                return true;
//            } else if (itemId == R.id.nav_map) {
//                startActivity(new Intent(MainActivity.this, MapActivity.class));
//                return true;
//            }
//            return false;
//        });
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupSortSpinner() {
        String[] sortOptions = {"Default", "Price: Low to High", "Price: High to Low", "Alphabetical"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (dataList != null) {
                    switch (position) {
                        case 1:
                            sortByPriceAscending();
                            break;
                        case 2:
                            sortByPriceDescending();
                            break;
                        case 3:
                            sortByAlphabetical();
                            break;
                        default:
                            break;
                    }
                }
        // Thiết lập Bottom Navigation
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_cart) {
                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            } else if (itemId == R.id.nav_chat) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
                return true;
            }else if (itemId == R.id.nav_map) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                return true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void sortByPriceAscending() {
        Collections.sort(dataList, Comparator.comparingDouble(Item::getPrice));
        adapter.notifyDataSetChanged();
    }

    private void sortByPriceDescending() {
        Collections.sort(dataList, (item1, item2) -> Double.compare(item2.getPrice(), item1.getPrice()));
        adapter.notifyDataSetChanged();
    }

    private void sortByAlphabetical() {
        Collections.sort(dataList, Comparator.comparing(Item::getProductName));
        adapter.notifyDataSetChanged();
    }
}