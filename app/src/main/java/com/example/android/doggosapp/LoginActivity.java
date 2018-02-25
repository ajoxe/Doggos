package com.example.android.doggosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences login;
    String SHARED_PREFS_KEY;
    String USER_KEY;
    Intent breedsIntent;
    EditText usernameEditText, passwordEditText;
    TextView errorMessageTV;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SHARED_PREFS_KEY = getResources().getString(R.string.shared_preferences_key);
        USER_KEY = getResources().getString(R.string.username_key);

        login = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        breedsIntent = new Intent(LoginActivity.this, BreedsActivity.class);
        if (login.getString(USER_KEY, null) != null){
            startActivity(breedsIntent);
        }
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        submitButton = (Button) findViewById(R.id.submit_button);
        errorMessageTV = (TextView) findViewById(R.id.error_message_tv);



        //TODO shared prefs add username
        //TODO login logic
    }

    public void loginOnClick(View view){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (validLogin(username, password)){
            SharedPreferences.Editor prefEditor = login.edit();
            prefEditor.putString(USER_KEY, username);
            startActivity(breedsIntent);
        }
    }

    private boolean passwordContainsUsername(String password, String username){
        return password.contains(username);
    }

    private boolean usernameIsNull(){
        return usernameEditText.getText().toString().equals("");
    }

    private boolean passwordIsNull(){
        return passwordEditText.getText().toString().equals("");
    }

    private boolean loginNotNull(){
       return !usernameIsNull() && !passwordIsNull();
    }

    private boolean validLogin(String username, String password){
        if (usernameIsNull() && passwordIsNull()){
            errorMessageTV.setVisibility(View.VISIBLE);
            errorMessageTV.setText(getResources().getString(R.string.null_username_password_error));
            return false;
        }
        if (usernameIsNull()){
            errorMessageTV.setVisibility(View.VISIBLE);
            errorMessageTV.setText(getResources().getString(R.string.null_username_error));
            return false;
        }

        if (passwordIsNull()){
            errorMessageTV.setVisibility(View.VISIBLE);
            errorMessageTV.setText(getResources().getString(R.string.null_password_error));
            return false;
        }
        if (passwordContainsUsername(password, username)){
            errorMessageTV.setVisibility(View.VISIBLE);
            errorMessageTV.setText(getResources().getString(R.string.password_error));
            return false;
        }
        return true;
    }
}
