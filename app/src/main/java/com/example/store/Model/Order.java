package com.example.store.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
    String userID;
    String Reigon;
    String City;
    String Street;
    String Floor;
    String AppartmentNo;
    String Name;
    Double phone;
    boolean delivered;
    Long OrderPlacedDate;
    boolean Accepted;
    List<CartItem> productlist;
    double Total;


    public Order() {
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getOrderPlacedDate() {
        return OrderPlacedDate;
    }

    public void setOrderPlacedDate(Long orderPlacedDate) {
        OrderPlacedDate = orderPlacedDate;
    }

    public boolean isAccepted() {
        return Accepted;
    }

    public void setAccepted(boolean accepted) {
        Accepted = accepted;
    }

    public List<CartItem> getProductlist() {
        return productlist;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public void setProductlist(List<CartItem> productlist) {
        this.productlist = productlist;
    }

    public Double getPhone() {
        return phone;
    }

    public void setPhone(Double phone) {
        this.phone = phone;
    }

    public String getReigon() {
        return Reigon;
    }

    public void setReigon(String reigon) {
        Reigon = reigon;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getAppartmentNo() {
        return AppartmentNo;
    }

    public void setAppartmentNo(String appartmentNo) {
        AppartmentNo = appartmentNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
