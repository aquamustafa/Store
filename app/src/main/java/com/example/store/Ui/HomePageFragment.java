package com.example.store.Ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.store.Adapters.ProductAdapter;
import com.example.store.Model.Product;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.FragmentHomePageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
  * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment implements ProductAdapter.listItemOnlick {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProductAdapter adapter;
    List<Product> productList=new ArrayList<>();
    UserPref userPref;
    List<String> productsId=new ArrayList<>();
    FragmentHomePageBinding binding;


    public HomePageFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPref=new UserPref(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Views();
        SettingUpFirebase();

    }

    private void SettingUpFirebase() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference().child("Products");

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount()!=0) {
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        productsId.add(s.getKey());
                        productList.add(s.getValue(Product.class));
                        Log.d("Products",productsId.get(0));
                    }


                    adapter.setList(productList,productsId);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("shiit",databaseError.toString());

            }
        };

        reference.addValueEventListener(valueEventListener);
    //    Product product=new Product ("Ds",5,4,"dsd","DSAD","DSAD",1,2);
     //   reference.push().setValue(product);
    }

    private void Views() {
        binding.popularRecycle.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL,false));
        adapter=new ProductAdapter(this.getContext(),this,userPref);
        binding.popularRecycle.setAdapter(adapter);

        binding.ShirtsCatergory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CategoryResultsActivity.class).putExtra("Category","Shirts"));
            }
        });
        binding.TShirtsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CategoryResultsActivity.class).putExtra("Category","T-Shirts"));
            }
        });
        binding.ShoesCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CategoryResultsActivity.class).putExtra("Category","Shoes"));
            }
        });


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(!(query.equals("")||query.isEmpty())){

                    Intent intent=new Intent(getActivity(),SearchResultsActivity.class);
                    intent.putExtra("Search",query);
                    startActivity(intent);
                    return true;

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onitemclicked(int position) {

    }
}
