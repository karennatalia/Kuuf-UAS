package com.client.kuuf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {

    ArrayList<Products> products;
    SQLiteDatabase db;
    RecyclerView rv_store;
    StoreAdapter storeAdapter;
    RequestQueue requestQueue;
    int loggedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        loggedId = getIntent().getIntExtra("loggedId", 0);

        final ProductsDB productsDB = new ProductsDB(StoreActivity.this);

        products = new ArrayList<>();
        rv_store = findViewById(R.id.rv_store);

        if(productsDB.checkIfEmpty() == true){
            Log.e("err", "onErrorResponse: masukkosong");
            requestQueue = Volley.newRequestQueue(this);
            String url = "https://api.jsonbin.io/b/5eb51c6947a2266b1474d701/7";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("items");
                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Products products = new Products();
                            products.setProductName(jsonObject.getString("name"));
                            products.setMinPlayer(jsonObject.getInt("min_player"));
                            products.setMaxPlayer(jsonObject.getInt("max_player"));
                            products.setPrice(jsonObject.getInt("price"));
                            products.setLatitude(jsonObject.getDouble("latitude"));
                            products.setLongitude(jsonObject.getDouble("longitude"));

                            productsDB.InsertProduct(products);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("err", "onErrorResponse: error");

                }
            });

            requestQueue.add(jsonObjectRequest);

            Intent intent = new Intent(StoreActivity.this, StoreActivity.class);
            startActivity(intent);
            finish();
        }

        else{
            Log.i("TAG", "onCreate: gamasuk");

        }

        products = productsDB.getAllProducts();

        LinearLayoutManager llManager = new LinearLayoutManager(this);
        rv_store.setLayoutManager(llManager);
        storeAdapter = new StoreAdapter(StoreActivity.this, products, loggedId);
        rv_store.setAdapter(storeAdapter);

    }
}