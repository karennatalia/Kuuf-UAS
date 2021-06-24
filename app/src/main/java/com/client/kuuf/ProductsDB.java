package com.client.kuuf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductsDB {

    Context ctx;
    DBHelper dbHelper;
    int id1;
    String productName;
    int minPlayer;
    int maxPlayer;
    int price;
    Double latitude;
    Double longitude;

    public ProductsDB(Context context){
        this.ctx = context;
        dbHelper = new DBHelper(ctx);
    }

    public boolean InsertProduct(Products new_product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.COLUMN_PRODUCTS_NAME, new_product.getProductName());
        cv.put(dbHelper.COLUMN_PRODUCTS_MINPLAYER, new_product.getMinPlayer());
        cv.put(dbHelper.COLUMN_PRODUCTS_MAXPLAYER, new_product.getMaxPlayer());
        cv.put(dbHelper.COLUMN_PRODUCTS_PRICE, new_product.getPrice());
        cv.put(dbHelper.COLUMN_PRODUCTS_LATITUDE, new_product.getLatitude());
        cv.put(dbHelper.COLUMN_PRODUCTS_LONGITUDE, new_product.getLongitude());

        long insert =  sqLiteDatabase.insert(dbHelper.TABLE_PRODUCTS, null, cv);

        return insert != -1;
    }

    public boolean checkIfEmpty(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_products_query = "SELECT * FROM " + dbHelper.TABLE_PRODUCTS;
        Cursor cursor = sqLiteDatabase.rawQuery(get_products_query, null);

        if(cursor.getCount() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Products> getAllProducts(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_products_query = "SELECT * FROM " + dbHelper.TABLE_PRODUCTS;
        Cursor cursor = sqLiteDatabase.rawQuery(get_products_query, null);

        ArrayList<Products> products = new ArrayList<Products>();

        if(cursor.moveToFirst() == true){
            do{
                //extract datanya
                String productName = cursor.getString(1);
                int minPlayer = cursor.getInt(2);
                int maxPlayer = cursor.getInt(3);
                int price = cursor.getInt(4);
                Double latitude = cursor.getDouble(5);
                Double longitude = cursor.getDouble(6);
                Log.i("TAG", "getAllProducts: " + price);

                products.add(new Products(productName, minPlayer, maxPlayer, price, latitude, longitude));

            }while(cursor.moveToNext());
        }
        return products;
    }

    public int getProductId(int productid){
        getProductCode(productid);
        return id1;
    }

    public String getProductName(int productid){
        getProductCode(productid);
        return productName;
    }

    public int getProductMin(int productid){
        getProductCode(productid);
        return minPlayer;
    }

    public int getProductMax(int productid){
        getProductCode(productid);
        return maxPlayer;
    }

    public int getProductPrice(int productid){
        getProductCode(productid);
        return price;
    }

    public Double getProductLat(int productid){
        getProductCode(productid);
        return latitude;
    }

    public Double getProductLong(int productid){
        getProductCode(productid);
        return longitude;
    }


    public void getProductCode(int productid){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_products_query = "SELECT * FROM " + dbHelper.TABLE_PRODUCTS + " WHERE "+ dbHelper.COLUMN_PRODUCTS_ID + "=" + productid;
        Cursor cursor = sqLiteDatabase.rawQuery(get_products_query, null);
        cursor.moveToFirst();

        Products products = new Products();

        id1 = cursor.getInt(0);
        productName = cursor.getString(1);
        minPlayer = cursor.getInt(2);
        maxPlayer = cursor.getInt(3);
        price = cursor.getInt(4);
        latitude = cursor.getDouble(5);
        longitude = cursor.getDouble(6);

        Log.i("check", "getUser: " + productName);

        cursor.close();
        sqLiteDatabase.close();
    }

}
