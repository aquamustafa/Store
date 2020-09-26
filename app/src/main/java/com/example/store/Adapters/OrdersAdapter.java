package com.example.store.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store.Model.Order;
import com.example.store.R;
import com.example.store.Utility.UserPref;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersHolder> {
    private List<Order> arrayList = new ArrayList<>();
    List<String> orderIds=new ArrayList<>();
    Context context;
    listItemOnlick listItemOnlick;
    public interface listItemOnlick{
        void onitemclicked (int position);
    }
    public OrdersAdapter(Context context,listItemOnlick listItemOnlick) {
        this.context = context;
        this.listItemOnlick = listItemOnlick;
     }


    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        OrdersHolder viewHolder = new OrdersHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHolder holder, int position) {
        holder.orderid.setText(orderIds.get(position));
        if(arrayList.get(position).isAccepted()){
            holder.state.setText("Accepted");
        }
        else{
            holder.state.setTextColor(Color.GRAY);
            holder.state.setText("Pending");
        }

    }

    public class OrdersHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView state,orderid;
        public OrdersHolder(View itemView) {
            super(itemView);
            state=itemView.findViewById(R.id.state_order_item);
            orderid=itemView.findViewById(R.id.orderid_order_item);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            listItemOnlick.onitemclicked(position);
        }
    }


    public void setList(List<Order> arrayList, ArrayList<String> ids) {
        this.arrayList = arrayList;
        this.orderIds=ids;
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