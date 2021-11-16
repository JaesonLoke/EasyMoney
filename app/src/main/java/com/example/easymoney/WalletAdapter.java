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

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    private Context context;
    private ArrayList fid, title, amount, desc, walletType;

    WalletAdapter(Context context, ArrayList fid, ArrayList title, ArrayList amount, ArrayList desc, ArrayList walletType){
        this.context = context;
        this.fid = fid;
        this.title = title;
        this.amount = amount;
        this.desc = desc;
        this.walletType = walletType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.wallet_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //get String from ArrayList

        holder.title_text.setText(String.valueOf(title.get(position)));
        holder.amount_text.setText("RM" +  String.valueOf(amount.get(position)));
        holder.decs_text.setText(String.valueOf(desc.get(position)));

        String saveOrLoan = String.valueOf(walletType.get(position));
        if(saveOrLoan.equalsIgnoreCase("saving")){
            holder.amount_text.setTextColor(Color.GREEN);
        } else {
            holder.amount_text.setTextColor(Color.RED);
        }

        holder.WalletRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditWallet.class);
                intent.putExtra("id",String.valueOf(fid.get(position)));
                intent.putExtra("title",String.valueOf(title.get(position)));
                intent.putExtra("amount",String.valueOf(amount.get(position)));
                intent.putExtra("desc",String.valueOf(desc.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_text, amount_text, decs_text, walletType_text;
        LinearLayout WalletRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_text = itemView.findViewById(R.id.wallet_title);
            amount_text = itemView.findViewById(R.id.walletAmount_text);
            decs_text = itemView.findViewById(R.id.walletDecs);
            WalletRow = itemView.findViewById(R.id.walletRow);
        }
    }
}
