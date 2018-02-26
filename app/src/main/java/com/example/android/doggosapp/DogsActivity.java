package com.example.android.doggosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.doggosapp.dogsrecyclerview.DogAdapter;
import com.example.android.doggosapp.model.Dog;
import com.example.android.doggosapp.model.Dogs;
import com.example.android.doggosapp.network.RetroFitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogsActivity extends AppCompatActivity {
    SharedPreferences login;
    String SHARED_PREFS_KEY;
    String USER_KEY;
    String breed;
    View.OnClickListener dogClick;
    RecyclerView recyclerView;
    DogAdapter dogAdapter;
    List<Dog> dogList = new ArrayList<>();
    TextView brredTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);
        initializeSharedPrefs();
        Intent intent = getIntent();
        breed = intent.getStringExtra("breed");
        brredTV = (TextView) findViewById(R.id.breed_text_view);
        brredTV.setText(breed);
        getDogList(breed.toLowerCase());
        Configuration config = getResources().getConfiguration();
        setDogClick();

        recyclerView = (RecyclerView) findViewById(R.id.dog_recycler_view);
        dogAdapter = new DogAdapter(dogList, getApplicationContext(), dogClick);
        recyclerView.setAdapter(dogAdapter);

        if (config.orientation == 2){
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
    }
    public void setDogClick(){
        dogClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = v.getTag().toString();
                Intent intent = new Intent(DogsActivity.this, PhotoActivity.class);
                intent.putExtra("url",imageUrl);
                startActivity(intent);

            }
        };
    }

    public void getDogList(String breed){
        Call<Dogs> getAllDogs = RetroFitInstance.getInstance()
                .getApi()
                .getDogs(breed);

        getAllDogs.enqueue(new Callback<Dogs>() {
            @Override
            public void onResponse(Call<Dogs> call, Response<Dogs> response) {
                if (response.isSuccessful()){

                    Dogs dogs = response.body();
                    Log.d("doglist", "size" + dogs.getMessage().length);

                    if (dogs != null) {
                        for (String dog : dogs.getMessage()) {
                            Dog thisDog = new Dog(dog);
                            dogList.add(thisDog);
                            Log.d("doglist", thisDog.getMessage());
                        }
                    }

                    dogAdapter.notifyDataSetChanged();
                    Log.d("doglist", "size" + dogList.size());
                }

            }

            @Override
            public void onFailure(Call<Dogs> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    public void initializeSharedPrefs() {
        SHARED_PREFS_KEY = getResources().getString(R.string.shared_preferences_key);
        USER_KEY = getResources().getString(R.string.username_key);
        login = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = login.edit();
        editor.putString(USER_KEY, null);
        editor.commit();
        Intent loginIntent = new Intent(DogsActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        return true;
    }

}
