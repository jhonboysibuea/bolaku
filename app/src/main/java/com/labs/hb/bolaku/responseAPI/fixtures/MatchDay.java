
package com.labs.hb.bolaku.responseAPI.fixtures;

import java.util.List;


public class MatchDay {

    private  _links _links;

    private Integer count;

    private List<Fixture> fixtures = null;


    public  _links get_links() {
        return _links;
    }


    public void set_links( _links _links) {
        this._links = _links;
    }


    public Integer getCount() {
        return count;
    }


    public void setCount(Integer count) {
        this.count = count;
    }


    public List<Fixture> getFixtures() {
        return fixtures;
    }


    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

}
