package com.example.store.Ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.store.Adapters.CartAdapter;
import com.example.store.Model.CartItem;
import com.example.store.Model.Product;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.FragmentCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
  * create an instance of this fragment.
 *
 */
public class CartFragment extends Fragment {

    FragmentCartBinding binding;
    UserPref userPref;
    CartAdapter adapter;
    ArrayList<Product> list;
    FirebaseDatabase database;
    ArrayList<CartItem> cartItems;
    ArrayList<String> cartIds;

    int Total;
    DatabaseReference reference;
      public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPref=new UserPref(getActivity().getApplicationContext());
        list=new ArrayList<>();
        cartIds=new ArrayList<>();
        cartItems=new ArrayList<>();
        adapter=new CartAdapter(this.getContext(),userPref);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cartRecycle.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.cartRecycle.setAdapter(adapter);
        GetUsersCartItems();

        binding.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Total=0;
                for(int i=0;i<list.size();i++){
                    Total+=list.get(i).getPrice();
                     Log.d("Total",String.valueOf(Total));
                }

                Gson gson=new Gson();
                String s=gson.toJson(cartItems);
                Toast.makeText(getActivity(), String.valueOf(Total), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),CheckoutActivity.class)
                        .putExtra("cartItems",s)
                        .putExtra("total",String.valueOf(Total)));

            }
        });


    }

    private void GetUsersCartItems() {
    reference.child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot s:snapshot.getChildren()){
                Log.d("cart",s.getKey());
                cartIds.add(s.getKey());
                cartItems.add(s.getValue(CartItem.class));
             }
            GetProduct();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
      }

    private void GetProduct() {
          for(CartItem s:cartItems) {
              reference.child("Products").child(s.getProductid()).addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      list.add(snapshot.getValue(Product.class));
                      adapter.setList(list,cartIds);
                   }


                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
          });

          }

          }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}