package com.client.kuuf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TransactionsDB {

    Context ctx;
    DBHelper dbHelper;
    int loggedId;

    public TransactionsDB(Context context){
        this.ctx = context;
        dbHelper = new DBHelper(ctx);
    }

    public boolean InsertTransaction(Transactions new_transaction) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.COLUMN_TRANSACTIONS_USERID, new_transaction.getUserID());
        cv.put(dbHelper.COLUMN_TRANSACTIONS_PRODUCTID, new_transaction.getProductID());
        cv.put(dbHelper.COLUMN_TRANSACTIONS_DATE, new_transaction.getDate());

        long insert =  sqLiteDatabase.insert(dbHelper.TABLE_TRANSACTIONS, null, cv);

        return insert != -1;
    }

    public ArrayList<Transactions> getAllTransactions(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_transactions_query = "SELECT * FROM " + dbHelper.TABLE_TRANSACTIONS;
        Cursor cursor = sqLiteDatabase.rawQuery(get_transactions_query, null);

        ArrayList<Transactions> transactions = new ArrayList<Transactions>();

        if(cursor.moveToFirst() == true){
            do{
                //extract datanya
                String transactionID = cursor.getString(0);
                String userID = cursor.getString(1);
                String productID = cursor.getString(2);
                String date = cursor.getString(3);

                transactions.add(new Transactions(userID, productID, date));

            }while(cursor.moveToNext());
        }
        return transactions;
    }

    public boolean checkIfEmpty(int userid){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_transactions_query = "SELECT * FROM " + dbHelper.TABLE_TRANSACTIONS + " WHERE userID = " + userid;
        Cursor cursor = sqLiteDatabase.rawQuery(get_transactions_query, null);

        if(cursor.getCount() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Transactions> getAllTransactionsUser(int userid){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_transactions_query = "SELECT * FROM " + dbHelper.TABLE_TRANSACTIONS + " WHERE userID = " + userid;
        Cursor cursor = sqLiteDatabase.rawQuery(get_transactions_query, null);

        ArrayList<Transactions> transactions = new ArrayList<Transactions>();

        if(cursor.moveToFirst() == true){
            do{
                //extract datanya
                String transactionID = cursor.getString(0);
                String userID = cursor.getString(1);
                String productID = cursor.getString(2);
                String date = cursor.getString(3);

                transactions.add(new Transactions(userID, productID, date));

            }while(cursor.moveToNext());
        }
        return transactions;
    }

    public int getTransactionId(int userid, int position){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_transactions_query = "SELECT * FROM " + dbHelper.TABLE_TRANSACTIONS + " WHERE userID = " + userid;
        Cursor cursor = sqLiteDatabase.rawQuery(get_transactions_query, null);
        int counter = 0;
        int transactionID = 0;

        if(cursor.moveToFirst() == true){
            do{
                //extract datanya
                counter++;
                transactionID = cursor.getInt(0);
                Log.i("TAG", "getTransactionId: " + transactionID + " counter : " + counter);

                cursor.moveToNext();
            }while(counter != position);
        }

        return transactionID;
    }

    public void deleteTransaction(int id){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + dbHelper.TABLE_TRANSACTIONS + " WHERE " + dbHelper.COLUMN_TRANSACTIONS_ID + " = " + (id));
    }


}
