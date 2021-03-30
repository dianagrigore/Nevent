package com.nevent.model.comparators;

import com.nevent.model.event.Event;

import java.util.Comparator;

public class EventDurationSorter implements Comparator<Event> {
    @Override
    public int compare(Event event1, Event event2){
        return event1.getDuration().compareTo(event2.getDuration());
    }
}
