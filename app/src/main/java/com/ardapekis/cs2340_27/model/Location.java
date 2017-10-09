package com.ardapekis.cs2340_27.model;

/**
 * Created by jason on 10/5/2017.
 */

public class Location {
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

    public enum AddressType {
        INTERSECTION,
        LATLONG,
        ADDRESS,
        BLOCKFACE,
        PLACENAME
    }

    class Address {
        private AddressType addressType;
        private int zipcode;
        private int number;
        private String streetName;
        private String crossStreet1;
        private String crossStreet2;
        private String intersectionStreet1;
        private String intersectionStreet2;
        private String city;
        private String landmark;
        private String community;
        private String borough;

        public AddressType getAddressType() {
            return addressType;
        }

        public int getZipcode() {
            return zipcode;
        }

        public int getNumber() {
            return number;
        }

        public String getStreetName() {
            return streetName;
        }

        public String getCrossStreet1() {
            return crossStreet1;
        }

        public String getCrossStreet2() {
            return crossStreet2;
        }

        public String getIntersectionStreet1() {
            return intersectionStreet1;
        }

        public String getIntersectionStreet2() {
            return intersectionStreet2;
        }

        public String getCity() {
            return city;
        }

        public String getLandmark() {
            return landmark;
        }

        public String getCommunity() {
            return community;
        }

        public String getBorough() {
            return borough;
        }

        public Address(AddressType addressType, int zipcode, int number, String streetName, String crossStreet1,
                       String crossStreet2, String intersectionStreet1, String intersectionStreet2,
                       String city, String landmark, String community, String borough) {
            this.addressType = addressType;
            this.zipcode = zipcode;
            this.number = number;
            this.streetName = streetName;
            this.crossStreet1 = crossStreet1;
            this.crossStreet2 = crossStreet2;
            this.intersectionStreet1 = intersectionStreet1;
            this.intersectionStreet2 = intersectionStreet2;
            this.city = city;
            this.landmark = landmark;
            this.community = community;
            this.borough = borough;
        }
    }

    class StateCoordinates {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public StateCoordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class Coordinates {
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
