package com.nevent.model.performer;


import java.util.List;
import java.util.Objects;

public class Singer extends Performer{
    private String musicGenre;
    private Boolean isGroup;
    private List<String> memberNames;
    private List<String> bestKnownSongs;

    public Singer(
                  String name,
                  String description,
                  String musicGenre,
                  Boolean isGroup,
                  List<String> memberNames,
                  List<String> bestKnownSongs) {
        super( name, description);
        this.musicGenre = musicGenre;
        this.isGroup = isGroup;
        this.memberNames = memberNames;
        this.bestKnownSongs = bestKnownSongs;
    }
    public Singer(
            String id,
            String name,
            String description,
            String musicGenre,
            Boolean isGroup,
            List<String> memberNames,
            List<String> bestKnownSongs) {
        super(id, name, description);
        this.musicGenre = musicGenre;
        this.isGroup = isGroup;
        this.memberNames = memberNames;
        this.bestKnownSongs = bestKnownSongs;
    }

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

    @Override
    public void getPortrait() {
        System.out.println("id: " + this.getPerformerId() + "\n" + "name: " + this.getName()
                + "\n" + this.getDescription() + "\n Music genre: " + this.getMusicGenre() + "\n Members: "
                + this.getMemberNames() + "\nBest known songs: " + this.getBestKnownSongs());
    }

    @Override
    public String toString() {
        return "Singer{" +
                "musicGenre='" + musicGenre + '\'' +
                ", isGroup=" + isGroup +
                ", memberNames=" + memberNames +
                ", bestKnownSongs=" + bestKnownSongs +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Singer singer = (Singer) o;
        return Objects.equals(musicGenre, singer.musicGenre) && Objects.equals(isGroup, singer.isGroup) && Objects.equals(memberNames, singer.memberNames) && Objects.equals(bestKnownSongs, singer.bestKnownSongs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), musicGenre, isGroup, memberNames, bestKnownSongs);
    }
}
