
package com.labs.hb.bolaku.responseAPI.team;


public class Team {


    private _links_ _links;

    private String name;

    private String code;

    private String shortName;

    private String squadMarketValue;

    private String crestUrl;


    public _links_ get_links() {
        return _links;
    }


    public void set_links(_links_ _links) {
        this._links = _links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


    public String getSquadMarketValue() {
        return squadMarketValue;
    }

    public void setSquadMarketValue(String squadMarketValue) {
        this.squadMarketValue = squadMarketValue;
    }


    public String getCrestUrl() {
        return crestUrl;
    }


    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

}
