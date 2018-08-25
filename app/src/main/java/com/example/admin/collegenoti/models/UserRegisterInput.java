package com.example.admin.collegenoti.models;

/**
 * Created by Admin on 1/7/2018.
 */
public class UserRegisterInput {
    public String user_username;
    public String user_password;
    public String user_type;
    public String user_mobile_number;
    public String user_sem;
    public String user_urn;

    // ALT INSERT FOR Constructor

    public UserRegisterInput(String user_username, String user_password, String user_type, String user_mobile_number, String user_sem, String user_urn) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_type = user_type;
        this.user_mobile_number = user_mobile_number;
        this.user_sem = user_sem;
        this.user_urn = user_urn;
    }
}
