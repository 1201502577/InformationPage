package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AddToCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        String name = getIntent().getStringExtra("name");

        TextView tv = findViewById(R.id.pendingActivity);
        tv.setText("Activity pending for "+name);
    }
}
