package com.ardapekis.cs2340_27;

import com.ardapekis.cs2340_27.controller.GraphActivity;
import com.ardapekis.cs2340_27.model.Facade;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JasonTest {

    private Facade facade;

    @Before
    public void setUp() throws ParseException {
        facade = Facade.getInstance();
        DateFormat format = new SimpleDateFormat("MMddyyyy", Locale.US);
        facade.setDate1(format.parse("10012016"));
        facade.setDate2(format.parse("01012018"));
    }

    @Test
    public void testGetDateRange() throws Exception {
        assertEquals(16, facade.getDateRange());
    }

    @Test
    public void testGetDateRangeSameMonth() throws Exception {
        DateFormat format = new SimpleDateFormat("MMddyyyy", Locale.US);
        facade.setDate1(format.parse("10012016"));
        facade.setDate2(format.parse("10012016"));
        assertEquals(1, facade.getDateRange());
    }

    @Test
    public void testAddXLabelsSize() throws Exception {
        String[] test = GraphActivity.getXLabels();
        assertEquals(16, test.length);
    }

    @Test
    public void testAddXLabelsContents() throws Exception {
        String[] test = GraphActivity.getXLabels();
        String[] expected = {"10/2016", "11/2016", "12/2016", "1/2017",
                            "2/2017", "3/2017", "4/2017", "5/2017", "6/2017",
                            "7/2017", "8/2017", "9/2017", "10/2017", "11/2017",
                            "12/2017", "1/2018"};
        assertArrayEquals(expected, test);
    }
}