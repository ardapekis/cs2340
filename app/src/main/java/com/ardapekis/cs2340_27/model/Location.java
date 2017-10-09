package com.ardapekis.cs2340_27.model;

/**
 * Created by jason on 10/5/2017.
 */

public class Location {
    private LocationType locationType;
    private Address address;
    private Coordinates coordinates;

    public Location(LocationType locationType, Address address,
                    Coordinates coordinates) {
        this.locationType = locationType;
        this.address = address;
        this.coordinates = coordinates;
    }

    public enum LocationType {
        THREE_PLUS_MIXED_USE ("3+ Family Mixed Use Building"),
        COMMERCIAL ("Commercial Building"),
        ONE_TWO_FAMILY ("1-2 Family Dwelling"),
        THREE_PLUS_FAMILY_APT ("3+ Family Apt. Building"),
        PUBLIC_STAIRS ("Public Stairs"),
        OTHER ("Other (Explain Below)"),
        VACANT_LOT ("Vacant Lot"),
        CONSTRUCTION_SITE ("Construction Site"),
        HOSPITAL ("Hospital"),
        PARKING_LOT_GARAGE ("Parking Lot/Garage"),
        CATCH_BASIN_SEWER ("Catch Basin/Sewer"),
        VACANT_BUILDING ("Vacant Building"),
        ONE_TWO_MIXED_USE ("1-2 Family Mixed Use Building"),
        PUBLIC_GARDEN ("Public Garden"),
        GOVERNMENT ("Government Building"),
        OFFICE ("Office Building"),
        SCHOOL_PRE_SCHOOL ("School/Pre-School"),
        DAY_CARE_NURSERY ("Day Care/Nursery"),
        SRO ("Single Room Occupancy"),
        SUMMER_CAMP ("Summer Camp");

        private String display;
        LocationType(String display) {
            this.display = display;
        }

        public String toString() {
            return display;
        }
    }

    private class Address {
        private int zipcode;
        private int number;
        private String streetName;
        private String city;
        private String borough;

        public int getZipcode() {
            return zipcode;
        }

        public int getNumber() {
            return number;
        }

        public String getStreetName() {
            return streetName;
        }

        public String getCity() {
            return city;
        }

        public String getBorough() {
            return borough;
        }

        public Address(int zipcode, int number, String streetName,
                       String city, String borough) {
            this.zipcode = zipcode;
            this.number = number;
            this.streetName = streetName;
            this.city = city;
            this.borough = borough;
        }
    }

    private class Coordinates {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
