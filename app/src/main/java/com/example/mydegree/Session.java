package com.example.mydegree;

import android.app.Application;
import android.content.Intent;

import com.example.mydegree.Progress.Program;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Session extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // This class allows the user to remain logged in even if they close the application

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

            Intent intent = new Intent(Session.this, Program.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

    }
}
