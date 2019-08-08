package com.example.mydegree.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.Plan.Plan;
import com.example.mydegree.Program;
import com.example.mydegree.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private TextView email, password, createAccount, forgotPassword, noLogin;
    private Button login;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(Login.this);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        createAccount = findViewById(R.id.createAccount);
        forgotPassword = findViewById(R.id.forgotPassword);
        login = findViewById(R.id.login);
        noLogin = findViewById(R.id.noLogin);

        password.addTextChangedListener(new addListenerOnTextChange(this, (EditText) password));

        noLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Program.class);
                startActivity(intent);
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                    final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "Please wait", "Processing...", true);
                    (auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, Program.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
            });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        if (emailInput.isEmpty() || passwordInput.isEmpty()) {
            email.setError("Must have input");
            valid = false;
        } else {
            email.setError(null);
        }
        return valid;
    }


    public class addListenerOnTextChange implements TextWatcher {
        private Context context;
        EditText editText;

        public addListenerOnTextChange(Context context, EditText editText) {
            super();
            this.context = context;
            this.editText= editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            login.setBackgroundColor(Color.parseColor("#33a7ff"));
            login.setTextColor(Color.parseColor("#ffffff"));
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

}
