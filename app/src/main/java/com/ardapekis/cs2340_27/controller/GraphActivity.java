package com.ardapekis.cs2340_27.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Facade;
import com.ardapekis.cs2340_27.model.RatReportItem;
import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * GraphActivity class
 *
 */
public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        BarChart barChart = (BarChart) findViewById(R.id.graph);

        List<BarEntry> entries = convertDataSetToEntry(Facade.getInstance().getItemsInRange());

        BarDataSet dataset = new BarDataSet(entries, "# of Rat Sightings");

        Log.d("APP", "Made dataset with : " + entries.size());

        BarData data = new BarData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //

        barChart.setData(data);
        barChart.animateY(1000);

        // get array of String labels based on date range
        final String[] xLabels = getXLabels();

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabels[(int) value];
            }
        };

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        barChart.getDescription().setText("Rat Reports per Month");

    }

    /**
     * Fills an array with month and year strings based on the date range specified in facade
     *
     * @return      array of strings with month/year labels
     */
    public static String[] getXLabels() {
        String[] labels = new String[Facade.getInstance().getDateRange()];
        Calendar cal = Calendar.getInstance();
        cal.setTime(Facade.getInstance().getDate1());
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        for (int i = 0; i < Facade.getInstance().getDateRange(); i++) {
            labels[i] = (((month + i) % 12) + 1) + "/" + year;
            if (((month + i) % 12) + 1 == (12)) {
                year++;
            }
        }
        return labels;
    }

    /**
     * Converts the list of ratReportItems into a list of BarEntry
     * @param data      List of RatReportItem
     * @return          List of BarEntry
     */
    private List<BarEntry> convertDataSetToEntry(List<RatReportItem> data) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < Facade.getInstance().getDateRange(); i++) {
            entries.add(i, new BarEntry(i, 0f));
        }
        Date date1 = Facade.getInstance().getDate1();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int month1 = cal.get(Calendar.MONTH);
        int year1 = cal.get(Calendar.YEAR);

        // if not in entries, add new, else set
        for (RatReportItem d : data) {
            cal.setTime(d.getCreatedDate());
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            int index = (year - year1) * 12 + (month - month1);
            BarEntry old = entries.get(index);
            float oldY = old.getY();
            old.setY(oldY + 1);
        }

        return entries;
    }
}
