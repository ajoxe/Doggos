package com.example.android.doggosapp.network;

import com.example.android.doggosapp.model.Dog;
import com.example.android.doggosapp.model.Dogs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public interface DogService {
    @GET("{breed}/images")
    Call<Dogs> getDogs(@Path("breed") String breed);

    @GET("{breed}/images/random")
    Call<Dog> getRandomDog(@Path("breed") String breed);
}
