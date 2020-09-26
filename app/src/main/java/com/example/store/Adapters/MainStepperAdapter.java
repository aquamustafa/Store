package com.example.store.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.store.Model.Order;
import com.liefery.android.vertical_stepper_view.VerticalStepperAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainStepperAdapter extends VerticalStepperAdapter {
    public Order order=new Order();

    public MainStepperAdapter(Context context) {
        super(context);
        Log.d("yeah","Constructor");

    }

    @NonNull
    @Override
    public CharSequence getTitle(int position) {
        Log.d("yeah","title");

        if (position==0){
           return "Order Placed";
        }
        if(position==1){
            return "Accepted";
        }
        if (position==2){
            return "Delevired";
        }
        return "";
    }

    @Nullable
    @Override
    public CharSequence getSummary(int position) {
        Log.d("yeah","summry");

        if (position==0){
            return "df.format(currentDate)";
        }
        if(position==1){

                return "Accepted";
            }


        if (position==2){
            return "Delevired";
        }
        return "";
    }

    @Override
    public boolean isEditable(int position) {
        return true;
    }

    @Nullable
    @Override
    public void next() {
        super.next();
    }

    @NonNull
    @Override
    public View onCreateContentView(Context context, int position) {

        Log.d("yeah","createview");

        return null;
    }

    @Override
    public int getCount() {

        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }
}
