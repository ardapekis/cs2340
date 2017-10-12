package com.ardapekis.cs2340_27.model;

/**
 * Model class for location
 */

public class Location {
    private LocationType locationType;
    private Address address;
    private Coordinates coordinates;

    /** Getters and a method for getting the displayed address */
    public LocationType getLocationType() {
        return locationType;
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressString() {
        return address.getAddress();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /** Normal constructor
     *
     * @param locationType      The locationType of the location
     * @param address           The address of the location
     * @param coordinates       The coordinates of the location
     */
    public Location(LocationType locationType, Address address,
                    Coordinates coordinates) {
        this.locationType = locationType;
        this.address = address;
        this.coordinates = coordinates;
    }

    /**
     * Constructor that takes in individual elements
     *
     * @param locationTypeString        LocationType as a string
     * @param zipcode                   Zipcode for location
     * @param address                   Address string
     * @param city                      City string
     * @param borough                   Borough string
     * @param latitude                  Latitude as a double
     * @param longitude                 Longitude as a double
     */
    public Location(String locationTypeString, int zipcode, String address,
                    String city, String borough, double latitude, double longitude) {
        this.locationType = LocationType.parseString(locationTypeString);
        this.address = new Address(zipcode, address, city, borough);
        this.coordinates = new Coordinates(latitude, longitude);
    }

    /**
     * Enum for locationType, kind of unnecessary
     */
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

        /**
         * Matches up enums with their string attributes
         * @param s     The string to match
         * @return      The LocationType that matches w/ the string
         */
        public static LocationType parseString(String s) {
            switch (s) {
                case "3+ Family Mixed Use Building":
                    return LocationType.THREE_PLUS_MIXED_USE;
                case "Commercial Building":
                    return LocationType.COMMERCIAL;
                case "1-2 Family Dwelling":
                    return LocationType.ONE_TWO_FAMILY;
                case "3+ Family Apt. Building":
                    return LocationType.THREE_PLUS_FAMILY_APT;
                case "Public Stairs":
                    return LocationType.PUBLIC_STAIRS;
                case "Other (Explain Below)":
                    return LocationType.OTHER;
                case "Vacant Lot":
                    return LocationType.VACANT_LOT;
                case "Construction Site":
                    return LocationType.CONSTRUCTION_SITE;
                case "Hospital":
                    return LocationType.HOSPITAL;
                case "Parking Lot/Garage":
                    return LocationType.PARKING_LOT_GARAGE;
                case "Catch Basin/Sewer":
                    return CATCH_BASIN_SEWER;
                case "Vacant Building":
                    return VACANT_BUILDING;
                case "1-2 Family Mixed Use Building":
                    return ONE_TWO_MIXED_USE;
                case "Public Garden":
                    return PUBLIC_GARDEN;
                case "Government Building":
                    return GOVERNMENT;
                case "Office Building":
                    return OFFICE;
                case "School/Pre-School":
                    return SCHOOL_PRE_SCHOOL;
                case "Day Care/Nursery":
                    return DAY_CARE_NURSERY;
                case "Single Room Occupancy":
                    return SRO;
                case "Summer Camp":
                    return SUMMER_CAMP;
                default:
                    return null;
            }
        }

        public String toString() {
            return display;
        }
    }

    /**
     * Address holder class
     */
    public class Address {
        private int zipcode;
        private String address;
        private String city;
        private String borough;

        public int getZipcode() {
            return zipcode;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getBorough() {
            return borough;
        }

        public Address(int zipcode, String address,
                       String city, String borough) {
            this.zipcode = zipcode;
            this.address = address;
            this.city = city;
            this.borough = borough;
        }
    }

    /**
     * Coordinates holder class
     */
    public class Coordinates {
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
