package com.example.mydegree.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mydegree.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private TextView login;
    private EditText input_email;
    private Button sendEmail;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        login = findViewById(R.id.login);
        input_email = findViewById(R.id.email);
        sendEmail = findViewById(R.id.sendEmail);

        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPassword.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPassword.this, Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }

}
