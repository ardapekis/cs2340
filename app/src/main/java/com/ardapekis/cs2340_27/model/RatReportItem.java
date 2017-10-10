package com.ardapekis.cs2340_27.model;

import java.util.Date;

/**
 * Information holder for rat reports
 */

public class RatReportItem {
    private int key;
    private Date createdDate;
    private Location location;

    public int getKey() {
        return key;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Location getLocation() {
        return location;
    }

    public String getAddressString() {
        return location.getNumberAddress();
    }

    public RatReportItem(int key, Date createdDate, Location location) {
        this.key = key;
        this.createdDate = createdDate;
        this.location = location;
    }
}
