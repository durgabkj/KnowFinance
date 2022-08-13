package com.ottego.knowfinance.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import com.ottego.knowfinance.R;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ottego.knowfinance.Model.PricingModel;

public class PricingAdapter extends RecyclerView.Adapter<PricingAdapter.ViewHolder>{

    private PricingModel[] listdata;

        // RecyclerView recyclerView;
        public PricingAdapter(PricingModel[] listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_pricing, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final PricingModel myListData = listdata[position];
            holder.textView.setText(listdata[position].getDescription());
            holder.imageView.setImageResource(listdata[position].getImgId());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(), Toast.LENGTH_LONG).show();
                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.length;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;
            public RelativeLayout relativeLayout;
            public ViewHolder(View itemView) {
                super(itemView);
                this.textView = (TextView) itemView.findViewById(R.id.textView);

        }
    }
}
