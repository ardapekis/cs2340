package com.ardapekis.cs2340_27.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Admin;
import com.ardapekis.cs2340_27.model.UserManager;

/**
 * Stand in class representing our app
 */
public class AppActivity extends AppCompatActivity {

    /** displays when app runs, shows if user is admin or regular user */
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sets message to user type
        UserManager userManager = UserManager.getInstance();
        message = (TextView) findViewById(R.id.sample_text);
        if (userManager.getLoggedInUser() instanceof Admin) {
            message.setText("Admin");
        } else {
            message.setText("User");
        }
    }

    /**
     * Adds the logout button in the menu
     * @param menu      The menu, provided by Android
     * @return          Success of action
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_options_menu, menu);
        return true;
    }

    /**
     * Handles the menu items' actions
     * @param item      The item selected
     * @return          Success of action
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Logs a user out by setting the currently logged in user to null, clearing
     * the activity stack, and returning to the welcome screen
     */
    private void logout() {
        UserManager userManager = UserManager.getInstance();
        userManager.setLoggedInUser(null);
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
