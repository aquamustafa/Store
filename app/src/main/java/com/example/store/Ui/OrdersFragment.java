package com.example.store.Ui;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.store.Adapters.OrdersAdapter;
import com.example.store.Model.Order;
import com.example.store.R;
import com.example.store.databinding.FragmentCartBinding;
import com.example.store.databinding.FragmentOrdersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
  * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment implements OrdersAdapter.listItemOnlick {
    FirebaseDatabase database;
    DatabaseReference reference;
    FragmentOrdersBinding binding;
    OrdersAdapter adapter;
    ArrayList<Order> list=new ArrayList<>();
    ArrayList<String> ids=new ArrayList<>();
    
    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new OrdersAdapter(getContext(),this);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Orders");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.orderRecycle.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.orderRecycle.setAdapter(adapter);
        reference.orderByChild("userID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void onitemclicked(int position) {
        Gson gson=new Gson();
        Intent intent=new Intent(getActivity(),TrackingOrderActivity.class);
        intent.putExtra("Orders",gson.toJson(list.get(position)));
        intent.putExtra("OrderKey",ids.get(position));
        startActivity(intent);
    }
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(snapshot.hasChildren()){

                 for(DataSnapshot s:snapshot.getChildren()){

                     Order order=s.getValue(Order.class);
                     ids.add(s.getKey());
                     list.add(order);
                     Log.d("yeah",order.getCity());
                 }

                 adapter.setList(list,ids);

            }
            }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}