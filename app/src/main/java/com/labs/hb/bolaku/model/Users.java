package com.labs.hb.bolaku.model;

/**
 * Created by BwX on 3/30/2018.
 */

public class Users {
    private String user_id;
    private String username;
    private String token_device;
    private String name;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String birthday;
    private String email;
    private String phone_no;
    private String gender;
    private String profile_picture;
    private String user_device;

    public Users() {
    }

    public Users(String user_id, String username, String token_device, String name, String first_name, String middle_name, String last_name, String birthday, String email, String phone_no, String gender, String profile_picture, String user_device) {
        this.user_id = user_id;
        this.username = username;
        this.token_device = token_device;
        this.name = name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.birthday = birthday;
        this.email = email;
        this.phone_no = phone_no;
        this.gender = gender;
        this.profile_picture = profile_picture;
        this.user_device = user_device;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken_device() {
        return token_device;
    }

    public void setToken_device(String token_device) {
        this.token_device = token_device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getUser_device() {
        return user_device;
    }

    public void setUser_device(String user_device) {
        this.user_device = user_device;
    }
}
