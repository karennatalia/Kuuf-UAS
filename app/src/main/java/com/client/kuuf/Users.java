package com.client.kuuf;

public class Users {

    String username;
    String password;
    String phone;
    String gender;
    int wallet;
    String dob;

    public Users(String username, String password, String phone, String gender, int wallet, String dob) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.wallet = wallet;
        this.dob = dob;
    }

    public Users() {

    }

    public Users(Users user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.gender = user.getGender();
        this.wallet = user.getWallet();
        this.dob = user.getDob();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
