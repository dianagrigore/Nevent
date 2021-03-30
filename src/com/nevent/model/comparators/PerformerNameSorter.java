package com.nevent.model.comparators;

import com.nevent.model.performer.Performer;

import java.util.Comparator;

public class PerformerNameSorter implements Comparator<Performer> {
    @Override
    public int compare(Performer performer1, Performer performer){
        return performer1.getName().compareToIgnoreCase(performer.getName());
    }
}
