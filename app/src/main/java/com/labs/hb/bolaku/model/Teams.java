package com.labs.hb.bolaku.model;

/**
 * Created by BwX on 3/30/2018.
 */

public class Teams {
    private String id;
    private String team_id;
    private String name;
    private String team_code;
    private String team_pic;
    private String team_type;
    private String season_year;

    public Teams() {
    }

    public Teams(String id, String team_id, String name, String team_code, String team_pic, String team_type, String season_year) {
        this.id = id;
        this.team_id = team_id;
        this.name = name;
        this.team_code = team_code;
        this.team_pic = team_pic;
        this.team_type = team_type;
        this.season_year = season_year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam_code() {
        return team_code;
    }

    public void setTeam_code(String team_code) {
        this.team_code = team_code;
    }

    public String getTeam_pic() {
        return team_pic;
    }

    public void setTeam_pic(String team_pic) {
        this.team_pic = team_pic;
    }

    public String getTeam_type() {
        return team_type;
    }

    public void setTeam_type(String team_type) {
        this.team_type = team_type;
    }

    public String getSeason_year() {
        return season_year;
    }

    public void setSeason_year(String season_year) {
        this.season_year = season_year;
    }
}
