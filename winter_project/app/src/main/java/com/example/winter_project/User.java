package com.example.winter_project;

public class User {
    public String imageUrl;
    public String capture_time;
    public String car;
    public String latitude;
    public String longtitude;
    public String userid;
    public String image_delete_name;
    public User(){

    }


    public User(String imageUrl,String capture_time,String car,String latitude,String longtitude,String userid){
        this.imageUrl=imageUrl;
        this.capture_time=capture_time;
        this.car=car;
        this.latitude=latitude;
        this.longtitude=longtitude;
        this.userid=userid;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCapture_time() {
        return capture_time;
    }

    public void setCapture_time(String capture_time) {
        this.capture_time = capture_time;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getlongtitude() {
        return longtitude;
    }

    public void setlongtitude(String longtitude) {
        this.longtitude = longtitude;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getImage_delete_name() {
        return image_delete_name;
    }

    public void setImage_delete_name(String image_delete_name) {
        this.image_delete_name = image_delete_name;
    }

}
