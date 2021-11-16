package com.example.easymoney;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WalletFragment extends AppCompatActivity {

    TextView sum, SText, LText;
    RecyclerView recyclerView;
    DBHelperWallet walletDB;
    ArrayList<String> fid, title, amount, desc, walletType;
    WalletAdapter mAdapter;
    FloatingActionButton addWalletB;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Accounts");
        }

        addWalletB = (FloatingActionButton) findViewById(R.id.addWalletBtn);

        addWalletB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWallet();
            }
        });

        sum = findViewById(R.id.text_walletSum);
        recyclerView = findViewById(R.id.recyclerViewWallet);

        updateUI();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tutorialBtn:
                openT();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void openT(){
        Intent intent = new Intent(this, tutorialActivity.class);
        startActivity(intent);
    }

  @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    void updateUI(){
        DBHelperWallet helper = new DBHelperWallet(this);
        walletDB = new DBHelperWallet(this);
        fid = new ArrayList<>();
        title = new ArrayList<>();
        amount = new ArrayList<>();
        desc = new ArrayList<>();
        walletType = new ArrayList<>();

        storeWalletData();

        mAdapter = new WalletAdapter(this, fid, title, amount, desc, walletType);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sum.setText("RM" + helper.getSum());


        if (helper.getSum().contains("-")){
            sum.setTextColor(Color.RED);
            String r = helper.getSum().replaceFirst("-","");
            sum.setText("-RM" + r);
        }else{
            sum.setText("RM" + helper.getSum());
            sum.setTextColor(Color.BLACK);
        }

        if (helper.getSum() == null){
            sum.setText("RM0");
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void storeWalletData(){
        Cursor cursor = walletDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "There is no data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                fid.add(cursor.getString(0));
                title.add(cursor.getString(1));
                amount.add(cursor.getString(2));
                desc.add(cursor.getString(3));
                walletType.add(cursor.getString(4));
            }
        }
    }

    void openWallet(){
        Intent intent = new Intent( this, addWallet.class);
        startActivity(intent);
    }
}