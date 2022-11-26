package com.example.cruddatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button tambah;
    ListView listView;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambah = findViewById(R.id.btnTambah);
        listView = findViewById(R.id.listView);

        mDatabaseHelper = new DatabaseHelper(this);

        getAllProduct();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateData.class);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedProductName = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(MainActivity.this, EditData.class);
                intent.putExtra("nama",selectedProductName);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showAllProduct(){
        List<Product> products = mDatabaseHelper.getAllProduct();
        String result = "";
        for (int i=0; i < products.size(); i++){
            Product product1 = products.get(i);
            result = result + product1.getName()+"@"+product1.getPrice()+", ";
        }
    }

    private void getAllProduct() {
        List<Product> products = mDatabaseHelper.getAllProduct();
        List<String> productNames = new ArrayList<>();
        for (int i=0; i < products.size(); i++){
            Product product1 = products.get(i);
            productNames.add(String.valueOf(product1.getName()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,productNames);
        listView.setAdapter(adapter);
    }
}