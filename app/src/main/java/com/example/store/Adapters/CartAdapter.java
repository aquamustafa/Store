package com.example.store.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.store.Model.CartItem;
import com.example.store.Model.Product;
import com.example.store.R;
import com.example.store.Utility.UserPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    public List<Product> arrayList = new ArrayList<>();
    List<String> cartItems=new ArrayList<>();
    Context context;
    UserPref userPref;

    public CartAdapter(Context context, UserPref userPref) {
        this.context = context;
        this.userPref = userPref;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        CartHolder viewHolder = new CartHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, final int position) {
         holder.name.setText(arrayList.get(position).getName());
          int po=position;
         holder.price.setText(String.valueOf(arrayList.get(position).getPrice())+"EGP");
        holder.description.setText(arrayList.get(position).getDescription());
        Glide.with(context).load(arrayList.get(position).getPhoto()).into(holder.ProductImg);

        //    holder.count.setText(String.valueOf(count.get(po).getQuantity()));

/*        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(holder.count.getText().toString())!=0){
               holder.count.setText(String.valueOf(Integer.parseInt(holder.count.getText().toString())-1));
                userPref.IncreaseItemCount(po);
                }
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.count.setText(String.valueOf(Integer.parseInt(holder.count.getText().toString())+1));
                userPref.DecreaseITtemCount(po);

            }
        });
       // Glide.with(context).load(arrayList.get(position).getPhoto()).into(holder.ProductImg);
        */
        holder.removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                //TODO:REMOVEITEM FROM CART IN FIREBASE
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                 firebaseDatabase.getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(cartItems.get(position)).removeValue();
                Toast.makeText(context, "Item Removed From Cart", Toast.LENGTH_SHORT).show();
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    public void add(Product value) {
        arrayList.add(value);
        notifyDataSetChanged();
    }

    public static class CartHolder extends RecyclerView.ViewHolder {
        TextView name,price,description,plus,minus,count;
        ImageView removeImg,ProductImg;

        public CartHolder(View itemView) {
            super(itemView);
           // plus=itemView.findViewById(R.id.adding);
           // minus=itemView.findViewById(R.id.minus);
           // count=itemView.findViewById(R.id.item_count_cart_item);
            name=itemView.findViewById(R.id.product_name_cart_item);
            description=itemView.findViewById(R.id.product_description_cart_item);
            price=itemView.findViewById(R.id.product_price_cart_item);
            removeImg=itemView.findViewById(R.id.remove_product_cart_item);
            ProductImg=itemView.findViewById(R.id.product_pic_cart_item);

         }
    }

    public void setList(List<Product> arrayList, ArrayList<String> arrayList1) {

        this.arrayList = arrayList;
        this.cartItems = arrayList1;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if (arrayList==null||arrayList.size()==0){
            return 0;
        }
        return arrayList.size();
    }
}