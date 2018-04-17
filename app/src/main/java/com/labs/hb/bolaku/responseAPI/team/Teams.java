
package com.labs.hb.bolaku.responseAPI.team;


import java.util.List;

public class Teams {


    private   _links _links;

    private Integer count;

    private List<Team> teams = null;


    public  _links get_links() {
        return _links;
    }


    public void set_links(_links _links) {
        this._links = _links;
    }


    public Integer getCount() {
        return count;
    }


    public void setCount(Integer count) {
        this.count = count;
    }


    public List<Team> getTeams() {
        return teams;
    }


    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

}
