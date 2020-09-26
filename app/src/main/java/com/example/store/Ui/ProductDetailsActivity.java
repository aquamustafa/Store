package com.example.store.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.store.Model.CartItem;
import com.example.store.Model.Product;
import com.example.store.R;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.ActivityProductDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    ActivityProductDetailsBinding binding;
    Product product;
    UserPref userPref;
    String productId;
    FirebaseDatabase database;
    DatabaseReference reference;
    Map<String,CartItem> map=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        GetIntentData();
        Views();
    }

    private void Views() {

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Glide.with(this).load(product.getPhoto()).into(binding.productPicDetails);

        binding.materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if the product is already on the Users Cart
                // if its dont add it to the cart
                reference.orderByChild("productid").equalTo(productId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            startActivity(new Intent(ProductDetailsActivity.this,MainActivity.class)
                                    .putExtra("Activity","details"));
                        }
                        else {
                            reference.child(productId).setValue(new CartItem(productId, 1)).addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(ProductDetailsActivity.this, MainActivity.class)
                                            .putExtra("Activity", "details"));
                                }
                            });
                        }}

                        @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });





                reference.push().setValue(new CartItem(productId,1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(ProductDetailsActivity.this,MainActivity.class)
                                .putExtra("Activity","details"));
                        finish();
                    }

                });

    }});

    }

    private void GetIntentData() {
        Gson gson=new Gson();
        Intent intent=getIntent();
        product= gson.fromJson(intent.getStringExtra("Product"),Product.class);
        productId=intent.getStringExtra("ProductKey");
        binding.DescriptionDetails.setText(product.getDescription());
        binding.productPriceDetails.setText(String.valueOf(product.getPrice())+"egp");
        binding.productNameDetails.setText(product.getName());
    }


}