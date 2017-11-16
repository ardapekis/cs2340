package com.ardapekis.cs2340_27.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Facade;
import com.ardapekis.cs2340_27.model.UserManager;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * UI references
     */
    private EditText mUsernameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        // setup login button listener
        Button mLogInButton = (Button) findViewById(R.id.login_button);
        mLogInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // setup cancel button listener
        Button mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    /**
     * when cancel is pressed, closes activity
     */
    private void cancel() {
        finish();
    }

    /**
     * Attempts to login the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(username,
                password)) {
            mPasswordView.setError(getString(R.string
                    .error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Sets the currently logged in user and clears the activity stack
            UserManager userManager = Facade.getInstance().getUserManager();
            userManager.setLoggedInUser(userManager.getUser(username));
            Intent intent = new Intent(this, AppActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                    .FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

    /**
     * Checks that the username and password are a valid combination
     *
     * @param username The username to check
     * @param password The password to check
     * @return True if valid, false if not
     */
    private boolean isPasswordValid(String username, String password) {
        UserManager userManager = Facade.getInstance().getUserManager();
        return userManager.checkUserCredentials(username, password);
    }
}

