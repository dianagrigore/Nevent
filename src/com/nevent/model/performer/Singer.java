package com.nevent.model.performer;

import java.util.List;

public class Singer extends Performer{
    private String musicGenre;
    private Boolean isGroup;
    private List<String> memberNames;
    private List<String> bestKnownSongs;

    public String getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    public Boolean getGroup() {
        return isGroup;
    }

    public void setGroup(Boolean group) {
        isGroup = group;
    }

    public List<String> getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(List<String> memberNames) {
        this.memberNames = memberNames;
    }

    public List<String> getBestKnownSongs() {
        return bestKnownSongs;
    }

    public void setBestKnownSongs(List<String> bestKnownSongs) {
        this.bestKnownSongs = bestKnownSongs;
    }
}
