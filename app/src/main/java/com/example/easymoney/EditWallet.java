package com.example.easymoney;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditWallet extends AppCompatActivity {

    EditText titleInput, descInput, amountInput;
    Button UpdateButton, DeleteButton;
    String id, title, desc, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wallet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleInput = findViewById(R.id.walletTitleEdit);
        descInput = findViewById(R.id.DecsEdit);
        amountInput = findViewById(R.id.amountWalletEdit);
        UpdateButton = findViewById(R.id.updateWalletButton);
        DeleteButton = findViewById(R.id.deleteWalletButton);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                DBHelperWallet myDB = new DBHelperWallet(EditWallet.this);

                title = titleInput.getText().toString();
                desc = descInput.getText().toString();
                amount = amountInput.getText().toString();

                myDB.updateData(id, title, amount, desc);
                finish();
            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
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

    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("amount") && getIntent().hasExtra("desc")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            desc = getIntent().getStringExtra("desc");
            amount = getIntent().getStringExtra("amount");

            titleInput.setText(title);
            descInput.setText(desc);
            amountInput.setText(amount);

        }else{
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + title + "?");
        builder.setMessage("Are you sure you want to delete this?");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelperWallet myDB = new DBHelperWallet(EditWallet.this);
                myDB.deleteData(id);
                finish();
            }
        });

        builder.create().show();
    }
}