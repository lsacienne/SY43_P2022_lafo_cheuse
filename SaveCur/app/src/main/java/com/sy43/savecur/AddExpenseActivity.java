package com.sy43.savecur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_layout);
        MaterialButton confirmAddExpenseBtn = (MaterialButton) findViewById(R.id.confirmExpenseAddBtn);
        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> categoriesNames = extras.getStringArrayList("categories");
        ArrayList<String> categoriesIDs = extras.getStringArrayList("categoriesIDs");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);

        Intent intent = new Intent(this, HomeActivity.class);

        confirmAddExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((TextInputEditText) findViewById(R.id.expenseName)).getText().toString();
                float amount = Float.parseFloat(((TextInputEditText) findViewById(R.id.expenseAmount)).getText().toString());
                int selectedCategoryPos = categorySpinner.getSelectedItemPosition();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String userUid = mAuth.getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> expense = new HashMap<>();
                expense.put("name", name);
                expense.put("category", categoriesNames.get(selectedCategoryPos));
                expense.put("categoryID", categoriesIDs.get(selectedCategoryPos));
                expense.put("amount", amount);
                expense.put("date", FieldValue.serverTimestamp());

                db.collection("users").document(userUid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        task.getResult().getReference().collection("expenses").add(expense)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("req", "DocumentSnapshot added with ID: " + documentReference.getId());

                                        db.collection("users").document(userUid).collection("categories")
                                                .document(expense.get("categoryID").toString())
                                                .update("moneySpent", FieldValue.increment(Float.parseFloat(expense.get("amount").toString())))
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        startActivity(intent);
                                                        Log.d("req", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("req", "Error adding document", e);
                                    }
                                });
                    }
                });
            }
        });
    }
}