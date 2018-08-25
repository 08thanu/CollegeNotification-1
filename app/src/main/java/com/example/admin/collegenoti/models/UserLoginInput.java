package com.example.admin.collegenoti.models;

/**
 * Created by Admin on 1/7/2018.
 */

public class UserLoginInput {
    public String username;
    public String password;

    /*====CONSTRUCTOR====ALT + INSERT*/
    public UserLoginInput(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
