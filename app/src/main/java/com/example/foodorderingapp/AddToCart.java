package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class AddToCart extends AppCompatActivity {

    String name;
    String price;
    String description;
    int delivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        description = getIntent().getStringExtra("description");
        delivery = getIntent().getIntExtra("delivery",0);
        String rating = getIntent().getStringExtra("rating");
        String icon = getIntent().getStringExtra("icon");
        //String delivery = getIntent().getStringExtra("delivery");

        TextView cartName = findViewById(R.id.cart_name);
        Button cartPrice = findViewById(R.id.cart_price);
        TextView cartRating = findViewById(R.id.cart_rating);
        TextView cartDelivery = findViewById(R.id.cart_delivery);
        TextView cartdescription = findViewById(R.id.cart_description);
        ImageView cartIcon = findViewById(R.id.cart_icon);

        cartName.setText(name);
        cartPrice.setText("Add to cart - RM"+price);
        cartRating.setText(rating);
        //cartDelivery.setText("RM "+delivery+".00");
        if (delivery==0){
            cartDelivery.setText("FREE Delivery");
        }else {
            cartDelivery.setText("RM " + Integer.toString(delivery) + ".00");
        }
        String iconUrl = "https://raw.githubusercontent.com/WillyLiew/DeliveryApp/master/"+icon;
        try{
            Glide.with(AddToCart.this).load(iconUrl).into(cartIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addFood(View view) {
        Intent CartActivity = new Intent(AddToCart.this, Cart.class);
        CartActivity.putExtra("name",name);
        CartActivity.putExtra("price",price);
        CartActivity.putExtra("delivery",delivery);
        CartActivity.putExtra("description",description);
        startActivity(CartActivity);


    }
}
