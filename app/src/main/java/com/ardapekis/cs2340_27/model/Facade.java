package com.ardapekis.cs2340_27.model;

import android.util.Log;

import com.ardapekis.cs2340_27.controller.AppActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jason on 10/25/2017.
 * Facade class used to access the managers
 */

public final class Facade {
    // public final static String REPORT_JSON_FILE_NAME = "data.txt";
    public static final String REPORT_JSON_FILE_NAME = "data.txt";
    // public final static String USER_JSON_FILE_NAME = "user.json";
    public static final String USER_JSON_FILE_NAME = "user.json";
    // private RatReportManager reportManager;
    private final RatReportManager reportManager;
    private UserManager userManager;
    private Date date1;
    private Date date2;

    /** Singleton */
    // private static Facade instance = new Facade();
    // private final static Facade instance = new Facade();
    private static final Facade instance = new Facade();

    /**
     *
     * Getter
     *
     * @return RatReportManager
     */
    public RatReportManager getReportManager() {
        return reportManager;
    }

    /**
     *
     * Getter
     *
     * @return UserManager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     *
     * Setter
     *
     * @param date the date1 to be set
     */
    public void setDate1(Date date) {
        this.date1 = date;
    }

    /**
     *
     * Setter
     *
     * @param date the date2 to be set
     */
    public void setDate2(Date date) {
        this.date2 = date;
    }

    /**
     *
     * Getter
     *
     * @return date1
     */
    public Date getDate1() {
        return date1;
    }

    /**
     * Gets a list of items within the date range of date1 and date2
     * @return      list of reports in range
     */
    public List<RatReportItem> getItemsInRange() {
        List<RatReportItem> list = new ArrayList<>();
        for (RatReportItem r : reportManager.getItems()) {
            if ((r.getCreatedDate().compareTo(date1) >= 0)
                    && (r.getCreatedDate().compareTo(date2) <= 0)) {
                list.add(r);
            }
        }
        return list;
    }

    /**
     *
     * Gets range of dates
     *
     * @return integer representing range of dates
     */
    public int getDateRange() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int month1 = cal.get(Calendar.MONTH);
        int year1 = cal.get(Calendar.YEAR);
        cal.setTime(date2);
        int month2 = cal.get(Calendar.MONTH);
        int year2 = cal.get(Calendar.YEAR);
        return ((year2 - year1) * 12) + ((month2 - month1) + 1);
    }

    /**
     * private constructor for singleton
     */
    private Facade() {
        reportManager = new RatReportManager();
        userManager = new UserManager();
    }

    /**
     *
     * Getter
     *
     * @return Facade instance
     */
    public static Facade getInstance() {
        return instance;
    }

    /**
     * Saves reports in a text file, uses the reportManager saveAsText method
     * @param file      File to write reports into
     */
    public void saveReports(File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            reportManager.saveAsText(pw);
            pw.close();
        } catch (FileNotFoundException e) {
            Log.e("UserManagementFacade", "Failed to open json file for output");
        }

    }

    /**
     * Save users into file as JSON
     * @param file      The file to write into
     */
    public void saveUsers(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            /*
                We are using the Google Gson library to make things easy.  You will need to add the
                following line to your gradle file so the proper dependencies are set up:
                compile 'com.google.code.gson:gson:2.3'

                Gson, like object serialization will take a single object and save all the objects
                it refers to.  You can save everything by one reference, as long as it is the
                top-level reference.


             */
            Gson gson = new Gson();
            // convert our objects to a string for output
            String outString = gson.toJson(userManager);
            Log.d("DEBUG", "JSON Saved: " + outString);
            //then just write the string
            writer.println(outString);
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e("UserManagementFacade", "Failed to open json file for output");
        }

    }

    /**
     * Load users from JSON
     * @param file      The file to load from
     * @param adapter   adapter to refresh
     */
    public void loadReports(File file, AppActivity.RatReportItemRecyclerViewAdapter adapter) {
        try {
            //make an input object for reading
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reportManager.loadFromText(reader);
            adapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            Log.e("ModelSingleton", "Failed to open text file for loading!");
        }

    }

    /**
     * Read users from file
     * @param file      File to read from
     */
    public void loadUsers(File file) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            //Since we saved the json as a string, we just read in the string normally
            String inString = input.readLine();
            Log.d("DEBUG", "JSON: " + inString);
            //Then we use the Gson library to recreate the object references and links automagically
            Gson gson = new Gson();

            userManager = gson.fromJson(inString, UserManager.class);

            input.close();
        } catch (IOException e) {
            Log.e("UserManagementFacade", "Failed to open/read the buffered reader for json");
        }

    }
}
