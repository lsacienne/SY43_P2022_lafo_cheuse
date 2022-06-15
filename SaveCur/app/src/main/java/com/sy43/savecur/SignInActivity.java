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

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onSignInBtnClicked(android.view.View view) {
        Intent intent = new Intent(this, HomeActivity.class);

        String email = ((TextInputEditText) findViewById(R.id.EmailInput)).getText().toString();
        String password = ((TextInputEditText) findViewById(R.id.PasswordInput)).getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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