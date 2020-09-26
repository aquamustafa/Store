package com.example.store.Model;

public class CartItem {
    String productid;
    int quantity;

    public CartItem() {
    }

    public CartItem(String productid, int quantity) {
        this.productid = productid;
        this.quantity = quantity;
    }


     public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
