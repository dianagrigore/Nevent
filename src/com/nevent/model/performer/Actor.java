package com.nevent.model.performer;


import java.util.List;
import java.util.Objects;

public class Actor extends Performer{
    private List<String> awards;
    private List<String> pastProductions;

    public Actor(String name, String description,
                 List<String> awards, List<String> pastProductions) {
        super(name, description);
        this.awards = awards;
        this.pastProductions = pastProductions;
    }

    public Actor(String id, String name, String description,
                 List<String> awards, List<String> pastProductions) {
        super(id, name, description);
        this.awards = awards;
        this.pastProductions = pastProductions;
    }

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

    @Override
    public void getPortrait(){
            System.out.println("id: " + this.getPerformerId() + "\n" + "name: " + this.getName()
            + "\n" + this.getDescription() + "\n Awards: " + this.getAwards() + "\n Past productions: "
            + this.getPastProductions() + '\n');
    }

    @Override
    public String toString() {
        return "Actor{" +
                "awards=" + awards +
                ", pastProductions=" + pastProductions +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(awards, actor.awards) && Objects.equals(pastProductions, actor.pastProductions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), awards, pastProductions);
    }
}
