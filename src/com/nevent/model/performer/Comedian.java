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

    public Comedian( String name, String description, String genreOfComedy, String positionInShow, Integer tenure, Integer timePerSet, List<String> podcasts) {
        super(name, description);
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
    public void getPortrait() {
        System.out.println("id: " + this.getPerformerId() + "\n" + "name: " + this.getName()
                + "\n" + this.getDescription() + "\n Comedy genre: " + this.getGenreOfComedy() + "\n Podcasts: "
                + this.getPodcasts() + '\n');
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
        return Objects.hash(super.hashCode(), genreOfComedy, positionInShow, tenure, timePerSet, podcasts);
    }
}
