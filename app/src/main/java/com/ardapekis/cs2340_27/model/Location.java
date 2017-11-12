package com.ardapekis.cs2340_27.model;

/**
 * Model class for location
 */

public class Location {
    private Address address;
    private Coordinates coordinates;

    /**
     *
     * Getter
     *
     * @return the address of the Location
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * Getter
     *
     * @return the AddressString of the Location
     */
    public String getAddressString() {
        return address.getAddress();
    }

    /**
     *
     * Getter
     *
     * @return the coordinates of the Location
     */
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

        /**
         *
         * Getter
         *
         * @return the zipcode of the Address
         */
        public int getZipcode() {
            return zipcode;
        }

        /**
         *
         * Getter
         *
         * @return the address of the Address
         */
        public String getAddress() {
            return address;
        }

        /**
         *
         * Getter
         *
         * @return the locationType of the Address
         */
        public String getLocationType() {
            return locationType;
        }

        /**
         *
         * Getter
         *
         * @return the city of the Address
         */
        public String getCity() {
            return city;
        }

        /**
         *
         * Getter
         *
         * @return the borough of the Address
         */
        public String getBorough() {
            return borough;
        }

        /**
         *
         * Class initializer
         *
         * @param locationType the location type
         * @param zipcode the zipcode
         * @param address the address
         * @param city the city
         * @param borough the borough
         */
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

        /**
         *
         * Getter
         *
         * @return the latitude of the Coordinates
         */
        public double getLatitude() {
            return latitude;
        }

        /**
         *
         * Getter
         *
         * @return the longitude of the Coordinates
         */
        public double getLongitude() {
            return longitude;
        }

        /**
         *
         * Class initializer
         *
         * @param latitude the latitude
         * @param longitude the longitude
         */
        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
