package com.labs.hb.bolaku.services;

import com.labs.hb.bolaku.responseAPI.Competitions;
import com.labs.hb.bolaku.responseAPI.fixtures.MatchDay;
import com.labs.hb.bolaku.responseAPI.team.Teams;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by BwX on 3/30/2018.
 */

public interface ApiInterface {

    @GET("competitions/?season=2018")
    Call<List<Competitions>> getCompetitions();

    @GET("competitions/467/teams")
    Call<Teams> getTeamsWorlCup();
    @GET("competitions/467/fixtures")
    Call<MatchDay> getMatchDay();

}
