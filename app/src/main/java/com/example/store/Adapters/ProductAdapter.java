package com.example.store.Adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.store.Model.CartItem;
import com.example.store.Model.Product;
import com.example.store.R;
import com.example.store.Utility.UserPref;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Product> arrayList = new ArrayList<>();
    public List<String> ProdctIds=new ArrayList<>();
    Context context;
    listItemOnlick listItemOnlick;
    UserPref userPref;

    public void setList(List<Product> productList, List<String> productsId) {
        this.arrayList=productList;
        this.ProdctIds=productsId;
        notifyDataSetChanged();
    }

    public interface listItemOnlick{
        void onitemclicked (int position);
    }

    public ProductAdapter(Context context, ProductAdapter.listItemOnlick listItemOnlick, UserPref userPref) {
        this.context = context;
        this.listItemOnlick = listItemOnlick;
        this.userPref = userPref;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        ProductHolder viewHolder = new ProductHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, final int position) {
//        holder.ratingBar.setRating((float) arrayList.get(position).getAverageRating());
    //    holder.ratingcount.setText(String.valueOf(arrayList.get(position).getNoRatings()));
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(arrayList.get(position).getPrice() +" EGP");
        Glide.with(context).load(arrayList.get(position).getPhoto()).into(holder.image);

    }

    public  class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,price,ratingcount;
        RatingBar ratingBar;
        ImageView image;
        Button button;
        public ProductHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.prdouct_name_item);
            price=itemView.findViewById(R.id.prdouct_price_item);
          //  ratingcount=itemView.findViewById(R.id.Ratings_count);
           // ratingBar=itemView.findViewById(R.id.product_ratingbar_item);
            image=itemView.findViewById(R.id.product_image_item);
           // button=itemView.findViewById(R.id.add_cart_product_item);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            listItemOnlick.onitemclicked(position);
        }
    }


    @Override
    public int getItemCount() {
        if (arrayList==null||arrayList.size()==0){
            return 0;
        }
        return arrayList.size();
    }
}