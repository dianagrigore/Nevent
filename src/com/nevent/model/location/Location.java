package com.nevent.model.location;

import java.util.Objects;

public class Location {
    private String id;
    private String nameOfVenue;
    private String address;
    private String city;
    private Seating seating;
    private static Integer numberOfLocations = 0;

    public Location(String nameOfVenue, String address, String city, Seating seating) {
        this.id = "LOC" + (++numberOfLocations).toString();
        this.nameOfVenue = nameOfVenue;
        this.address = address;
        this.city = city;
        this.seating = seating;
    }
    public void describeLocation(){
        System.out.println("Location with id: " + this.getId() + "\nVenue name: " +
                this.getNameOfVenue() + "\nAddress: " + this.getAddress() +
                "\nCity: " + this.getCity());
    }

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



    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", nameOfVenue='" + nameOfVenue + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", seating=" + seating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(nameOfVenue, location.nameOfVenue) && Objects.equals(address, location.address) && Objects.equals(city, location.city) && Objects.equals(seating, location.seating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameOfVenue, address, city, seating);
    }
}
