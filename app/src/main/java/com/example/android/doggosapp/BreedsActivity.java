package com.example.android.doggosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.android.doggosapp.model.Dog;
import com.example.android.doggosapp.network.NetworkUtility;
import com.example.android.doggosapp.network.RetroFitInstance;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsActivity extends AppCompatActivity {
    SharedPreferences login;
    String SHARED_PREFS_KEY;
    String USER_KEY;
    String username;
    String welcome;
    String base_url;
    String endpoint;
    String imageUrl;

    String poodleImage, spanielImage, retrieverImage, terrierImage;

    ImageView poodle, spaniel, retriever, terrier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);
        initializeSharedPrefs();
        username = login.getString(USER_KEY, null);
        getSupportActionBar().setTitle(welcome + username);

        poodle = (ImageView) findViewById(R.id.poodle_breed_card_iv);
        spaniel = (ImageView) findViewById(R.id.spaniel_breed_card_iv);
        retriever = (ImageView) findViewById(R.id.retriever_breed_card_iv);
        terrier = (ImageView) findViewById(R.id.terrier_breed_card_iv);

        base_url = getResources().getString(R.string.api_base_url);
        endpoint = getResources().getString(R.string.api_endpoint_random);


        poodleImage = getRandomDogResponseCall("poodle", poodle);
        spanielImage = getRandomDogResponseCall("spaniel", spaniel);
        retrieverImage = getRandomDogResponseCall("retriever", retriever);
        Log.d("image", "url" + retrieverImage);
        terrierImage = getRandomDogResponseCall("terrier", terrier);

    }

    public String getRandomDogResponseCall(String breed, final ImageView view) {
        Log.d("dog", "dog call");


        Call<Dog> getRandomDog = RetroFitInstance.getInstance()
                .getApi()
                .getRandomDog(breed);

        getRandomDog.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                Log.d("dog", response.body().toString());
                if (response.isSuccessful()){
                    Dog dog = response.body();
                    Log.d("dog", dog.getMessage());
                    imageUrl = dog.getMessage();
                }

                Picasso.with(getApplicationContext())
                        .load(imageUrl)
                        .into(view);

            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return imageUrl;
    }



    public void breedOnClick(View view) {
        String breed = view.getTag().toString();
        Log.d("breed", breed);
        Intent dogIntent = new Intent(BreedsActivity.this, DogsActivity.class);
        dogIntent.putExtra("breed", breed);
        startActivity(dogIntent);
    }



    public void initializeSharedPrefs() {
        SHARED_PREFS_KEY = getResources().getString(R.string.shared_preferences_key);
        USER_KEY = getResources().getString(R.string.username_key);
        login = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        welcome = getResources().getString(R.string.welcome_user);
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
        Intent loginIntent = new Intent(BreedsActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        return true;
    }
}
