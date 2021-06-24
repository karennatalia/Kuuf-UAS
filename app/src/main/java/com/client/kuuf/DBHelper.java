package com.client.kuuf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Kuuf.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "userID";
    public static final String COLUMN_USERS_USERNAME = "username";
    public static final String COLUMN_USERS_PASSWORD = "password";
    public static final String COLUMN_USERS_PHONE = "phone";
    public static final String COLUMN_USERS_GENDER = "gender";
    public static final String COLUMN_USERS_WALLET = "wallet";
    public static final String COLUMN_USERS_DOB = "dob";

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTIONS_ID = "transactionID";
    public static final String COLUMN_TRANSACTIONS_USERID = "userID";
    public static final String COLUMN_TRANSACTIONS_PRODUCTID = "productID";
    public static final String COLUMN_TRANSACTIONS_DATE = "date";

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_PRODUCTS_ID = "productID";
    public static final String COLUMN_PRODUCTS_NAME = "productName";
    public static final String COLUMN_PRODUCTS_MINPLAYER = "minPlayer";
    public static final String COLUMN_PRODUCTS_MAXPLAYER = "maxPlayer";
    public static final String COLUMN_PRODUCTS_PRICE = "price";
    public static final String COLUMN_PRODUCTS_LATITUDE = "latitude";
    public static final String COLUMN_PRODUCTS_LONGITUDE = "longitude";

    String create_table_users = "CREATE TABLE " + TABLE_USERS + "(" +
            COLUMN_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERS_USERNAME + " TEXT NOT NULL, " +
            COLUMN_USERS_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_USERS_PHONE + " TEXT NOT NULL, " +
            COLUMN_USERS_GENDER + " TEXT NOT NULL, " +
            COLUMN_USERS_WALLET + " INTEGER, " +
            COLUMN_USERS_DOB + " DATE NOT NULL);";

    String create_table_transactions = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" +
            COLUMN_TRANSACTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TRANSACTIONS_USERID + " INTEGER NOT NULL, " +
            COLUMN_TRANSACTIONS_PRODUCTID + " INTEGER NOT NULL, " +
            COLUMN_TRANSACTIONS_DATE + " DATE NOT NULL);";

    String create_table_products = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
            COLUMN_PRODUCTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PRODUCTS_NAME + " TEXT NOT NULL, " +
            COLUMN_PRODUCTS_MINPLAYER + " INTEGER NOT NULL, " +
            COLUMN_PRODUCTS_MAXPLAYER + " INTEGER NOT NULL, " +
            COLUMN_PRODUCTS_PRICE + " INTEGER NOT NULL, " +
            COLUMN_PRODUCTS_LATITUDE + " REAL NOT NULL, " +
            COLUMN_PRODUCTS_LONGITUDE + " REAL NOT NULL);";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_table_users);
        sqLiteDatabase.execSQL(create_table_transactions);
        sqLiteDatabase.execSQL(create_table_products);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

        onCreate(sqLiteDatabase);
    }
}
