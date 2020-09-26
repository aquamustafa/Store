package com.example.store.Ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.store.Adapters.MainStepperAdapter;
import com.example.store.Model.Order;
import com.example.store.R;
import com.example.store.databinding.ActivityTrackingOrderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class TrackingOrderActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    MainStepperAdapter adapter;
    ActivityTrackingOrderBinding binding;
    Order order;
    String OrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_tracking_order);
        GetIntentData();
        Var();
        Views();
        SetUpFirebase();

    }

    private void GetIntentData() {
        Gson gson=new Gson();
        Intent intent=getIntent();
        order=gson.fromJson(intent.getStringExtra("Orders"),Order.class);
        OrderId=getIntent().getStringExtra("OrderKey");

    }

    private void SetUpFirebase(){
        databaseReference.orderByKey().equalTo(OrderId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot s:snapshot.getChildren()){
                    Order order=s.getValue(Order.class);
                    if (order != null && order.isAccepted()) {
                        adapter.next();
                        if (order.isDelivered()) {
                            adapter.next();
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Var() {
        adapter=new MainStepperAdapter(this);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Orders");
    }

    private void Views() {

        binding.stepperList.setAdapter(adapter);
        binding.textView6.setText(OrderId);
        if (order.isAccepted()){
            adapter.next();

            if (order.isDelivered()){
                adapter.next();
            }
         }


    }
}