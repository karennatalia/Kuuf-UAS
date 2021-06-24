package com.client.kuuf;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context ctx;
    ArrayList<Transactions> transactionsList;
    DBHelper dbHelper;
    int loggedId;

    public HomeAdapter(Context ctx, ArrayList<Transactions> transactionsList, int loggedId) {
        this.transactionsList = transactionsList;
        this.ctx = ctx;
        this.loggedId = loggedId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.fragment_home_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Transactions transactions = transactionsList.get(position);
        final TransactionsDB transactionsDB = new TransactionsDB(ctx);
        ProductsDB productsDB = new ProductsDB(ctx);
        holder.txtName.setText(productsDB.getProductName(Integer.parseInt(transactions.getProductID())));
        holder.txtDate.setText(transactions.getDate());
        holder.txtPrice.setText(""  + productsDB.getProductPrice(Integer.parseInt(transactions.getProductID())));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: klikk " + position);
                transactionsDB.deleteTransaction(transactionsDB.getTransactionId(loggedId, position + 1));
                Intent intent = new Intent(ctx, HomeActivity.class);
                intent.putExtra("loggedId", loggedId);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDate, txtPrice;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = (TextView) itemView.findViewById(R.id.txtTransactionDate);
            txtName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            btnDelete = (Button)  itemView.findViewById(R.id.btnDeleteProduct);
        }

    }
}
