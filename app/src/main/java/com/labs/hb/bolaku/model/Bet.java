package com.labs.hb.bolaku.model;

/**
 * Created by BwX on 4/5/2018.
 */

public class Bet {
    private String matchDayId;
    private TeamChoosen team1;
    private TeamChoosen team2;
    private String status;
    private String result;

    public Bet() {
    }


    public Bet(String matchDayId, TeamChoosen team1, TeamChoosen team2, String status, String result) {
        this.matchDayId = matchDayId;
        this.team1 = team1;
        this.team2 = team2;
        this.status = status;
        this.result = result;
    }

    public String getMatchDayId() {
        return matchDayId;
    }

    public void setMatchDayId(String matchDayId) {
        this.matchDayId = matchDayId;
    }

    public TeamChoosen getTeam1() {
        return team1;
    }

    public void setTeam1(TeamChoosen team1) {
        this.team1 = team1;
    }

    public TeamChoosen getTeam2() {
        return team2;
    }

    public void setTeam2(TeamChoosen team2) {
        this.team2 = team2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
