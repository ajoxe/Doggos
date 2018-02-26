package com.example.android.doggosapp.network;


import android.util.Log;

import com.example.android.doggosapp.model.Dog;
import com.example.android.doggosapp.model.Dogs;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public class NetworkUtility {
    private static NetworkUtility utility;

    public static NetworkUtility getUtility() {
        if (utility == null) {
            utility = new NetworkUtility();
        }
        return utility;
    }


    public void getAllDogsResponseCall(String breed, final ResponseListener listener) {
        Call<Dogs> getAllDogs = RetroFitInstance.getInstance()
                .getApi()
                .getDogs(breed);

        getAllDogs.enqueue(new Callback<Dogs>() {
            @Override
            public void onResponse(Call<Dogs> call, Response<Dogs> response) {
                if (response.isSuccessful()) {
                    listener.updateUI(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Dogs> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void getRandomResponseCall(String breed, final ResponseListener listener) {
        Call<Dog> getRandomDog = RetroFitInstance.getInstance()
                .getApi()
                .getRandomDog(breed);

        getRandomDog.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {

                if (response.isSuccessful()) {
                    listener.updateUI(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
