package com.labs.hb.bolaku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.labs.hb.bolaku.activity.AuthActivity;
import com.labs.hb.bolaku.activity.BetActivity;
import com.labs.hb.bolaku.activity.FavoriteActivity;
import com.labs.hb.bolaku.activity.MatchDayActivity;
import com.labs.hb.bolaku.responseAPI.Competitions;
import com.labs.hb.bolaku.responseAPI.Fixtures;
import com.labs.hb.bolaku.responseAPI.fixtures.Fixture;
import com.labs.hb.bolaku.responseAPI.fixtures.MatchDay;
import com.labs.hb.bolaku.responseAPI.team.Team;
import com.labs.hb.bolaku.responseAPI.team.Teams;
import com.labs.hb.bolaku.services.ApiClient;
import com.labs.hb.bolaku.services.ApiInterface;
import com.labs.hb.bolaku.util.CommonUtil;


import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    private  FirebaseFirestore firebaseFirestore;
    private static final String TAG="Insert To FireStore";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //getWorlCupTeams();
        getMatchDay();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent fav= new Intent(this, FavoriteActivity.class);
            startActivity(fav);

        } else if (id == R.id.nav_slideshow) {
            Intent fav= new Intent(this, MatchDayActivity.class);
            startActivity(fav);

        } else if (id == R.id.nav_manage) {
            Intent fav= new Intent(this, BetActivity.class);
            startActivity(fav);


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getWorlCupTeams(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Teams> call = apiService.getTeamsWorlCup();
        call.enqueue(new Callback<Teams>() {

            @Override
            public void onResponse(Call<Teams> call, Response<Teams> response) {
                Teams teams = response.body();
                List<com.labs.hb.bolaku.model.Teams> teamWordCup = new LinkedList<>();
try{
                int i = 1;
                for (Team a : teams.getTeams()) {
                    com.labs.hb.bolaku.model.Teams t = new com.labs.hb.bolaku.model.Teams();
                    t.setId(i + "");
                    t.setName(a.getName());
                    t.setSeason_year("2018");
                    t.setTeam_code(a.getCode()+"");
                    t.setTeam_type("WC");
                    t.setTeam_pic(a.getCrestUrl()+"");
                    t.setTeam_id("WC" +CommonUtil.getId(a.get_links().getSelf().getHref())+ "");

                    firebaseFirestore.collection("Teams").document(CommonUtil.getId(a.get_links().getSelf().getHref()))
                            .set(t).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });


                    teamWordCup.add(t);
                    i++;

                }
}catch (URISyntaxException u){

}

            }
            @Override
            public void onFailure(Call<Teams> call, Throwable t) {

            }
        });
    }
    public void getCompetitions(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Competitions>> call = apiService.getCompetitions();
        call.enqueue(new Callback<List<Competitions>>() {
            @Override
            public void onResponse(Call<List<Competitions>> call, Response<List<Competitions>> response) {

                Log.d("horas",response.body().get(0).getLinks().getSelf().getHref()+"");
            }

            @Override
            public void onFailure(Call<List<Competitions>> call, Throwable t) {
                // Log error here since request failed
                Log.e("Error", t.toString());
            }
        });
    }
    public void getMatchDay(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MatchDay> call = apiService.getMatchDay();
        call.enqueue(new Callback<MatchDay>() {
            @Override
            public void onResponse(Call<MatchDay> call, Response<MatchDay> response) {
                com.labs.hb.bolaku.model.MatchDay matchDayLocal;
                com.labs.hb.bolaku.model.Result result;
                for(Fixture e: response.body().getFixtures()){
                    matchDayLocal= new com.labs.hb.bolaku.model.MatchDay ();
                    result= new com.labs.hb.bolaku.model.Result();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                    try {
                        matchDayLocal.setDate(new Date(String.valueOf(sdf.parse(e.getDate().toString()))));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        matchDayLocal.setAwayTeamId(CommonUtil.getId(e.get_links().getAwayTeam().getHref()).toString());

                        matchDayLocal.setAwayTeamName(e.getAwayTeamName());
                        matchDayLocal.setHomeTeamId(CommonUtil.getId(e.get_links().getHomeTeam().getHref()).toString());
                        matchDayLocal.setHomeTeamName(e.getHomeTeamName());
                        matchDayLocal.setResult(result);
                        matchDayLocal.setMatchDayId(CommonUtil.getId(e.get_links().getSelf().getHref()));
                        result.setGaolAwayTeam(e.getResult().getGoalsAwayTeam()+"");
                        result.setGoalHomeTeam(e.getResult().getGoalsHomeTeam()+"");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        firebaseFirestore.collection("matchday").document(CommonUtil.getId(e.get_links().getSelf().getHref()))
                                .set(matchDayLocal).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MatchDay> call, Throwable t) {
                // Log error here since request failed
                Log.e("Error", t.toString());
            }
        });
    }
    @Override
    public void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            sendToStart();

        } else {



        }
    }

    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(startIntent);
        finish();

    }
}
