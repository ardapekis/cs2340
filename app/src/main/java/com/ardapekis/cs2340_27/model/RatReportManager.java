package com.ardapekis.cs2340_27.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles all rat reports
 */

public class RatReportManager {
    /** singleton design pattern */
    public static final RatReportManager INSTANCE = new RatReportManager();

    /** used to generate new keys */
    private int keySeed;

    /** Used to prevent excess loading */
    private boolean loaded;

    /** holds reports in an arraylist, sort by old */
    private List<RatReportItem> reports;

    /** holds reports in a linkedlist/queue, sort by new */
    private List<RatReportItem> reportsQueue;

    /**
     * Private constructor since singleton design
     */
    private RatReportManager() {
        reports = new ArrayList<>(110000);
        reportsQueue = new LinkedList<>();
        loaded = false;
        keySeed = 0;
    }

    /** Getters and setters */
    public void setLoaded(Boolean b) {
        loaded = b;
    }

    public boolean getLoaded() {
        return loaded;
    }

    /**
     * Add a new item to the list of rat reports, also updates keySeed
     * @param item      New rat report to add
     */
    public void addItem(RatReportItem item) {
        reports.add(item);
        if (item.getKey() > keySeed) {
            keySeed = item.getKey();
        }
    }

    /**
     * Add a new item to the front of the list, i.e. add to queue, also
     * updates keySeed
     *
     * @param item      New rat report to add
     */
    public void addItemToFront(RatReportItem item) {
        reportsQueue.add(0, item);
        if (item.getKey() > keySeed) {
            keySeed = item.getKey();
        }
    }

    /**
     * Uses keySeed to generate a new unique key
     *
     * @return      new unique key
     */
    public int getNewKey() {
        return ++keySeed;
    }

    /**
     * Gets the list of rat reports
     * @return      The list of rat reports, oldest first
     */
    public List<RatReportItem> getItems() {
        return reports;
    }

    /**
     * Gets the queue of rat reports
     * @return      The queue of rat reports, sorted newest first
     */
    public List<RatReportItem> getItemsQueue() { return reportsQueue; }

    /**
     * Returns an item based on its unique key
     * @param key       The rat report's unique key
     * @param sort      The flag for whether to search the queue or the list
     * @return          The rat report corresponding to the key
     */
    public RatReportItem findItemByKey(int key, String sort) {
        List<RatReportItem> list =  reportsQueue;
        switch (sort) {
            case "new":
                list = reportsQueue;
                break;
            case "old":
                list = reports;
                break;
        }
        for (RatReportItem d : list) {
            if (d.getKey() == key) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + key);
        return null;
    }
}
