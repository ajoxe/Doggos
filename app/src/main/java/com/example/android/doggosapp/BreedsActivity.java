package com.example.android.doggosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class BreedsActivity extends AppCompatActivity {
    SharedPreferences login;
    String SHARED_PREFS_KEY;
    String USER_KEY;
    String username;
    String welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);
        initializeSharedPrefs();
        username = login.getString(USER_KEY, null);
        getSupportActionBar().setTitle(welcome + username);

    }



    public void breedOnClick(View view) {
        String breed = view.getTag().toString();

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
        Intent loginIntent = new Intent(BreedsActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        return true;
    }
}
