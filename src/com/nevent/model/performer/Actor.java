package com.nevent.model.performer;

import java.util.List;

public class Actor extends Performer{
    private List<String> awards;
    private List<String> pastProductions;

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public List<String> getPastProductions() {
        return pastProductions;
    }

    public void setPastProductions(List<String> pastProductions) {
        this.pastProductions = pastProductions;
    }
}
