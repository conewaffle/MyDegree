package com.example.mydegree.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private TextView login;
    private EditText input_name, input_email, input_password, input_confirmPassword;
    private Button signup;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_confirmPassword = findViewById(R.id.input_confirm_password);
        signup = findViewById(R.id.sign_up);
        login = findViewById(R.id.log_in);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onSignUpFailed();
                    return;
                }
                signup.setEnabled(false);

                final ProgressDialog progressDialog = ProgressDialog.show(SignUp.this, "Please wait...", "Processing...", true);

                String name = input_name.getText().toString();
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                new Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                onSignUpSuccess();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        });

    }

    // If sign up is a success
    public void onSignUpSuccess() {
        signup.setEnabled(true);
        setResult(RESULT_OK, null);
        (auth.createUserWithEmailAndPassword(input_email.getText().toString(), input_password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            final String name = input_name.getText().toString().trim();
                            final String email = input_email.getText().toString().trim();
                            final String password = input_password.getText().toString().trim();
                            final String confirmPassword = input_confirmPassword.getText().toString().trim();

                            User user = new User(name, email);
                            auth.signInWithEmailAndPassword(email, password);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
                            DatabaseReference currentUser = databaseReference.child(auth.getCurrentUser().getUid());
                            currentUser.child("name").setValue(name);
                            currentUser.child("email").setValue(email);
                            Intent intent = new Intent (SignUp.this, Login.class);
                            SignUp.this.finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        finish();
    }

    // If sign up is unsuccessful
    public void onSignUpFailed() {
        Toast.makeText(this, "Sign up unsuccessful. Please try again.", Toast.LENGTH_SHORT).show();
        signup.setEnabled(true);
    }

    // Sign up validation
    public boolean validate() {
        boolean valid = true;
        String name = input_name.getText().toString();
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();
        String confirmPassword = input_confirmPassword.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            input_name.setError("Minimum 2 characters");
            valid = false;
        } else {
            input_name.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Email must be valid");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            input_password.setError("Password must be between 4 and 20 alphanumeric characters");
        } else {
            input_password.setError(null);
        }

        if (password.equals(confirmPassword)) {
            input_confirmPassword.setError(null);
        } else {
            input_confirmPassword.setError("Passwords do not match");
            valid = false;
        }

        return valid;
    }

}
