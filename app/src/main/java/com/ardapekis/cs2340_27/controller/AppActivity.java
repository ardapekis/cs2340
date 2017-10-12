package com.ardapekis.cs2340_27.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Location;
import com.ardapekis.cs2340_27.model.RatReportItem;
import com.ardapekis.cs2340_27.model.RatReportManager;
import com.ardapekis.cs2340_27.model.UserManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Shows list of all rat reports
 */
public class AppActivity extends AppCompatActivity {

    /** Singleton instance of RatReportManager */
    RatReportManager manager = RatReportManager.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // only load if not already loaded
        if (!manager.getLoaded()) {
            FileReader reader = new FileReader();
            reader.execute();
        }
        View recyclerView = findViewById(R.id.recycler_list);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view
        setupRecyclerView((RecyclerView) recyclerView);
    }

    /**
     * AsyncTask that reads in the rat_sightings.csv file data and stores it in
     * the RatReportManager singleton
     */
    private class FileReader extends AsyncTask<Void, Integer, Boolean> {

        private Boolean resp;
        ProgressDialog progressDialog;

        @Override
        protected Boolean doInBackground(Void... params) {
            int numLoaded = 0;
            try {
                InputStream is = getResources().openRawResource(R.raw.rat_sightings);
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                String line;
                br.readLine(); //get rid of header line
                DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);
                Date createdDate = null;
                Location location;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    int key = Integer.parseInt(tokens[0]);
                    try {
                        createdDate = format.parse(tokens[1]);
                    } catch (ParseException e) {
                        Log.d("AppActivity", "parseException");
                    }
                    // trying to parse data with a couple weird entries
                    if (tokens.length <= 50) {
                        if (tokens[8].length() == 0 || tokens[8].equals("N/A")) {
                            location = new Location(tokens[7], 0, tokens[9], tokens[16], tokens[23], 0, 0);
                        } else {
                            location = new Location(tokens[7], Integer.valueOf(tokens[8]), tokens[9], tokens[16], tokens[23], 0, 0);
                        }
                    } else {
                        if (tokens[8].length() == 0 || tokens[8].equals("N/A")) {
                            location = new Location(tokens[7], 0, tokens[9], tokens[16], tokens[23], Double.valueOf(tokens[49]), Double.valueOf(tokens[50]));
                        } else {
                            location = new Location(tokens[7], Integer.valueOf(tokens[8]), tokens[9], tokens[16], tokens[23], Double.valueOf(tokens[49]), Double.valueOf(tokens[50]));
                        }
                    }
                    manager.addItem(new RatReportItem(key, createdDate, location));
                    publishProgress(++numLoaded); // Calls onProgressUpdate()
                }
                br.close();
                resp = true;
            } catch (Exception e) {
                e.printStackTrace();
                resp = false;
            }
            return resp;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            manager.setLoaded(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AppActivity.this);
            progressDialog.setMessage("Loaded:");
            progressDialog.show();
        }


        @Override
        protected void onProgressUpdate(Integer... loaded) {
            progressDialog.setMessage("Loaded: " + loaded[0]);
        }
    }

    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RatReportItemRecyclerViewAdapter(manager.getItems()));
    }

    public class RatReportItemRecyclerViewAdapter
            extends RecyclerView.Adapter<RatReportItemRecyclerViewAdapter.ViewHolder> {

        private final List<RatReportItem> mValues;

        public RatReportItemRecyclerViewAdapter(List<RatReportItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mDateView.setText(mValues.get(position).getCreatedDate().toString());
            holder.mAddressView.setText(mValues.get(position).getAddressString());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RatReportDetailActivity.class);
                    Log.d("MYAPP", "Switch to detailed view for item: " + holder.mItem.getKey());
                    intent.putExtra(RatReportDetailActivity.ARG_ITEM_ID, holder.mItem.getKey());

                    context.startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mDateView;
            public final TextView mAddressView;
            public RatReportItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mDateView = (TextView) view.findViewById(R.id.date);
                mAddressView = (TextView) view.findViewById(R.id.address);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mAddressView.getText() + "'";
            }
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
