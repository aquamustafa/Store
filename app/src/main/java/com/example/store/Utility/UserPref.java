package com.example.store.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.store.Model.CartItem;
import com.example.store.Model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserPref {
    SharedPreferences mPrefs ;
    Gson gson = new Gson();

    public UserPref(Context context) {
        mPrefs = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
    }

    public void saveCardItems(Product product) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        //there are no Items in CART
        if (GetSCardItems() == null) {
            ArrayList<Product> products = new ArrayList<>();
            products.add(product);
            prefsEditor.putString("Items", gson.toJson(products));
            prefsEditor.commit();
         }

        else {
            ArrayList<Product> list = GetSCardItems();
            for (Product s : list) {
                if (s.getName() == product.getName()) {
                    Log.d("UserPref","doesnt match");
                }
                else {
                    list.add(product);
                    prefsEditor.putString("Items", gson.toJson(list));
                    prefsEditor.commit();
                 }
            }


        }
    }



    public ArrayList<Product> GetSCardItems() {
        if(mPrefs.getString("Items", "") .equals("")){
            return null;
        }
        String s=mPrefs.getString("Items", "");
        Type type=new TypeToken<ArrayList<Product>>(){}.getType();
        return gson.fromJson(s,type);

    }

   public void RemoveItemFromCart(int position){
        ArrayList<Product> list=GetSCardItems();
        list.remove(position);
       SharedPreferences.Editor prefsEditor = mPrefs.edit();
       prefsEditor.clear().commit();

       for(Product p :list){
           saveCardItems(p);
       }
    }

    public void saveItemsIdAndCount(CartItem cartItem) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        //there are no Items in CART
        if(GetSItemsIdsAndCount()==null){
            ArrayList<CartItem> cartItems=new ArrayList<>();
            cartItems.add(cartItem);
            prefsEditor.putString("CartItems",gson.toJson(cartItems));
            prefsEditor.commit();
        }else
        {   ArrayList<CartItem> list=GetSItemsIdsAndCount();
            list.add(cartItem);
            prefsEditor.putString("CartItems",gson.toJson(list));
            prefsEditor.commit();
        }

    }


    public void IncreaseItemCount(int position){

        ArrayList<CartItem>list=GetSItemsIdsAndCount();
        list.get(position).setQuantity(list.get(position).getQuantity()+1);
        DeleteItemsIDandCount();
        for (CartItem c: list) {
            saveItemsIdAndCount(c);
        }
     }

    private void DeleteItemsIDandCount() {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.remove("CartItems").commit();

    }

    public void DecreaseITtemCount(int position){
        ArrayList<CartItem>list=GetSItemsIdsAndCount();
        list.get(position).setQuantity(list.get(position).getQuantity()-1);
        DeleteItemsIDandCount();
        for (CartItem c: list) {
            saveItemsIdAndCount(c);
        }
     }


    public ArrayList<CartItem> GetSItemsIdsAndCount() {
        if(mPrefs.getString("CartItems", "") .equals("")){
            return null;
        }
        String s=mPrefs.getString("CartItems", "");
        Type type=new TypeToken<ArrayList<CartItem>>(){}.getType();
        return gson.fromJson(s,type);

    }

    public void RemoveItemsIds(int position){
        ArrayList<CartItem> list=GetSItemsIdsAndCount();
        list.remove(position);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear().commit();

        for(CartItem p :list){
            saveItemsIdAndCount(p);
        }


    }

    public  void DeletePrefs(){
        mPrefs.edit().clear().commit();
    }



}
