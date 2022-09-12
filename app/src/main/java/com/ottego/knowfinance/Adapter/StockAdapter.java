package com.ottego.knowfinance.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ottego.knowfinance.AddStockInfoActivity;
import com.ottego.knowfinance.ApiListener;
import com.ottego.knowfinance.Model.StockDetailsModel;
import com.ottego.knowfinance.R;
import com.ottego.knowfinance.ShowDeleteButtonListener;
import com.ottego.knowfinance.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder> {
    List<StockDetailsModel> list;
    Context context;
    ApiListener clickListener;
    ShowDeleteButtonListener listener;
    StockDetailsModel model = null;

    public StockAdapter(Context context, List<StockDetailsModel> list, ApiListener clickListener, ShowDeleteButtonListener listener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_stock, parent, false);
        return new MyViewHolder(view);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
       model = list.get(i);
        holder.tvSno.setText(String.valueOf(i + 1));
        holder.tvModule.setText(model.module);
        holder.tvType.setText(model.type);
        holder.tvStock.setText(model.stock);
        holder.tvQuantity.setText(model.quantity);

        //Delete function from utils
        if (model.isSelected) {
            holder.cvStockList.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.side_border_green));
            holder.tvDelete.setVisibility(View.INVISIBLE);
        } else {
            holder.tvDelete.setVisibility(View.VISIBLE);
           // holder.llDataShow.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
            holder.cvStockList.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
        }

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });

        //multiple selection item
        holder.llDataShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.isSelected) {
                    model.isSelected = false;
                    holder.cvStockList.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
                    holder.tvDelete.setVisibility(View.VISIBLE);
//                    if (getSelectedData().size() == 0) {
//                        listener.onShowAction(false);
//                    }
                    Log.e("selected", String.valueOf(getSelectedData().size()));

                } else {
                    holder.cvStockList.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.side_border_green));
                    holder.tvDelete.setVisibility(View.INVISIBLE);
                    model.isSelected = true;
                    listener.onShowAction(true);


//                    Set s2 = new HashSet();
//
//                        List<String> ids = new ArrayList<>();    //  i.add(null);
//                        for (int j = 0; j <getSelectedData().size(); j++) {
//                            ids.add(String.valueOf(j));
//                            s2.add(String.valueOf(j));
//
//                        }
//                    String result_state = String.join(",", s2);
//                    Log.e("result_state", String.valueOf(result_state));

                }
            }
        });




    }

    private void setDialog() {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setContentView(R.layout.layout_confirmation_dialog);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.white_bg);

        ImageView btnClose = dialog.findViewById(R.id.btn_close);
        TextView btnCancel = dialog.findViewById(R.id.btn_no);
        TextView btnOk = dialog.findViewById(R.id.btn_yes);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.deleteStock(context, model.id, clickListener);
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<StockDetailsModel> getSelectedData() {
        List<StockDetailsModel> selectedData = new ArrayList<>();
        for (StockDetailsModel model : list) {
            if (model.isSelected) {
                selectedData.add(model);
            }
        }
        return selectedData;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSno, tvModule, tvType, tvStock, tvQuantity;
        ImageView tvDelete;
        CardView cvStockList;
        LinearLayout llAddStockList,llDataShow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSno = itemView.findViewById(R.id.tvSno);
            tvModule = itemView.findViewById(R.id.tvModule);
            tvType = itemView.findViewById(R.id.tvType);
            tvStock = itemView.findViewById(R.id.tvStock);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            llAddStockList = itemView.findViewById(R.id.llAddStockList);
            llDataShow = itemView.findViewById(R.id.llDataShow);
            cvStockList = itemView.findViewById(R.id.cvStockList);

        }
    }
}
