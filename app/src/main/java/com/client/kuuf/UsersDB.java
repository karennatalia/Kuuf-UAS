package com.client.kuuf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UsersDB {

    Context ctx;
    DBHelper dbHelper;
    int loginID;
    Integer id1;
    String username1;
    String password1;
    String phone1;
    String gender1;
    Integer wallet1;
    String dob1;


    public UsersDB(Context context){
        this.ctx = context;
        dbHelper = new DBHelper(ctx);
    }

    public int getLoginID(){
        return loginID;
    }

    public void setLoginID(int loginID){
        this.loginID = loginID;
    }

    public boolean InsertUser(Users new_user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.COLUMN_USERS_USERNAME, new_user.getUsername());
        cv.put(dbHelper.COLUMN_USERS_PASSWORD, new_user.getPassword());
        cv.put(dbHelper.COLUMN_USERS_PHONE, new_user.getPhone());
        cv.put(dbHelper.COLUMN_USERS_GENDER, new_user.getGender());
        cv.put(dbHelper.COLUMN_USERS_WALLET, new_user.getWallet());
        cv.put(dbHelper.COLUMN_USERS_DOB, new_user.getDob());

        long insert =  sqLiteDatabase.insert(dbHelper.TABLE_USERS, null, cv);

        return insert != -1;
    }

    public List<String> getAllUsers(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_users_query = "SELECT * FROM " + dbHelper.TABLE_USERS;
        Cursor cursor = sqLiteDatabase.rawQuery(get_users_query, null);

        List<String> users = new ArrayList<String>();

        if(cursor.moveToFirst() == true){
            do{
                //extract datanya
                Integer id = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String phone = cursor.getString(3);
                String gender = cursor.getString(4);
                Integer wallet = cursor.getInt(5);
                String dob = cursor.getString(6);

                String compact = id + ", " + username + ", " + password + ", " + phone + ", " +
                        gender + ", " + wallet + ", " + dob;
                users.add(compact);

            }while(cursor.moveToNext());
        }
        return users;
    }

    public int getUserId(int userid){
        getUserCode(userid);
        return id1;
    }

    public String getUserUsername(int userid){
        getUserCode(userid);
        return username1;
    }

    public String getUserPassword(int userid){
        getUserCode(userid);
        return password1;
    }

    public String getUserPhone(int userid){
        getUserCode(userid);
        return phone1;
    }

    public String getUserGender(int userid){
        getUserCode(userid);
        return gender1;
    }

    public int getUserWallet(int userid){
        getUserCode(userid);
        return wallet1;
    }

    public String getUserDob(int userid){
        getUserCode(userid);
        return dob1;
    }

    public boolean UpdateUser(Users new_user, int nominal, int loggedID) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE "+ dbHelper.TABLE_USERS +" SET wallet = wallet + " + nominal + " WHERE userID = " + loggedID);
        sqLiteDatabase.close();
        return true;
    }

    public void getUserCode(int userid){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_users_query = "SELECT * FROM " + dbHelper.TABLE_USERS + " WHERE "+ dbHelper.COLUMN_USERS_ID + "=" + userid;
        Cursor cursor = sqLiteDatabase.rawQuery(get_users_query, null);
        cursor.moveToFirst();

        Users user = new Users();

        id1 = cursor.getInt(0);
        Log.i("TAG", "getUserCode: " + id1);
        username1 = cursor.getString(1);
        password1 = cursor.getString(2);
        phone1 = cursor.getString(3);
        gender1 = cursor.getString(4);
        wallet1 = cursor.getInt(5);
        dob1 = cursor.getString(6);

        Log.i("check", "getUser: " + username1);

        cursor.close();
        sqLiteDatabase.close();
    }

    public boolean checkUser(String check_username, String check_password){

        int countID = 0;

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_users_query = "SELECT * FROM " + dbHelper.TABLE_USERS;
        Cursor cursor = sqLiteDatabase.rawQuery(get_users_query, null);

        List<String> users = new ArrayList<String>();

        if(cursor.moveToFirst() == true){
            do{
                countID++;
                //extract datanya
                String username = cursor.getString(1);
                String password = cursor.getString(2);

                if(username.equals(check_username) && password.equals(check_password)){
                    loginID = countID;
                    countID = 0;
                    return true;
                }

            }while(cursor.moveToNext());
        }

        return false;
    }

    public int checkUserID(String check_username, String check_password){

        int countID = 0;

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String get_users_query = "SELECT * FROM " + dbHelper.TABLE_USERS;
        Cursor cursor = sqLiteDatabase.rawQuery(get_users_query, null);

        List<String> users = new ArrayList<String>();

        if(cursor.moveToFirst() == true){
            do{
                countID++;
                //extract datanya
                String username = cursor.getString(1);
                String password = cursor.getString(2);

                if(username.equals(check_username) && password.equals(check_password)){
                    loginID = countID;
                    countID = 0;
                    return loginID;
                }

            }while(cursor.moveToNext());
        }

        return loginID;
    }

}
