package com.example.android.doggosapp.network;

import com.example.android.doggosapp.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public class RetroFitInstance {
    private static String base_url = "https://dog.ceo/api/breed/";

    public static RetroFitInstance instance;


    public RetroFitInstance() {

    }


    public static RetroFitInstance getInstance() {
        if (instance == null) {
            instance = new RetroFitInstance();
        }
        return instance;
    }



    Retrofit getRetrofit() { // getting the retofit builder to use in other methods
        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public DogService getApi() {
        return getRetrofit().create(DogService.class);
    }

}
