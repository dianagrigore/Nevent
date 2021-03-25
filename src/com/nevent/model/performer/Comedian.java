package com.nevent.model.performer;

import com.nevent.model.event.Event;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Comedian extends Performer{
    private String genreOfComedy;
    private String positionInShow;
    private Integer tenure;
    private Integer timePerSet;
    List<String> podcasts;

    public Comedian(String performerId, String name, String description, Set<Event> currentEvents, String genreOfComedy, String positionInShow, Integer tenure, Integer timePerSet, List<String> podcasts) {
        super(performerId, name, description, currentEvents);
        this.genreOfComedy = genreOfComedy;
        this.positionInShow = positionInShow;
        this.tenure = tenure;
        this.timePerSet = timePerSet;
        this.podcasts = podcasts;
    }

    public String getGenreOfComedy() {
        return genreOfComedy;
    }

    public void setGenreOfComedy(String genreOfComedy) {
        this.genreOfComedy = genreOfComedy;
    }

    public String getPositionInShow() {
        return positionInShow;
    }

    public void setPositionInShow(String positionInShow) {
        this.positionInShow = positionInShow;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Integer getTimePerSet() {
        return timePerSet;
    }

    public void setTimePerSet(Integer timePerSet) {
        this.timePerSet = timePerSet;
    }

    public List<String> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(List<String> podcasts) {
        this.podcasts = podcasts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comedian comedian = (Comedian) o;
        return Objects.equals(genreOfComedy, comedian.genreOfComedy) && Objects.equals(positionInShow, comedian.positionInShow) && Objects.equals(tenure, comedian.tenure) && Objects.equals(timePerSet, comedian.timePerSet) && Objects.equals(podcasts, comedian.podcasts);
    }

    @Override
    public int hashCode() {
        int hashCode = 31;
        int prime = 17;
        hashCode = genreOfComedy == null ? 0 : prime * genreOfComedy.hashCode();
        hashCode += positionInShow == null ? 0 : prime * positionInShow.hashCode();
        hashCode += tenure == null ? 0 : prime * tenure.hashCode();
        hashCode += timePerSet == null ? 0 : prime * timePerSet.hashCode();
        hashCode += podcasts == null ? 0 : prime * podcasts.hashCode();
        hashCode += super.hashCode();
        return hashCode;
    }
}
