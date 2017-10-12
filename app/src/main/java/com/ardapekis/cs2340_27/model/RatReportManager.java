package com.ardapekis.cs2340_27.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all rat reports
 */

public class RatReportManager {
    /** singleton design pattern */
    public static final RatReportManager INSTANCE = new RatReportManager();

    /** Used to prevent excess loading */
    private boolean loaded;

    private List<RatReportItem> reports;

    /**
     * Private constructor since singleton design
     */
    private RatReportManager() {
        reports = new ArrayList<>(110000);
        loaded = false;
    }

    /** Getters and setters */
    public void setLoaded(Boolean b) {
        loaded = b;
    }

    public boolean getLoaded() {
        return loaded;
    }

    /**
     * Add a new item to the list of rat reports
     * @param item      New rat report to add
     */
    public void addItem(RatReportItem item) {
        reports.add(item);
    }

    /**
     * Gets the list of rat reports
     * @return      The list of rat reporst
     */
    public List<RatReportItem> getItems() {
        return reports;
    }

    /**
     * Returns an item based on its unique key
     * @param key       The rat report's unique key
     * @return          The rat report corresponding to the key
     */
    public RatReportItem findItemByKey(int key) {
        for (RatReportItem d : reports) {
            if (d.getKey() == key) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + key);
        return null;
    }
}
