package com.example.android.doggosapp.network;

import android.content.Context;
import android.util.Log;

import com.example.android.doggosapp.model.Dog;
import com.example.android.doggosapp.model.Dogs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public class NetworkUtility {
    private static NetworkUtility utility;
    Dogs dogs = new Dogs();
    private static List<Dog> dogList;
    private static Dog dog;

    public static NetworkUtility getUtility(){
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
                if (response.isSuccessful()){


                    listener.updateUI(response.body().getMessage());

                    /*if (dogs != null) {
                        for (String dog : dogs.getMessage()) {
                            Dog thisDog = new Dog(dog);
                            dogList.add(thisDog);
                            Log.d("doglist", thisDog.getMessage());
                        }*/
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
                Log.d("dog", response.body().toString());
                if (response.isSuccessful()){
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
