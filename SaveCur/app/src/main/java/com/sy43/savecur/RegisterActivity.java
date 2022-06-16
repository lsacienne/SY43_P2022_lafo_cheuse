package com.sy43.savecur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Spinner statusSpinner;
    private TextInputEditText companyNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("info", "info");
        mAuth = FirebaseAuth.getInstance();

        companyNameInput = (TextInputEditText) findViewById(R.id.companyNameInput);
        statusSpinner = (Spinner) findViewById(R.id.statusSelection);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) companyNameInput.setVisibility(View.INVISIBLE);
                else companyNameInput.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onRegisterBtnClicked(android.view.View view) {
        Intent intent = new Intent(this, HomeActivity.class);

        String email = ((TextInputEditText) findViewById(R.id.emailInput)).getText().toString();
        String password = ((TextInputEditText) findViewById(R.id.passwordInput)).getText().toString();
        String name = ((TextInputEditText) findViewById(R.id.nameInput)).getText().toString();


        boolean company = statusSpinner.getSelectedItemPosition() == 0;
        String companyName = "";
        if (company) {
            companyName = companyNameInput.getText().toString();
        }

        String finalCompanyName = companyName;
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("company", company);
                    if (company) user.put("companyName", finalCompanyName);

                    String userUid = mAuth.getCurrentUser().getUid();

                    Map<String, Object> category = new HashMap<>();
                    category.put("name", "Party");
                    category.put("moneySpent", 70);
                    category.put("moneyLimit", 170);

                    db.collection("users").document(userUid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.collection("users").document(userUid).collection("categories").add(category).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    db.collection("users").document(userUid).collection("expenses").add(new HashMap<>());
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                } else {
                    Log.w("Auth", task.getException());
                }
            }
        });

    }
}