package com.ottego.knowfinance.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ottego.knowfinance.Model.StockDetailsModel;
import com.ottego.knowfinance.Model.TradeListModel;
import com.ottego.knowfinance.R;

import java.util.List;

public class TradeListAdapter extends RecyclerView.Adapter<TradeListAdapter.MyViewHolder> {

        List<TradeListModel> list;
        Context context;

        public TradeListAdapter(Context context, List<TradeListModel> list) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public TradeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tradeing_status, parent, false);
            return new TradeListAdapter.MyViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull TradeListAdapter.MyViewHolder holder, int i) {
            final TradeListModel model = list.get(i);

            holder.tvTradeSno.setText(String.valueOf(i + 1));
            holder.tvTradeDateTime.setText(model.datetime);
            holder.tvTradeDateType.setText(model.type);
            holder.tvSTOCK_NAME.setText(model.STOCK_NAME);
            holder.tvLOTS.setText(model.LOTS);
            holder.tvStatus.setText(model.status);

            holder.tvStatus.setTextColor(Color.parseColor("#4caf50"));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTradeSno, tvTradeDateTime, tvTradeDateType, tvSTOCK_NAME, tvLOTS,tvStatus;
            ImageView tvDelete;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTradeSno = itemView.findViewById(R.id.tvTradeSno);
                tvTradeDateTime = itemView.findViewById(R.id.tvTradeDateTime);
                tvTradeDateType = itemView.findViewById(R.id.tvTradeDateType);
                tvSTOCK_NAME = itemView.findViewById(R.id.tvSTOCK_NAME);
                tvLOTS = itemView.findViewById(R.id.tvLOTS);
                tvStatus=itemView.findViewById(R.id.tvStatus);

            }
        }
    }


