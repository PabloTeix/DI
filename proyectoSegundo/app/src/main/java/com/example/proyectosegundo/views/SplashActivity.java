package com.example.proyectosegundo.views;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // startActivity(new Intent(this, MainActivity.class));
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    }

