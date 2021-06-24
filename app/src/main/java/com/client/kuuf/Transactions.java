package com.client.kuuf;

public class Transactions {

    String userID;
    String productID;
    String date;

    public Transactions(String userID, String productID, String date) {
        this.userID = userID;
        this.productID = productID;
        this.date = date;
    }

    public Transactions() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
