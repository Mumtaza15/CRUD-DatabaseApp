package com.example.cruddatabaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "produk.db";
    private static final int DATABASE_VERSION = 1;

    private static final String PRODUCT_TABLE = "products";
    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";

    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + PRODUCT_TABLE +
                "(" + PRODUCT_TABLE + " INTEGER PRIMARY KEY, " +
                PRODUCT_NAME + " TEXT, " +
                PRODUCT_PRICE + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertRecord(Product product){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());

        db.insert(PRODUCT_TABLE, null, values);
        db.close();
    }

    public Product getProductFromName(String nama){
        SQLiteDatabase db = getReadableDatabase();

        //tabel apa yang mau dipakai, kolom apa aja,
        //cursor kayak pointer
        Cursor cursor = db.query(PRODUCT_TABLE,
                new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE},
                PRODUCT_NAME + "=" +nama,
                new String[]{nama},
                null,null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        Product product = new Product();
        product.setId(cursor.getInt(0));
        product.setName(cursor.getString(1));
        product.setPrice(cursor.getInt(2));

        return product;
    }

    public List<Product> getAllProduct(){
        List<Product> allProducts = new ArrayList<Product>();

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + PRODUCT_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setPrice(cursor.getInt(2));

                allProducts.add(product);
            }
            while (cursor.moveToNext());
        }
        return allProducts;
    }

    public void updateProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());

        db.update(PRODUCT_TABLE, values, PRODUCT_TABLE + "=?",
                new String[] {String.valueOf(product.getId())});
        db.close();
    }

    public void deleteProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PRODUCT_TABLE, PRODUCT_TABLE+"=?",
                new String[] {String.valueOf(product.getId())});
        db.close();
    }
}
