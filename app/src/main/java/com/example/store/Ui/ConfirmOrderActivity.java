package com.example.store.Ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.store.Model.CartItem;
import com.example.store.Model.Order;
import com.example.store.Model.Product;
import com.example.store.R;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.ActivityConfirmOrderBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashMap;

public class ConfirmOrderActivity extends AppCompatActivity {

    Order order;
    HashMap<String, CartItem> map=new HashMap<String, CartItem>();
    UserPref userPref;
    DatabaseReference reference,cartRefrence;
    FirebaseDatabase database;
    ArrayList<CartItem> cartItems=new ArrayList<>();

    ActivityConfirmOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_confirm_order);
        Views();
        GetIntentData();
        SetData();
        //  reference.child("Orders").push().setValue(order);

    }



    private void SetData() {
    binding.deleveryPrice.setText("7");
    binding.itemsPrice.setText(String.valueOf(order.getTotal()));
    binding.totalCost.setText(String.valueOf(order.getTotal())+7);
    binding.name.setText(order.getName());
    order.setProductlist(cartItems);

    }

    private void Views() {
        userPref=new UserPref(getApplicationContext());
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        cartRefrence=database.getReference().child("Cart");
        binding.confirmOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          /*      for(int i=0;i>cartItems.size()-1;i++){
                     map.put(String.valueOf(i),new CartItem(cartIds.get(0),1));
                }
              order.setProductlist(map);

           */

                reference.child("Orders").push().setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //userPref.DeletePrefs();
                        Toast.makeText(ConfirmOrderActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                        DeleteUserShoppingCartItems();
                    }
                });
            }
        });
    }

    private void DeleteUserShoppingCartItems() {
    cartRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            startActivity(new Intent(ConfirmOrderActivity.this,MainActivity.class).putExtra("Activity","ConfirmOrder"));

        }
    });

    }
    private void GetIntentData() {
       Intent intent= getIntent();
       String m =intent.getStringExtra("Order");
         Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<CartItem>>(){}.getType();
        cartItems=gson.fromJson(intent.getStringExtra("cartItems"),type);
        order=gson.fromJson(m,Order.class);
     }

}