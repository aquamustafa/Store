package com.example.store.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.store.Model.CartItem;
import com.example.store.Model.Order;
import com.example.store.Model.Product;
import com.example.store.R;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.ActivityCheckoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;
    Order order;
    String total;
    UserPref userPref;
    String cartItems;
    int I=0;

    final String tag="CheckoutActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        Views();
        GetIntent();
        SetUpFirebase();
     }

    private void GetIntent() {
        Intent intent=getIntent();
        cartItems=intent.getStringExtra("cartItems");
        total=intent.getStringExtra("total");
    }

    private void SetUpFirebase() {
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
    }

    private void Views() {
        userPref=new UserPref(getApplicationContext());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.placeOrder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            I=0;
             if(binding.ApartmentNo.getText().toString().equals("")){
                binding.AppartmentLayout.setError("Enter this Field");
                I++;
            }
            if(binding.nameEdt.getText().toString().equals("")){
                binding.NameLayout.setError("Enter this Field");
                I++;

            }
             if(binding.StreetEdt.getText().toString().equals("")){
                binding.StreetLayout.setError("Enter this Field");
                I++;

            }
            if(binding.reigon.getText().toString().equals("")){
                binding.ReigonLayout.setError("Enter this Field");
                I++;

            }
            if(binding.phoneEdt.getText().toString().equals("")){
                binding.phoneLayout.setError("Enter this Field");
                I++;

            }

            if(binding.City.getText().toString().equals("")){
                binding.CityLayout.setError("Enter this Field");
                I++;

            }
            if(binding.Floor.getText().toString().equals("")){
                binding.FloorLayout.setError("Enter this Field");
                I++;

           }
            if (I==0){

               Gson gson=new Gson();
               order=new Order();
               order.setAppartmentNo(binding.ApartmentNo.getText().toString());
               order.setCity(binding.City.getText().toString());
               order.setFloor(binding.Floor.getText().toString());
               order.setName(binding.nameEdt.getText().toString());
               order.setReigon(binding.reigon.getText().toString());
               order.setPhone(Double.parseDouble(binding.phoneEdt.getText().toString()));
               order.setStreet(binding.StreetEdt.getText().toString());
               order.setUserID(FirebaseAuth.getInstance().getUid());
               order.setTotal(Double.parseDouble(total));
                startActivity(new Intent(CheckoutActivity.this,ConfirmOrderActivity.class)
               .putExtra("Order",gson.toJson(order))
                        .putExtra("cartItems",cartItems));

        }
    }
     });
    }

}

