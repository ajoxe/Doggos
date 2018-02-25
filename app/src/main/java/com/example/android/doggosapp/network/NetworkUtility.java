package com.example.android.doggosapp.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.doggosapp.model.Dog;
import com.example.android.doggosapp.model.Dogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public class NetworkUtility {
    Dogs dogs = new Dogs();
    private static List<Dog> dogList;
    private static Dog dog;




    public List<Dog> getAllDogsResponseCall(String base_url, String breed, String endpoint) {

        Call<Dogs> getAllDogs = RetroFitInstance.getInstance()
                .getApi()
                .getDogs(breed);

        getAllDogs.enqueue(new Callback<Dogs>() {
            @Override
            public void onResponse(Call<Dogs> call, Response<Dogs> response) {
               /* if (response.isSuccessful()){
                    dogs = response.body();
                    dogList = new ArrayList<>();
                    for (String dog : dogs.getMessage()){
                        dogList.add(new Dog(dog));
                    }
                }*/

            }

            @Override
            public void onFailure(Call<Dogs> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return dogList;
    }

    public Dog getRandomResponseCall(String base_url, String breed, String endpoint) {
        Log.d("dog", "dog call");


        Call<Dog> getRandomDog = RetroFitInstance.getInstance()
                .getApi()
                .getRandomDog(breed);

        getRandomDog.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                Log.d("dog", response.body().toString());
                if (response.isSuccessful()){
                    dog = response.body();
                    Log.d("dog", dog.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return dog;
    }
}
