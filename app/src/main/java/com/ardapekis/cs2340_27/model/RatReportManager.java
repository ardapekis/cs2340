package com.ardapekis.cs2340_27.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 10/5/2017.
 */

public class RatReportManager {
    public static final RatReportManager INSTANCE = new RatReportManager();

    private List<RatReportItem> reports;

    private RatReportManager() {
        reports = new ArrayList<>();
    }

    public void addItem(RatReportItem item) {
        reports.add(item);
    }

    public List<RatReportItem> getItems() {
        return reports;
    }

    public RatReportItem findItemById(int key) {
        for (RatReportItem d : reports) {
            if (d.getKey() == key) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + key);
        return null;
    }
}
