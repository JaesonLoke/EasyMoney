package com.example.easymoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;
    private ArrayList fid, category, amount, date;

    MainAdapter(Context context, ArrayList fid, ArrayList category, ArrayList amount, ArrayList date){
        this.context = context;
        this.fid = fid;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.finance_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //get String from ArrayList
        holder.fid_text.setText(String.valueOf(fid.get(position)));
        holder.category_text.setText(String.valueOf(category.get(position)));
        holder.amount_text.setText(String.valueOf(amount.get(position)));
        holder.date_text.setText(String.valueOf(date.get(position)));
    }

    @Override
    public int getItemCount() {
        return fid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fid_text, category_text, amount_text, date_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fid_text = itemView.findViewById(R.id.fid_text);
            category_text = itemView.findViewById(R.id.category_text);
            amount_text = itemView.findViewById(R.id.amount_text);
            date_text = itemView.findViewById(R.id.date_text);
        }
    }
}
