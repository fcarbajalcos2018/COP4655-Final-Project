package com.example.finalproject;

public class UserModel {
    private String locationName;
    private String locationAddress;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String id;
    private String[] category = new String[3];
    private String rating;
    private String[] transaction = new String[3];
    private String phone;
    private String latitude;
    private String longitude;
    private boolean condition;


    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getTransaction() {
        return transaction;
    }

    public void setTransaction(String[] transaction) {
        this.transaction = transaction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLocationName() {
        return locationName;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public boolean getCondition() {
        return condition;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }
}
