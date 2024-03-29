package com.example.easymoney;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecordList extends Fragment {

    RecyclerView recyclerView;

    DBHelper financeDB;
    ArrayList<String> fid, category, amount, date, amountType;
    MainAdapter mAdapter;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        updateUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void updateUI(){
        financeDB = new DBHelper(getActivity());
        fid = new ArrayList<>();
        category = new ArrayList<>();
        amount = new ArrayList<>();
        date = new ArrayList<>();
        amountType = new ArrayList<>();

        storeFinanceData();

        mAdapter = new MainAdapter(getActivity(), fid, category, amount, date, amountType);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void storeFinanceData(){
        Cursor cursor = financeDB.readThisMonthData();
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(), "There is no data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                fid.add(cursor.getString(0));
                category.add(cursor.getString(1));
                amount.add(cursor.getString(2));
                date.add(cursor.getString(3));
                amountType.add(cursor.getString(4));
            }
        }
    }
}