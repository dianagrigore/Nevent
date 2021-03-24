package com.nevent.model.performer;

import java.util.List;

public class Comedian extends Performer{
    private String genreOfComedy;
    private String positionInShow;
    private Integer tenure;
    private Integer timePerSet;
    List<String> podcasts;

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
}
