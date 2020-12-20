package com.example.foodorderingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<food_Item> food_items = new ArrayList<food_Item>();
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView popularView = findViewById(R.id.popularView);
        //popularView.setLayoutManager(layoutManager);
        popularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) );

        final PopularAdapter popularAdapter = new PopularAdapter();
        popularView.setAdapter(popularAdapter);
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        "https://raw.githubusercontent.com/WillyLiew/DeliveryApp/master/msia.json",
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                food_items.clear();
                                for(int i =0;i < response.length();i++) {
                                    try {
                                        JSONObject item = response.getJSONObject(i);
                                        String name = item.getString("name");
                                        String icon = item.getString("icon");
                                        String price = item.getString("price");
                                        String rating = item.getString("rating");
                                        food_Item foodItem = new food_Item(name,icon,price,rating);
                                        food_items.add(foodItem);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(MainActivity.this,"JSON file format not compatible",Toast.LENGTH_LONG).show();
                                    }
                                }
                                popularAdapter.addElements(food_items);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,"Cannot retrieve from github",Toast.LENGTH_LONG).show();
                            }
                        }
                );

        queue.add(request);
    }

    class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder>{
        ArrayList<food_Item> elements = new ArrayList<food_Item>();

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View popularView = getLayoutInflater().inflate(R.layout.food_item,parent,false);
            return new MyViewHolder(popularView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.fName.setText(elements.get(position).getName());
            holder.fRating.setText(elements.get(position).getRating());
            holder.fPrice.setText("RM " + elements.get(position).getPrice());

            String iconUrl = "https://raw.githubusercontent.com/WillyLiew/DeliveryApp/master/"+elements.get(position).getIcon();
            final LruCache<String, Bitmap> cache =new LruCache<String, Bitmap>(20);
            
            holder.fIcon.setImageUrl(iconUrl, new ImageLoader(queue, new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            }));
        }

        @Override
        public int getItemCount() {
            return elements.size();
        }

        public void addElements(ArrayList<food_Item> food_items){
            elements.clear();
            elements.addAll(food_items);
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView fName, fRating, fPrice;
            public NetworkImageView fIcon;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                fIcon = itemView.findViewById(R.id.popular_icon);
                fName = itemView.findViewById(R.id.popular_name);
                fRating = itemView.findViewById(R.id.popular_rating);
                fPrice =itemView.findViewById(R.id.popular_price);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                String name = elements.get(getAdapterPosition()).getName();
                Intent addtocart = new Intent(MainActivity.this, AddToCart.class);
                addtocart.putExtra("name",name);
                startActivity(addtocart);

            }
        }
    }


}
