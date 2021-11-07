package com.example.easymoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class addRecord extends AppCompatActivity {

    EditText category_input, amount_input, date_input;
    Button add_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);

        category_input = findViewById(R.id.categoryName);
        amount_input = findViewById(R.id.amount);
        amount_input.setFilters(new InputFilter[] {new DecimalFilter(5,2)});
        amount_input.setRawInputType(Configuration.KEYBOARD_12KEY);
        date_input = findViewById(R.id.categoryDate);
        add_button = findViewById(R.id.addButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper financeDB = new DBHelper(addRecord.this);
                financeDB.addFinance(category_input.getText().toString().trim(),
                        Float.parseFloat(amount_input.getText().toString()),
                        date_input.getText().toString().trim());
            }
        });
    }

}
