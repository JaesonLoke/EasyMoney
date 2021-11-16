package com.example.easymoney;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class addWallet extends AppCompatActivity {

    EditText title_input, amount_input, desc_input;
    Button add_saving, add_loan;
    String walletType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wallet_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title_input = findViewById(R.id.titleWallet);
        amount_input = findViewById(R.id.amountWallet);
        amount_input.setFilters(new InputFilter[]{new DecimalFilter(8, 2)});
        amount_input.setRawInputType(Configuration.KEYBOARD_12KEY);
        desc_input = findViewById(R.id.descAdd);

        add_saving = findViewById(R.id.addToSaving);
        add_saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelperWallet walletDB = new DBHelperWallet(addWallet.this);
                walletType = "saving";
                walletDB.addWallet(title_input.getText().toString().trim(),
                        Float.parseFloat(amount_input.getText().toString()),
                        desc_input.getText().toString().trim(),
                        walletType);
                finish();
            }
        });

        add_loan = findViewById(R.id.addToLoan);
        add_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelperWallet walletDB = new DBHelperWallet(addWallet.this);
                walletType = "loan";
                walletDB.addWallet(title_input.getText().toString().trim(),
                        Float.parseFloat(amount_input.getText().toString()),
                        desc_input.getText().toString().trim(),
                        walletType);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return false;
    }
}
