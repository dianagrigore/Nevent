package com.nevent.model.comparators;

import com.nevent.model.location.Location;

import java.util.Comparator;

public class LocationCitySorter implements Comparator<Location> {
    @Override
    public int compare(Location location, Location location1){
        return location.getCity().compareToIgnoreCase(location1.getCity());
    }
}
