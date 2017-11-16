package com.ardapekis.cs2340_27;

import com.ardapekis.cs2340_27.model.Location;
import com.ardapekis.cs2340_27.model.RatReportManager;
import com.ardapekis.cs2340_27.model.RatReportItem;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit test for M10.
 * Tests the loadFromText method from RatReportManager.
 *
 * @author Arda Pekis
 * @version 1.0
 */
public class ArdaTest {

    @Test
    public void loadReports() {
        RatReportManager manager = new RatReportManager();
        RatReportItem report0 = (new RatReportItem(0, new Date(), new Location("a", 11, "001 Fake Street", "Atlanta", "Midtown", 23.01, 45.04)));
        RatReportItem report1 = (new RatReportItem(1, new Date(), new Location("a", 11, "001 Fake Street", "Atlanta", "Midtown", 23.01, 45.04)));
        RatReportItem report2 = (new RatReportItem(2, new Date(), new Location("a", 11, "001 Fake Street", "Atlanta", "Midtown", 23.01, 45.04)));

        String testText = "3\n" + report0.getSaveText() + "\n" + report1.getSaveText() + "\n" + report2.getSaveText() + "\n";
        BufferedReader testInput = new BufferedReader(new StringReader(testText));

        manager.loadFromText(testInput);

        for (int i = 0; i < 3; i++) {
            assertEquals("Wrong ordering in RatReportManager report list.", i, manager.getItems().get(i).getKey());
            assertEquals("Wrong ordering in RatReportManager report queue.", 2-i, manager.getItemsQueue().get(i).getKey());
        }

        try {
            testInput.ready();
            fail("Buffered Reader was not closed.");
        } catch (IOException x) {
            assertEquals("Stream closed", x.getMessage());
        }
    }

    @Test
    public void malformedData() {
        RatReportManager manager = new RatReportManager();
        RatReportItem report0 = (new RatReportItem(0, new Date(), new Location("a", 11, "001 Fake Street", "Atlanta", "Midtown", 23.01, 45.04)));
        RatReportItem report1 = (new RatReportItem(1, new Date(), new Location("a", 11, "001 Fake Street", "Atlanta", "Midtown", 23.01, 45.04)));

        String testText = "3\n" + report0.getSaveText() + "\n" + report1.getSaveText() + "\n";
        BufferedReader testInput = new BufferedReader(new StringReader(testText));

        manager.loadFromText(testInput);

        for (int i = 0; i < 2; i++) {
            assertEquals("Wrong ordering in RatReportManager report list.", i, manager.getItems().get(i).getKey());
            assertEquals("Wrong ordering in RatReportManager report queue.", 2-i, manager.getItemsQueue().get(i).getKey());
        }
    }

}
