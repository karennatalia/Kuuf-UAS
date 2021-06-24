package com.client.kuuf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {

    TextView productName, minPlayer, maxPlayer, price;
    Button btn_buy, btn_showlocation;
    String nama;
    int min, max, harga;
    Double latitude, longitude;
    int loggedId, position;
    UsersDB usersDB = new UsersDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        final TransactionsDB transactionsDB = new TransactionsDB(this);

        Intent intent = getIntent();
        productName = findViewById(R.id.DTprodukNameTV);
        minPlayer = findViewById(R.id.DTprodukMinTV);
        maxPlayer = findViewById(R.id.DTprodukMaxTV);
        price = findViewById(R.id.DTprodukHargaTV);
        btn_buy = findViewById(R.id.btn_buy);
        btn_showlocation = findViewById(R.id.btn_showmap);

        nama = intent.getStringExtra("nama");
        min = intent.getIntExtra("min",0);
        max = intent.getIntExtra("max",0);
        harga = intent.getIntExtra("harga", 0);
        latitude = intent.getDoubleExtra("lat", 0);
        longitude = intent.getDoubleExtra("long", 0);
        loggedId = intent.getIntExtra("loggedId", 0);
        position = intent.getIntExtra("position", 0);
        Log.i("TAG", "onCreate: logged" + loggedId);

        productName.setText(nama);
        minPlayer.setText("Min Player: " + min + " Player");
        maxPlayer.setText("Max Player: " + max + " Player");
        price.setText("Rp. " + harga);

        final SmsManager smsManager = SmsManager.getDefault();
        checkPermission();


        btn_buy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(harga > usersDB.getUserWallet(loggedId)){
                    Toast.makeText(ProductDetailActivity.this, "You don't have enough money in wallet", Toast.LENGTH_SHORT).show();
                }
                else{
                    UsersDB usersDB = new UsersDB(ProductDetailActivity.this);
                    Transactions transactions = new Transactions();
                    transactions.setUserID("" + loggedId);
                    transactions.setProductID("" + position);
                    transactions.setDate("" + java.time.LocalDate.now());
                    Log.i("TAG", "onClick: " + java.time.LocalDate.now());
                    transactionsDB.InsertTransaction(transactions);

                    Log.i("TAG", "onClick: " + usersDB.getUserPhone(loggedId));


                    smsManager.sendTextMessage(usersDB.getUserPhone(loggedId), null, "Transaction is successful!", null, null);

                    try {
                        Thread.sleep(1000);
                    }
                    catch (Exception e) {

                    }

                    finish();

                    Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                    intent.putExtra("loggedId", loggedId);
                    startActivity(intent);
                }

            }
        });

        btn_showlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDetailActivity.this, MapActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

    }

    public void checkPermission(){
        int sendPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if( sendPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},1);
        }
        int recPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if( recPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS},1);
        }
        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if( readPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS},1);
        }
    }

}