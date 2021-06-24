package com.client.kuuf;

public class Products {

    String productName;
    int minPlayer;
    int maxPlayer;
    int price;
    Double latitude;
    Double longitude;

    public Products(String productName, int minPlayer, int maxPlayer, int price, Double latitude, Double longitude) {
        this.productName = productName;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Products() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
