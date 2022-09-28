package com.ottego.knowfinance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ottego.knowfinance.Model.BackTestTradeListModel;
import com.ottego.knowfinance.Model.StockDetailsModel;
import com.ottego.knowfinance.R;

import java.util.List;

public class BackTestAdapter extends RecyclerView.Adapter<BackTestAdapter.MyViewHolder> {
    @NonNull

    List<BackTestTradeListModel> list;
    Context context;

    public BackTestAdapter(@NonNull List<BackTestTradeListModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BackTestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_backtest, parent, false);
        return new BackTestAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BackTestAdapter.MyViewHolder holder, int i) {


        BackTestTradeListModel  model = list.get(i);
        holder.tvBackTestSno.setText(String.valueOf(i + 1));
        holder.tvSTOCK_NAME.setText(model.stock_name);
        holder.tvBackTestBuySell.setText(model.trade_status);
        holder.tvTradeDate.setText(model.date);

        holder.tvBackTestPrice.setText(model.ha_high);
        holder.tvBackTestQuantity.setText(model.qty);
        holder.tvBackTestPoints.setText(model.points);
        holder.tvBackTestProfitLoss.setText(model.pnl);
        holder.tvBackTestNetProfitLoss.setText(model.net_profit);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSTOCK_NAME,tvBackTestSno, tvBackTestBuySell,tvTradeDate,tvBackTestPrice,tvBackTestQuantity,tvBackTestPoints,tvBackTestProfitLoss,tvBackTestNetProfitLoss;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBackTestSno=itemView.findViewById(R.id.tvBackTestSno);
            tvSTOCK_NAME = itemView.findViewById(R.id.tvSTOCK_NAME);
            tvBackTestBuySell = itemView.findViewById(R.id.tvBackTestBuySell);
            tvTradeDate = itemView.findViewById(R.id.tvTradeDate);
            tvBackTestPrice = itemView.findViewById(R.id.tvBackTestPrice);
            tvBackTestQuantity = itemView.findViewById(R.id.tvBackTestQuantity);
            tvBackTestPoints = itemView.findViewById(R.id.tvBackTestPoints);
            tvBackTestProfitLoss = itemView.findViewById(R.id.tvBackTestProfitLoss);
            tvBackTestNetProfitLoss = itemView.findViewById(R.id.tvBackTestNetProfitLoss);


        }
    }
}
