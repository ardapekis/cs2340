package com.ardapekis.cs2340_27.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Facade;

import java.io.File;

/**
 * The welcome screen first showed when app is launched, provides options to
 * login an existing user or register a new user
 */
public class WelcomeActivity extends AppCompatActivity {
    File filesDir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Facade facade = Facade.getInstance();
        filesDir = this.getFilesDir();
        facade.loadUsers(new File(filesDir, Facade.USER_JSON_FILE_NAME));
    }



    /**
     * Handles when the login button is pressed
     * @param view      The view calling this method
     */
    public void loginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Handles when the register button is pressed
     * @param view      The view calling this method
     */
    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
