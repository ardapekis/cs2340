package com.ardapekis.cs2340_27.model;

import android.util.Log;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Information holder for rat reports
 */

public class RatReportItem {
    private int key;
    private Date createdDate;
    private Location location;
    private String author;

    /**
     *
     * Getter
     *
     * @return the key of the RatReportItem
     */
    public int getKey() {
        return key;
    }

    /**
     *
     * Getter
     *
     * @return the createdDate of the RatReportItem
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * Getter
     *
     * @return the location of the RatReportItem
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * Getter
     *
     * @return the AddressString of the RatReportItem
     */
    public String getAddressString() {
        return location.getAddressString();
    }

    /**
     *
     * Getter
     *
     * @return the author of the RatReportItem
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Creates a new RatReportItem
     *
     * @param key           Unique int key
     * @param createdDate   createdDate as a Date object
     * @param location      Location as a Location object
     */
    public RatReportItem(int key, Date createdDate, Location location) {
        this(key, createdDate, location, "Bob Waters");
    }

    /**
     * Creates a new RatReportItem with known author
     *
     * @param key           Unique int key
     * @param createdDate   createdDate as a Date object
     * @param location      Location as a Location object
     * @param author        The author of the report
     */
    public RatReportItem(int key, Date createdDate, Location location, String author) {
        this.key = key;
        this.createdDate = createdDate;
        this.location = location;
        this.author = author;
    }


    /**
     * Saves this item's data into a file using the passed in PrintWriter
     * @param printWriter   the PrintWriter used to write the file
     */
    public void saveAsText(PrintWriter printWriter) {
        String locationType = location.getAddress().getLocationType();
        String zipcode = Integer.toString(location.getAddress().getZipcode());
        String address = location.getAddress().getAddress();
        String city = location.getAddress().getCity();
        String borough = location.getAddress().getBorough();
        String latitude = Double.toString(location.getCoordinates().getLatitude());
        String longitude = Double.toString(location.getCoordinates().getLongitude());
        printWriter.println(key + "\t" + createdDate.toString() + "\t" + locationType + "\t" + zipcode + "\t" + address
                + "\t" + city + "\t" + borough + "\t" + latitude + "\t" + longitude);
    }

    /**
     * Get the text written when saving
     * @return      the text String to be written
     */
    public String getSaveText() {
        String locationType = location.getAddress().getLocationType();
        String zipcode = Integer.toString(location.getAddress().getZipcode());
        String address = location.getAddress().getAddress();
        String city = location.getAddress().getCity();
        String borough = location.getAddress().getBorough();
        String latitude = Double.toString(location.getCoordinates().getLatitude());
        String longitude = Double.toString(location.getCoordinates().getLongitude());
        return (key + "\t" + createdDate.toString() + "\t" + locationType + "\t" + zipcode + "\t" + address
                + "\t" + city + "\t" + borough + "\t" + latitude + "\t" + longitude);
    }

    /**
     * Static method turning the text data into a new RatReportItem
     * @param line      The line of the file with each item's data
     * @return          A new ratreportitem
     */
    public static RatReportItem parseEntry(String line) {
        assert line != null;
        String[] tokens = line.split("\t");
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = null;
        try {
            date = format.parse(tokens[1]);
        } catch (ParseException e) {
            Log.d("AppActivity", "parseException");
        }
        Location location = new Location(tokens[2], Integer.valueOf(tokens[3]), tokens[4], tokens[5], tokens[6], Double.valueOf(tokens[7]), Double.valueOf(tokens[8]));
        RatReportItem s = new RatReportItem(Integer.valueOf(tokens[0]), date, location);

        return s;
    }
}
