package com.ardapekis.cs2340_27.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Facade;
import com.ardapekis.cs2340_27.model.Location;
import com.ardapekis.cs2340_27.model.RatReportItem;
import com.ardapekis.cs2340_27.model.RatReportManager;
import com.ardapekis.cs2340_27.model.UserManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Shows list of all rat reports
 */
public class AppActivity extends AppCompatActivity {

    Context context;
    Facade facade;
    /** Singleton instance of RatReportManager */
    RatReportManager manager;

    /** references to recyclerview elements for updating */
    RecyclerView recyclerView;
    RatReportItemRecyclerViewAdapter adapter;

    File filesDir;

    /** flag for type of sort */
    private String sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        filesDir = this.getFilesDir();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRatReportItemDialog();
            }
        });
        facade = Facade.getInstance();
        manager = facade.getReportManager();
        adapter = new RatReportItemRecyclerViewAdapter(manager.getItemsQueue());
        sort = "new";
        // only load if not already loaded
        File file = new File(filesDir, Facade.REPORT_JSON_FILE_NAME);
        if (!file.exists()) {
            FileReader reader = new FileReader();
            reader.execute();
        } else {
            facade.loadReports(file, this, adapter);
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);
    }

    /**
     * Creates a dialog to enter a date range for the map
     */
    private void setDateDialog(final Class intentClass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Date Range");
        LayoutInflater inflater = this.getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.dialog_date, null);
        final Spinner month1 = (Spinner) viewInflated.findViewById(R.id.month1);
        final Spinner day1 = (Spinner) viewInflated.findViewById(R.id.day1);
        final Spinner year1 = (Spinner) viewInflated.findViewById(R.id.year1);
        final Spinner month2 = (Spinner) viewInflated.findViewById(R.id.month2);
        final Spinner day2 = (Spinner) viewInflated.findViewById(R.id.day2);
        final Spinner year2 = (Spinner) viewInflated.findViewById(R.id.year2);
        String[] monthsArray = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayList<String> months = new ArrayList<>(Arrays.asList(monthsArray));
        String[] daysArray = new String[31];
        for (int i = 1; i < 32; i++) {
            if (i < 10) {
                daysArray[i - 1] = "0" + Integer.toString(i);
            } else {
                daysArray[i - 1] = Integer.toString(i);
            }
        }
        ArrayList<String> days = new ArrayList<>(Arrays.asList(daysArray));
        String[] yearsArray = new String[118];
        for (int i = 117; i >= 0; i--) {
            yearsArray[117 - i] = Integer.toString(i + 1900);
        }
        ArrayList<String> years = new ArrayList<>(Arrays.asList(yearsArray));
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month1.setAdapter(adapter);
        month2.setAdapter(adapter);
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day1.setAdapter(adapter);
        day2.setAdapter(adapter);
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year1.setAdapter(adapter);
        year2.setAdapter(adapter);

        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String m1 = (String) month1.getSelectedItem();
                String d1 = (String) day1.getSelectedItem();
                String y1 = (String) year1.getSelectedItem();
                String m2 = (String) month2.getSelectedItem();
                String d2 = (String) day2.getSelectedItem();
                String y2 = (String) year2.getSelectedItem();
                DateFormat format = new SimpleDateFormat("MMddyyyy", Locale.US);
                String date1String = m1 + d1 + y1;
                String date2String = m2 + d2 + y2;
                Date date1 = new Date();
                Date date2 = new Date();
                try {
                    date1 = format.parse(date1String);
                    date2 = format.parse(date2String);
                    if (date2.before(date1)) {
                        Toast toast = Toast.makeText(context, "Date invalid", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Intent intent = new Intent(context, intentClass);
                        facade.setDate1(date1);
                        facade.setDate2(date2);
                        startActivity(intent);
                    }
                } catch (ParseException e) {
                    Log.d("fuck", "fuuuuuck");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    /**
     * Creates a dialog with text entry for a new report with ok and cancel
     * buttons
     */
    private void addRatReportItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Item");

        LayoutInflater inflater = this.getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.dialog_add_item, null);
        final EditText locationType = (EditText) viewInflated.findViewById(R.id.location_type);
        final EditText address = (EditText) viewInflated.findViewById(R.id.address);
        final EditText city = (EditText) viewInflated.findViewById(R.id.city);
        final EditText zipcode = (EditText) viewInflated.findViewById(R.id.zipcode);
        final EditText borough = (EditText) viewInflated.findViewById(R.id.borough);
        final EditText latitude = (EditText) viewInflated.findViewById(R.id.latitude);
        final EditText longitude = (EditText) viewInflated.findViewById(R.id.longitude);
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (locationType.getText().toString().length() == 0 ||
                    address.getText().toString().length() == 0 ||
                    zipcode.getText().toString().length() == 0 ||
                    city.getText().toString().length() == 0 ||
                    borough.getText().toString().length() == 0 ||
                    latitude.getText().toString().length() == 0 ||
                    longitude.getText().toString().length() == 0) {
                    dialog.cancel();
                    //File file = new File(filesDir, PersistenceManager.DEFAULT_TEXT_FILE_NAME);
                    //model.saveText(file);
                } else {
                    RatReportItem item;
                    Date date = new Date();
                    Location location = new Location(locationType.getText().toString(),
                            Integer.valueOf(zipcode.getText().toString()), address.getText().toString(),
                            city.getText().toString(), borough.getText().toString(),
                            Double.valueOf(latitude.getText().toString()), Double.valueOf(longitude.getText().toString()));
                    item = new RatReportItem(manager.getNewKey(), date, location);
                    manager.addItem(item);
                    manager.addItemToFront(item);
                    facade.saveReports(new File(filesDir, Facade.REPORT_JSON_FILE_NAME));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.show();
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
                PrintWriter pw = new PrintWriter(new File(filesDir, Facade.REPORT_JSON_FILE_NAME));
                PrintWriter pw2 = new PrintWriter(new File(filesDir, Facade.REPORT_JSON_FILE_NAME));
                pw.println();
                String line;
                br.readLine(); //get rid of header line
                DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);
                Date createdDate = null;
                Location location;
                while ((line = br.readLine()) != null) {
                    if (isCancelled()) {
                        return false;
                    }
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
                    RatReportItem item = new RatReportItem(key, createdDate, location);
                    manager.addItemToFront(item);
                    manager.addItem(item);
                    item.saveAsText(pw);
                    publishProgress(++numLoaded); // Calls onProgressUpdate()
                }
                br.close();
                pw.close();
                pw2.println(manager.getItems().size());
                pw2.close();
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
            adapter.notifyDataSetChanged();
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AppActivity.this);
            progressDialog.setMessage("Loaded:");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    cancel(true);
                    Log.d("Cancel", "tried to cancel");
                }
            });
            progressDialog.show();
        }


        @Override
        protected void onProgressUpdate(Integer... loaded) {
            progressDialog.setMessage("Loaded: " + loaded[0]);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AppActivity.this, "Load Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(adapter);
    }

    public class RatReportItemRecyclerViewAdapter
            extends RecyclerView.Adapter<RatReportItemRecyclerViewAdapter.ViewHolder> {

        private final List<RatReportItem> mValues;

        /**
         *
         * Sets value of mValues to items
         *
         * @param items list of RatReportItems
         */
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
                    intent.putExtra(RatReportDetailActivity.ARG_SORT, sort);
                    context.startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // public final View mView;
            // public final TextView mDateView;
            // public final TextView mAddressView;
            // public RatReportItem mItem;
            final View mView;
            final TextView mDateView;
            final TextView mAddressView;
            RatReportItem mItem;

            /**
             *
             * Sets values of mView, mDateView, and mAddressView
             *
             * @param view the view
             */
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
            case R.id.sort_old:
                sort = "old";
                adapter = new RatReportItemRecyclerViewAdapter(manager.getItems());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.sort_new:
                sort = "new";
                adapter = new RatReportItemRecyclerViewAdapter(manager.getItemsQueue());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.maps:
                setDateDialog(MapsActivity.class);
                return true;
            case R.id.graph:
                setDateDialog(GraphActivity.class);
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
        UserManager userManager = Facade.getInstance().getUserManager();
        userManager.setLoggedInUser(null);
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
