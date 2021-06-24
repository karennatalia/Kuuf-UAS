package com.client.kuuf;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder>{

    Context ctx;
    ArrayList<Products> productsList;
    DBHelper dbHelper;
    int loggedId;

    public StoreAdapter(Context ctx, ArrayList<Products> productsList, int loggedId) {
        this.productsList = productsList;
        this.ctx = ctx;
        this.loggedId = loggedId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Products products = productsList.get(position);
        holder.produkNama.setText(products.getProductName());
        holder.produkMin.setText(products.getMinPlayer() + " - ");
        holder.produkMax.setText(products.getMaxPlayer() + " Player");
        holder.produkHarga.setText("Rp." + products.getPrice() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsDB productsDB = new ProductsDB(ctx);

                Intent intent = new Intent(ctx, ProductDetailActivity.class);
                intent.putExtra("nama", productsDB.getProductName(position + 1));
                intent.putExtra("min", productsDB.getProductMin(position + 1));
                intent.putExtra("max", productsDB.getProductMax(position + 1));
                intent.putExtra("harga", productsDB.getProductPrice(position + 1));
                intent.putExtra("lat", productsDB.getProductLat(position + 1));
                intent.putExtra("long", productsDB.getProductLong(position + 1));
                intent.putExtra("loggedId", loggedId);
                intent.putExtra("position", position + 1);
                Log.i("TAG", "onClick: LOGGED ID " + loggedId);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView produkNama, produkMin, produkMax, produkHarga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            produkNama = itemView.findViewById(R.id.tv_produkName);
            produkMin = itemView.findViewById(R.id.tv_produkMin);
            produkMax = itemView.findViewById(R.id.tv_produkMax);
            produkHarga = itemView.findViewById(R.id.tv_produkPrice);
        }
    }

}
