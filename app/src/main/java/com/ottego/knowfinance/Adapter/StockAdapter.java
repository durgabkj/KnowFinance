package com.ottego.knowfinance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ottego.knowfinance.ApiListener;
import com.ottego.knowfinance.Model.StockDetailsModel;
import com.ottego.knowfinance.R;
import com.ottego.knowfinance.Utils;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder> {
    List<StockDetailsModel> list;
    Context context;
    ApiListener clickListener;

    public StockAdapter(Context context, List<StockDetailsModel> list, ApiListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_stock, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final StockDetailsModel model = list.get(i);

        holder.tvSno.setText(model.id);
        holder.tvModule.setText(model.module);
        holder.tvType.setText(model.type);
        holder.tvStock.setText(model.stock);
        holder.tvQuantity.setText(model.quantity);

        //Delete function from utils

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.deleteStock(context, model.id, clickListener);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSno, tvModule, tvType, tvStock, tvQuantity;
        ImageView tvDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSno = itemView.findViewById(R.id.tvSno);
            tvModule = itemView.findViewById(R.id.tvModule);
            tvType = itemView.findViewById(R.id.tvType);
            tvStock = itemView.findViewById(R.id.tvStock);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDelete = itemView.findViewById(R.id.tvDelete);

        }
    }
}
