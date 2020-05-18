package com.example.soni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Signupform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupform);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Signup Form");
    }
}
