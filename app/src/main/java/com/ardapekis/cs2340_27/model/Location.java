package com.ardapekis.cs2340_27.model;

/**
 * Model class for location
 */

public class Location {
    private Address address;
    private Coordinates coordinates;

    /** Getters and a method for getting the displayed address */

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
     * @param address           The address of the location
     * @param coordinates       The coordinates of the location
     */
    public Location(Address address,
                    Coordinates coordinates) {
        this.address = address;
        this.coordinates = coordinates;
    }

    /**
     * Constructor that takes in individual elements
     *
     * @param locationType              LocationType string
     * @param zipcode                   Zipcode for location
     * @param address                   Address string
     * @param city                      City string
     * @param borough                   Borough string
     * @param latitude                  Latitude as a double
     * @param longitude                 Longitude as a double
     */
    public Location(String locationType, int zipcode, String address,
                    String city, String borough, double latitude, double longitude) {
        this.address = new Address(locationType, zipcode, address, city, borough);
        this.coordinates = new Coordinates(latitude, longitude);
    }

    /**
     * Address holder class
     */
    public class Address {
        private String locationType;
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

        public String getLocationType() {
            return locationType;
        }

        public String getCity() {
            return city;
        }

        public String getBorough() {
            return borough;
        }

        public Address(String locationType, int zipcode, String address,
                       String city, String borough) {
            this.locationType = locationType;
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
