package com.ardapekis.cs2340_27.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Facade;
import com.ardapekis.cs2340_27.model.RatReportItem;
//import com.ardapekis.cs2340_27.model.RatReportManager;

/**
 * Shown when user clicks on a rat report
 */

public class RatReportDetailActivity extends AppCompatActivity {
    /** keys for the intent */
    public static final String ARG_ITEM_ID = "key";
    public static final String ARG_SORT = "sort";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        RatReportItem mItem = Facade.getInstance().getReportManager().findItemByKey(getIntent().getIntExtra(ARG_ITEM_ID, 0), getIntent().getStringExtra(ARG_SORT));
        if (mItem != null) {
            Log.d("MYAPP", "Getting ready to set id");
            ((TextView) findViewById(R.id.key)).setText("Key: " + mItem.getKey());
            Log.d("MYAPP", "Getting ready to set name");
            ((TextView) findViewById(R.id.date)).setText("Created Date: " + mItem.getCreatedDate().toString());
            ((TextView) findViewById(R.id.location_type)).setText("Location Type: " + mItem.getLocation().getAddress().getLocationType());
            ((TextView) findViewById(R.id.address)).setText("Address: " + mItem.getAddressString());
            ((TextView) findViewById(R.id.zipcode)).setText("Zipcode: " + mItem.getLocation().getAddress().getZipcode());
            ((TextView) findViewById(R.id.city)).setText("City: " + mItem.getLocation().getAddress().getCity());
            ((TextView) findViewById(R.id.borough)).setText("Borough: " + mItem.getLocation().getAddress().getBorough());
            ((TextView) findViewById(R.id.coordinates)).setText("Coordinates:\n" + mItem.getLocation().getCoordinates().getLatitude() + ", " + mItem.getLocation().getCoordinates().getLongitude());
        }
    }


}
