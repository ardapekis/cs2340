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
    public void testAddXLabels() throws Exception {
        String[] test = GraphActivity.;
        assertEquals(4, 2 + 2);
    }
}