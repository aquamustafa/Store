package com.example.store.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.store.Adapters.ProductAdapter;
import com.example.store.Model.Product;
import com.example.store.R;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.ActivitySearchResultsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity implements ProductAdapter.listItemOnlick {

    ActivitySearchResultsBinding binding;
    ValueEventListener valueEventListener;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProductAdapter adapter;
    ArrayList<Product> productList=new ArrayList<>();
    UserPref pref;
    ArrayList<String> producIds=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_search_results);
        Views();
        GetIntentData();

    }

    private void SettingUpFirebase(String query) {

        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(SearchResultsActivity.this, "jj", Toast.LENGTH_SHORT).show();
                if (snapshot.getChildrenCount()!=0) {

                    for (DataSnapshot s : snapshot.getChildren()) {
                        producIds.add(s.getKey());
                        productList.add(s.getValue(Product.class));
                    }

                    adapter.setList(productList,producIds);
                    binding.resultsCount.setText(String.valueOf(productList.size())+" Results");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query1=reference.orderByChild("name").equalTo(query);
        query1.addValueEventListener(valueEventListener);
    }

    private void GetIntentData() {
        Intent intent=getIntent();
        String query=intent.getStringExtra("Search");
        SettingUpFirebase(query);
    }

    private void Views() {
        pref=new UserPref(this.getApplicationContext());
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Products");
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        binding.searchResultsRecycle.setLayoutManager(manager);
        adapter=new ProductAdapter(this,this,pref);
        binding.searchResultsRecycle.setAdapter(adapter);
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals("")){
                SettingUpFirebase(query);
                return false;
            }
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onitemclicked(int position) {

        Gson gson=new Gson();
        Intent intent=new Intent(SearchResultsActivity.this,ProductDetailsActivity.class);
        intent.putExtra("Product",gson.toJson(productList.get(position)));
        intent.putExtra("ProductKey",producIds.get(position));
        Log.d("ProductId",producIds.get(position));
        startActivity(intent);


    }
}