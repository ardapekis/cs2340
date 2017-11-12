package com.ardapekis.cs2340_27.controller;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ardapekis.cs2340_27.R;
import com.ardapekis.cs2340_27.model.Facade;
//import com.ardapekis.cs2340_27.model.Location;
import com.ardapekis.cs2340_27.model.RatReportItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//import java.util.Date;
import java.util.List;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Facade mFacade = Facade.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFacade = Facade.getInstance();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Setting a click event handler for the map, unused currently
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//
//
//                // Clears the previously touched position
//                // mMap.clear();
//                //mFacade.getReportManager().addItem("newly added", "Bobs Place", new Location(latLng.latitude, latLng.longitude));
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//                markerOptions.title(mFacade.getReportManager().getLastReport().getLocation().getAddressString());
//                markerOptions.snippet(mFacade.getReportManager().getLastReport().getLocation().getAddress().getLocationType());
//
//                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
//            }
//        });

        // Get the list of items within the date range and show them on the map
        List<RatReportItem> reportList = mFacade.getItemsInRange();
        LatLng loc = new LatLng(0, 0);
        for (RatReportItem r : reportList) {
            loc = new LatLng(r.getLocation().getCoordinates().getLatitude(), r.getLocation().getCoordinates().getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(r.getLocation().getAddressString()).snippet(r.getCreatedDate().toString()));

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//        RatReportItem item = mFacade.getReportManager().getLastReport();
//        LatLng loc = new LatLng(item.getLocation().getCoordinates().getLatitude(), item.getLocation().getCoordinates().getLongitude());
//        mMap.addMarker(new MarkerOptions().position(loc).title(item.getLocation().getAddressString()).snippet(item.getLocation().getAddress().getLocationType()));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        CustomInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
