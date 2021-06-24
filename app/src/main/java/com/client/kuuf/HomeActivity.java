package com.client.kuuf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Transactions> transactions = new ArrayList<>();;
    private RecyclerView recview;
    int loggedID;
    TransactionsDB transactionsDB = new TransactionsDB(this);
    TextView tv_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_form);

        loggedID = getIntent().getIntExtra("loggedId", 0);
        Log.i("userID", "onCreate: AT HOME " );

        tv_empty = findViewById(R.id.tv_empty);

        if (transactionsDB.checkIfEmpty(loggedID) == true){
            Log.i("userID", "onCreate: AT HOME kosong " );
            tv_empty.setText("There is no transaction");
        }
        else if(transactionsDB.checkIfEmpty(loggedID) == false){

            tv_empty.setText("");
            transactions = transactionsDB.getAllTransactionsUser(loggedID);
            Log.i("userID", "onCreate: AT HOME isi "  + loggedID);

            recview = findViewById(R.id.RecViewResList);
            LinearLayoutManager lm = new LinearLayoutManager(this);
            recview.setLayoutManager(lm);
            HomeAdapter adp = new HomeAdapter(HomeActivity.this, transactions, loggedID);
            recview.setAdapter(adp);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home :
                return true;

            case R.id.store :
                Intent intent1 = new Intent(this, StoreActivity.class);
                intent1.putExtra("loggedId", loggedID);
                startActivity(intent1);
                return true;

            case R.id.profile :
                Intent intent2 = new Intent(this, ProfileActivity.class);
                intent2.putExtra("loggedId", loggedID);
                startActivity(intent2);
                return true;

            case R.id.logout :
                Intent intent3 = new Intent(this, LoginActivity.class);
                startActivity(intent3);
                finish();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
}