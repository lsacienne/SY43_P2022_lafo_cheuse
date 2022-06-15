package com.sy43.savecur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_layout);
        Button confirmAddExpenseBtn = (Button) findViewById(R.id.confirmExpenseAddBtn);
        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);

        Bundle extras = getIntent().getExtras();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, extras.getStringArrayList("categories"));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);

        Intent intent = new Intent(this, HomeActivity.class);

        confirmAddExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((TextInputEditText) findViewById(R.id.expenseName)).getText().toString();
                int amount = Integer.parseInt(((TextInputEditText) findViewById(R.id.expenseName)).getText().toString());

                intent.putExtra("name", name);
                intent.putExtra("amount", amount);
                startActivity(intent);
            }
        });
    }
}