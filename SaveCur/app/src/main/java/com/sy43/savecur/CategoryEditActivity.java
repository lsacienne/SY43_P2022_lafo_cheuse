package com.sy43.savecur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryEditActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        Bundle extras = getIntent().getExtras();

        TextInputEditText nameTextField = (TextInputEditText) findViewById(R.id.categoryName);
        TextInputEditText moneySpentTextField = (TextInputEditText) findViewById(R.id.categoryMoney);
        TextInputEditText moneyLimitTextField = (TextInputEditText) findViewById(R.id.categoryMoneyLimit);
        String categoryID = extras.getString("categoryID");

        if (extras != null) {
            nameTextField.setText(extras.getString("name"));
            moneySpentTextField.setText(String.valueOf(extras.getFloat("moneySpent")));
            moneyLimitTextField.setText(String.valueOf(extras.getFloat("moneyLimit")));
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        Button applyChangesBtn = (Button) findViewById(R.id.applyCategoryChanges);

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> category = new HashMap<>();
                category.put("name", nameTextField.getText().toString());
                category.put("moneySpent", Float.parseFloat(moneySpentTextField.getText().toString()));
                category.put("moneyLimit", Float.parseFloat(moneyLimitTextField.getText().toString()));
                if (extras != null) {
                    db.collection("users").document(currentUser.getUid()).collection("categories").document(categoryID)
                            .update(category).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Intent intent = new Intent(CategoryEditActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                            });
                } else {
                    db.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            task.getResult().getReference().collection("categories").add(category)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("req", "DocumentSnapshot added with ID: " + documentReference.getId());

                                    Intent intent = new Intent(CategoryEditActivity.this, HomeActivity.class);
                                    startActivity(intent);
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

            }
        });
    }
}