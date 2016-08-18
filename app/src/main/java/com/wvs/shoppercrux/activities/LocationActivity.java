package com.wvs.shoppercrux.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wvs.shoppercrux.Gson.ItemObject;
import com.wvs.shoppercrux.Gson.RecyclerViewAdapter;
import com.wvs.shoppercrux.Gson.SimpleDividerItemDecoration;
import com.wvs.shoppercrux.R;
import com.wvs.shoppercrux.VolleyLocation.BackgroundTask;
import com.wvs.shoppercrux.VolleyLocation.Contact;
import com.wvs.shoppercrux.VolleyLocation.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
       // recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        layoutManager = new LinearLayoutManager(LocationActivity.this);
        recyclerView.setLayoutManager(layoutManager);


        requestJsonObject();
    }

    private void requestJsonObject(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://prachodayat.in/shopper_android_api/location.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();

                List<ItemObject> posts = new ArrayList<ItemObject>();
                posts = Arrays.asList(mGson.fromJson(response, ItemObject[].class));

                adapter = new RecyclerViewAdapter(LocationActivity.this, posts);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }
}
