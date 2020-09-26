package com.example.store.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.store.Adapters.ProductAdapter;
import com.example.store.Model.Product;
import com.example.store.R;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.ActivityCategoryResultsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryResultsActivity extends AppCompatActivity implements ProductAdapter.listItemOnlick {


    ActivityCategoryResultsBinding binding;
    ProductAdapter adapter;
    UserPref userPref;
    ArrayList<Product> productlist;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> productId=new ArrayList<>();
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_category_results);
        category= getIntent().getStringExtra("Category");
        Views();
        GetIntentData();

    }

    private void GetIntentData() {
        if (category != null) {
            switch (category){
                case "T-shirts":
                    GetData(category);
                    break;

                case "Shoes":
                    GetData(category);
                    break;

                case "Shirts":
                    GetData(category);
                    break;

            }
        }

    }

    private void GetData(String category) {
        reference.child("Products").orderByChild("Category").equalTo(category).addValueEventListener(valueEventListener);

    }




    private void Views() {
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        productlist=new ArrayList<>();
        userPref=new UserPref(getApplicationContext());
        adapter=new ProductAdapter(this,this,userPref);
        binding.categoryName.setText(category);
        binding.categoryRecycle.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        binding.categoryRecycle.setAdapter(adapter);
    }

    @Override
    public void onitemclicked(int position) {
        Gson gson=new Gson();
        Intent intent=new Intent(CategoryResultsActivity.this,ProductDetailsActivity.class);
        intent.putExtra("Product",gson.toJson(productlist.get(position)));
        intent.putExtra("ProductKey",productId.get(position));
        startActivity(intent);
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.getChildrenCount()!=0) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    productId.add(s.getKey());
                    productlist.add(s.getValue(Product.class));

                }

                adapter.setList(productlist,productId);

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}
