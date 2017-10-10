package com.ardapekis.cs2340_27.controller;

import android.content.Context;
import android.content.Intent;
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
import java.io.IOException;
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
 * Stand in class representing our app
 */
public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View recyclerView = findViewById(R.id.recycler_list);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void readSDFile() {
        RatReportManager model = RatReportManager.INSTANCE;

        try {
            InputStream is = getResources().openRawResource(R.raw.rat_sightings);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                Log.d(AppActivity.TAG, line);
                String[] tokens = line.split(",");
                int key = Integer.parseInt(tokens[0]);
                DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.ENGLISH);
                try {
                    Date createdDate = format.parse(tokens[1]);
                } catch (ParseException e) {
                    Log.d(AppActivity.TAG, "parseException");
                }
                Location location = new Location(tokens[7], Integer.valueOf(tokens[8]), Integer.valueOf(tokens[9]), )
                model.addItem(new RatReportItem());
            }
            br.close();
        } catch (IOException e) {
            Log.e(AppActivity.TAG, "error reading assets", e);
        }

    }

    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RatReportItemRecyclerViewAdapter(RatReportManager.INSTANCE.getItems()));
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

//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, RatReportItemDetailActivity.class);
//                    Log.d("MYAPP", "Switch to detailed view for item: " + holder.mItem.getId());
//                    intent.putExtra(RatReportItemDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
//
//                    context.startActivity(intent);
//                }
//            });
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
