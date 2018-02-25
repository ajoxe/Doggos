package com.example.android.doggosapp.model;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public class Dog {
    String message;

    public Dog (){

    }

    public Dog (String dog){
        this.message = dog;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
