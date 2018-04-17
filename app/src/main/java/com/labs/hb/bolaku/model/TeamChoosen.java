package com.labs.hb.bolaku.model;

/**
 * Created by BwX on 4/5/2018.
 */

public class TeamChoosen {
    private String team_id;
    private String team_name;
    private String voor_point;
    private String user_id;
    private String amount_bet;

    public TeamChoosen() {
    }


    public TeamChoosen(String team_id, String team_name, String voor_point, String user_id, String amount_bet) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.voor_point = voor_point;
        this.user_id = user_id;
        this.amount_bet = amount_bet;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getVoor_point() {
        return voor_point;
    }

    public void setVoor_point(String voor_point) {
        this.voor_point = voor_point;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAmount_bet() {
        return amount_bet;
    }

    public void setAmount_bet(String amount_bet) {
        this.amount_bet = amount_bet;
    }
}
