package com.nevent.model.location;

public class Location {
    private String id;
    private String nameOfVenue;
    private String address;
    private String city;
    private Seating seating;
    private static Integer numberOfLocations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameOfVenue() {
        return nameOfVenue;
    }

    public void setNameOfVenue(String nameOfVenue) {
        this.nameOfVenue = nameOfVenue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Seating getSeating() {
        return seating;
    }

    public void setSeating(Seating seating) {
        this.seating = seating;
    }

    public static Integer getNumberOfLocations() {
        return numberOfLocations;
    }

    public static void setNumberOfLocations(Integer numberOfLocations) {
        Location.numberOfLocations = numberOfLocations;
    }
}
