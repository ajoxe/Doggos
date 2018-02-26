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
import android.widget.ProgressBar;

import com.example.android.doggosapp.model.Dog;
import com.example.android.doggosapp.network.NetworkUtility;
import com.example.android.doggosapp.network.ResponseListener;
import com.example.android.doggosapp.network.RetroFitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsActivity extends AppCompatActivity {
    SharedPreferences login;
    String SHARED_PREFS_KEY;
    String USER_KEY;
    String username;
    String welcome;

    ImageView poodle, spaniel, retriever, terrier;
    ProgressBar loadingDogs;
    List<ImageView> imageViews = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);

        initializeSharedPrefs();
        username = login.getString(USER_KEY, null);

        getSupportActionBar().setTitle(welcome + username);
        setImageViews();

    }

    public void setImageViews(){
        loadingDogs = (ProgressBar) findViewById(R.id.breeds_progress_bar);
        poodle = (ImageView) findViewById(R.id.poodle_breed_card_iv);
        spaniel = (ImageView) findViewById(R.id.spaniel_breed_card_iv);
        retriever = (ImageView) findViewById(R.id.retriever_breed_card_iv);
        terrier = (ImageView) findViewById(R.id.terrier_breed_card_iv);
        imageViews.add(poodle);
        imageViews.add(spaniel);
        imageViews.add(retriever);
        imageViews.add(terrier);
        loadImage(imageViews);
    }

    public void getImage(String breed, final ImageView imageView){
        NetworkUtility utility = NetworkUtility.getUtility();
        utility.getRandomResponseCall(breed, new ResponseListener() {
            @Override
            public void updateUI(String... strings) {
                loadingDogs.setVisibility(View.GONE);
                Picasso.with(getApplicationContext())
                        .load(strings[0])
                        .resize(100, 100)
                        .into(imageView);
            }
        });
    }

    public void loadImage(List<ImageView> imageViews){
        for(ImageView view : imageViews){
            getImage(view.getTag().toString().toLowerCase(), view);
        }
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
