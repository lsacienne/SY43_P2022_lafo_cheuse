package com.sy43.savecur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("info", "info");
        mAuth = FirebaseAuth.getInstance();
    }

    public void onRegisterBtnClicked(android.view.View view) {
        Intent intent = new Intent(this, HomeActivity.class);

        String email = ((TextInputEditText) findViewById(R.id.emailInput)).getText().toString();
        String password = ((TextInputEditText) findViewById(R.id.passwordInput)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(intent);
                } else {
                    Log.w("Auth", task.getException());
                }
            }
        });

    }
}