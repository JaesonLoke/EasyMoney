package com.example.easymoney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditRecord extends AppCompatActivity {

    EditText titleInput, dateInput, amountInput;
    Button UpdateButton, DeleteButton;
    String id, title, date, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        titleInput = findViewById(R.id.categoryNameEdit);
        dateInput = findViewById(R.id.categoryDateEdit);
        amountInput = findViewById(R.id.amountEdit);
        UpdateButton = findViewById(R.id.updateButton);
        DeleteButton = findViewById(R.id.deleteButton);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(EditRecord.this);

                title = titleInput.getText().toString();
                date = dateInput.getText().toString();
                amount = amountInput.getText().toString();

                myDB.updateData(id, title, amount, date);
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

    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("amount") && getIntent().hasExtra("date")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            date = getIntent().getStringExtra("date");
            amount = getIntent().getStringExtra("amount");

            titleInput.setText(title);
            dateInput.setText(date);
            amountInput.setText(amount);

        }else{
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete this?");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(EditRecord.this);
                myDB.deleteData(id);
                finish();
            }
        });

        builder.create().show();
    }
}