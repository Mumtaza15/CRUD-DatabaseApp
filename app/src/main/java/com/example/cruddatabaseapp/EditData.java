package com.example.cruddatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class EditData extends AppCompatActivity {

    ImageView back;
    EditText editProduk,editHarga;
    Button update,hapus;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        back = findViewById(R.id.btnBack);
        editProduk = findViewById(R.id.editTxtProduk);
        editHarga = findViewById(R.id.editTxtHarga);
        update = findViewById(R.id.btnUpdate);
        hapus = findViewById(R.id.btnHapus);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        String selectedProductName = intent.getStringExtra("nama");

        List<Product> products = mDatabaseHelper.getAllProduct();
        for (int i=0; i < products.size(); i++){
            Product product1 = products.get(i);
            if (product1.getName().equals(selectedProductName)){
                editProduk.setText(product1.getName());
                editHarga.setText(String.valueOf(product1.getPrice()));
                break;
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct(selectedProductName);
                Toast.makeText(EditData.this, selectedProductName + " berhasil diperbarui", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(selectedProductName);
                Toast.makeText(EditData.this, selectedProductName + " berhasil dihapus", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateProduct(String productName){
        List<Product> products = mDatabaseHelper.getAllProduct();
        for (int i=0; i < products.size(); i++){
            Product product1 = products.get(i);
            if (product1.getName().equals(productName)){
                product1.setName(String.valueOf(editProduk.getText()));
                product1.setPrice(Integer.valueOf(String.valueOf(editHarga.getText())));
                mDatabaseHelper.updateProduct(product1);
                break;
            }
        }
    }

    private void deleteProduct(String productName){
        List<Product> products = mDatabaseHelper.getAllProduct();
        for (int i=0; i < products.size(); i++){
            Product product1 = products.get(i);
            if (product1.getName().equals(productName)){
                mDatabaseHelper.deleteProduct(product1);
                break;
            }
        }
    }
}