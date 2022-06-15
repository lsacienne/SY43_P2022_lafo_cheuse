package com.sy43.savecur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class CategoryEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        Bundle extras = getIntent().getExtras();

        TextInputEditText nameTextField = (TextInputEditText) findViewById(R.id.categoryName);
        TextInputEditText moneySpentTextField = (TextInputEditText) findViewById(R.id.categoryMoney);
        TextInputEditText moneyLimitTextField = (TextInputEditText) findViewById(R.id.categoryMoneyLimit);

        nameTextField.setText(extras.getString("name"));
        moneySpentTextField.setText(String.valueOf(extras.getInt("moneySpent")));
        moneyLimitTextField.setText(String.valueOf(extras.getInt("moneyLimit")));

        Button applyChangesBtn = (Button) findViewById(R.id.applyCategoryChanges);
        Intent intent = new Intent(this, HomeActivity.class);
        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("name", nameTextField.getText().toString());
                intent.putExtra("moneySpent", Integer.parseInt(moneySpentTextField.getText().toString()));
                intent.putExtra("moneyLimit", Integer.parseInt(moneyLimitTextField.getText().toString()));

                startActivity(intent);
            }
        });
    }
}