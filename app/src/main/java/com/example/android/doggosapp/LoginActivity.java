package com.example.android.doggosapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        if (login.getString(USER_KEY, null) != null) {
            startActivity(breedsIntent);
        }
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        submitButton = (Button) findViewById(R.id.submit_button);
        errorMessageTV = (TextView) findViewById(R.id.error_message_tv);


        //TODO shared prefs add username
        //TODO login logic
    }

    public void loginOnClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (validateLogin(username, password)) {
            startActivity(breedsIntent);
        }
    }

    private boolean validateLogin(String username, String password) {
        if (usernameIsNull() && passwordIsNull()) {
            errorAlert(getResources().getString(R.string.null_username_password_error));
            return false;
        }
        if (usernameIsNull()) {
            errorAlert(getResources().getString(R.string.null_username_error));
            return false;
        }

        if (passwordIsNull()) {
            errorAlert(getResources().getString(R.string.null_password_error));
            return false;
        }
        if (passwordContainsUsername(password, username)) {
            errorAlert(getResources().getString(R.string.password_error));
            return false;
        }

        SharedPreferences.Editor prefEditor = login.edit();
        prefEditor.putString(USER_KEY, username);
        prefEditor.commit();
        return true;
    }

    public void errorAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("!");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    private boolean passwordContainsUsername(String password, String username) {
        return password.contains(username);
    }

    private boolean usernameIsNull() {
        return usernameEditText.getText().toString().equals("");
    }

    private boolean passwordIsNull() {
        return passwordEditText.getText().toString().equals("");
    }
}
