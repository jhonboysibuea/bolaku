package com.labs.hb.bolaku.model;

import java.util.Date;

/**
 * Created by BwX on 4/4/2018.
 */

public class MatchDay {

    private String homeTeamName;
    private String awayTeamName;
    private Result result;
    private int mathday;
    private Date date;
    private String homeTeamId;
    private String awayTeamId;
    private String matchDayId;

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getMathday() {
        return mathday;
    }

    public void setMathday(int mathday) {
        this.mathday = mathday;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getMatchDayId() {
        return matchDayId;
    }

    public void setMatchDayId(String matchDayId) {
        this.matchDayId = matchDayId;
    }
}


