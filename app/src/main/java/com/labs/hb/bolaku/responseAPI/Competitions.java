package com.labs.hb.bolaku.responseAPI;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BwX on 3/30/2018.
 */

public class Competitions {
    @SerializedName("_links")
    private Links links;

    private Integer id;

    private String caption;

    private String league;

    private String year;

    private Integer currentMatchday;

    private Integer numberOfMatchdays;

    private Integer numberOfTeams;

    private Integer numberOfGames;

    private String lastUpdated;


    public Links getLinks() {
        return links;
    }


    public void setLinks(Links links) {
        this.links = links;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getCaption() {
        return caption;
    }


    public void setCaption(String caption) {
        this.caption = caption;
    }


    public String getLeague() {
        return league;
    }


    public void setLeague(String league) {
        this.league = league;
    }


    public String getYear() {
        return year;
    }


    public void setYear(String year) {
        this.year = year;
    }


    public Integer getCurrentMatchday() {
        return currentMatchday;
    }


    public void setCurrentMatchday(Integer currentMatchday) {
        this.currentMatchday = currentMatchday;
    }

    public Integer getNumberOfMatchdays() {
        return numberOfMatchdays;
    }


    public void setNumberOfMatchdays(Integer numberOfMatchdays) {
        this.numberOfMatchdays = numberOfMatchdays;
    }


    public Integer getNumberOfTeams() {
        return numberOfTeams;
    }


    public void setNumberOfTeams(Integer numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }


    public Integer getNumberOfGames() {
        return numberOfGames;
    }


    public void setNumberOfGames(Integer numberOfGames) {
        this.numberOfGames = numberOfGames;
    }


    public String getLastUpdated() {
        return lastUpdated;
    }


    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
