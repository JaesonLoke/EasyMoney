package com.example.easymoney;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;
    private ArrayList fid, category, amount, date, amountType;

    MainAdapter(Context context, ArrayList fid, ArrayList category, ArrayList amount, ArrayList date, ArrayList amountType){
        this.context = context;
        this.fid = fid;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.amountType = amountType;
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

        holder.category_text.setText(String.valueOf(category.get(position)));
        holder.amount_text.setText("RM" +  String.valueOf(amount.get(position)));
        holder.date_text.setText(String.valueOf(date.get(position)));
        holder.amountType_text.setText(String.valueOf(amountType.get(position)));

        String incomeOrExpense = String.valueOf(amountType.get(position));
        if(incomeOrExpense.equalsIgnoreCase("income")){
            holder.amount_text.setTextColor(Color.GREEN);
        } else {
            holder.amount_text.setTextColor(Color.RED);
        }

        holder.FinanceRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditRecord.class);
                intent.putExtra("id",String.valueOf(fid.get(position)));
                intent.putExtra("title",String.valueOf(category.get(position)));
                intent.putExtra("amount",String.valueOf(amount.get(position)));
                intent.putExtra("date",String.valueOf(date.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category_text, amount_text, date_text, amountType_text;
        LinearLayout FinanceRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category_text = itemView.findViewById(R.id.category_text);
            amount_text = itemView.findViewById(R.id.amount_text);
            date_text = itemView.findViewById(R.id.date_text);
            amountType_text = itemView.findViewById(R.id.amountType_text);
            FinanceRow = itemView.findViewById(R.id.financeRow);
        }
    }
}
