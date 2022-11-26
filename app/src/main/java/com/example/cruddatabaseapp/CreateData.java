package com.example.cruddatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateData extends AppCompatActivity {

    ImageView back;
    EditText editProduk,editHarga;
    Button tambah;

    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

        editProduk = findViewById(R.id.editTxtProduk);
        editHarga = findViewById(R.id.editTxtHarga);
        tambah = findViewById(R.id.btnTambah);
        back = findViewById(R.id.btnBack);

        mDatabaseHelper = new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateData.this,MainActivity.class);
                startActivity(intent);
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                editProduk.setText("");
                editHarga.setText("");
            }
        });
    }

    public void insertData(){
        Product product = new Product();
        product.setName(editProduk.getText().toString());
        product.setPrice(Integer.valueOf(editHarga.getText().toString()));

        mDatabaseHelper.insertRecord(product);
        Toast.makeText(CreateData.this, "Berhasil Menambah Barang", Toast.LENGTH_SHORT).show();
    }
}