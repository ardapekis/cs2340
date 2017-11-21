package com.ardapekis.cs2340_27;

import com.ardapekis.cs2340_27.model.Location;
import com.ardapekis.cs2340_27.model.RatReportItem;
import com.ardapekis.cs2340_27.model.RatReportManager;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Date;

/**
 *
 * Test of findItemByKey in RatReportManager
 *
 * Created by David on 11/12/2017.
 *
 * @author David Herszenhaut
 * @version 1.0
 *
 */

public class DavidHerszenhautTest {

    /*
     *
     * create new
     *
     *  - ratreportmanager
     *  - ratreportitem
     *  - location
     *  - createddate
     *  - key
     *
     */

    RatReportManager ratReportManager = new RatReportManager();

    @Test
    public void testInvalidKey() throws Exception {

        RatReportItem result = ratReportManager.findItemByKey(-1, "old");
        assertEquals(null, result);

    }

    @Test
    public void testValidKeyNotInList() throws Exception {

        int newKey = ratReportManager.getNewKey();
        RatReportItem result = ratReportManager.findItemByKey(newKey, "old");
        assertEquals(null, result);

    }

    @Test
    public void testValidKeyInList() throws Exception {

        Date createdDate = new Date();
        String locationType = "locationType";
        int zipcode = 0;
        String address = "address";
        String city = "city";
        String borough = "borough";
        double latitude = 0.0;
        double longitude = 0.0;
        Location location = new Location(locationType, zipcode, address, city,
                borough, latitude, longitude);
        RatReportItem ratReportItem = new RatReportItem(999999999, createdDate, location);

        ratReportManager.addItem(ratReportItem);
        RatReportItem result = ratReportManager.findItemByKey(999999999, "old");
        assertEquals(ratReportItem, result);

    }

}