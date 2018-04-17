package com.labs.hb.bolaku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.fragment.BetDialog;
import com.labs.hb.bolaku.fragment.PlaceHolderFragment;
import com.labs.hb.bolaku.fragment.SearchFriendFragment;
import com.labs.hb.bolaku.model.Bet;
import com.labs.hb.bolaku.model.TeamChoosen;
import com.labs.hb.bolaku.model.Team_Spinner;
import com.labs.hb.bolaku.model.Teams;
import com.labs.hb.bolaku.util.CommonUtil;
import com.labs.hb.bolaku.util.Constants;

import java.util.LinkedList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by BwX on 4/5/2018.
 */


public class ChallangeFriendActivity  extends AppCompatActivity  {


    private FirebaseFirestore firestoreDB;
    private RecyclerView recyclerView;
    private ImageView whoBet;
    private FirestoreRecyclerAdapter adapter;
    FragmentTransaction ft;
    private LinearLayoutManager linearLayoutManager;
    private PlaceHolderFragment placeholderFragment;
    private SearchFriendFragment searchFriendFragment;
    private String team_select;
    private String voor_team;
    private int bet;
    private BetDialog betDialog;
    private String matchday;
    private TeamChoosen teamchoosen1;
    private TeamChoosen teamchoosen2;
    public ChallangeFriendActivity() {
        super();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.challange_friend_activity);
        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.list_friend_available);

        placeholderFragment= new PlaceHolderFragment();
        searchFriendFragment= new SearchFriendFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, placeholderFragment).commit();
        init();

        getFriendAvailbleBet();
        setSegment();

        ((RadioGroup) findViewById(R.id.bet)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ft = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.placebet:
                        ft.replace(R.id.container, placeholderFragment);
                        break;
                    case R.id.searcfriend:
                        ft.replace(R.id.container, searchFriendFragment);
                        break;

                }
                ft.commit();
            }
        });
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(linearLayoutManager);
        firestoreDB = FirebaseFirestore.getInstance();
    }

    public void setSegment() {
        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.bet);
        segmented2.setTintColor(Color.DKGRAY);

        RadioButton place_bet = (RadioButton) findViewById(R.id.placebet);
        place_bet.setChecked(true);

    }

    private void getFriendAvailbleBet() {
        Query query = firestoreDB.collection("Bet").whereEqualTo("status","available").limit(1);

        FirestoreRecyclerOptions<Bet> response = new FirestoreRecyclerOptions.Builder<Bet>()
                .setQuery(query, Bet.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Bet, BetHolder>(response) {
            @Override
            public void onBindViewHolder(BetHolder holder, int position, final Bet model) {

                holder.setHomeName(getIntent().getStringExtra("home_team_name"));
                holder.setNameUser1(model.getTeam1().getUser_id());
                holder.setBetValue1(model.getTeam1().getAmount_bet());
                holder.setVoorPointUser1(model.getTeam1().getVoor_point());
                holder.setAwayName(getIntent().getStringExtra("away_team_name"));

                matchday=getIntent().getStringExtra(Constants.MATCH_DAY_ID);
                teamchoosen1= new TeamChoosen();
                teamchoosen1.setTeam_name(getIntent().getStringExtra(Constants.HOME_TEAM_NAME));
                teamchoosen1.setAmount_bet(model.getTeam1().getAmount_bet());
                teamchoosen1.setVoor_point(model.getTeam1().getVoor_point());
                teamchoosen2= new TeamChoosen();
                teamchoosen2.setTeam_name(getIntent().getStringExtra(Constants.AWAY_TEAM_NAME));
                teamchoosen2.setAmount_bet(model.getTeam1().getAmount_bet());

                teamchoosen2.setVoor_point(model.getTeam1().getVoor_point());


                holder.mView.findViewById(R.id.who_bet).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        betDialog = new BetDialog(ChallangeFriendActivity.this,matchday,teamchoosen1,teamchoosen2);

                        betDialog.setRow(teamchoosen1,teamchoosen2);
                        betDialog.show();
                    }
                });
            }

            @Override
            public BetHolder onCreateViewHolder(ViewGroup group, int i) {

                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_bet, group, false);

                return new BetHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }




    public class BetHolder extends RecyclerView.ViewHolder {

        View mView;
        private ImageView whoBet;

        public BetHolder(View itemView) {
            super(itemView);
            mView = itemView;
            whoBet= (ImageView) mView.findViewById(R.id.who_bet);
        }
        public void setHomeName(String name){
            TextView homeName=(TextView) mView.findViewById(R.id.home_name);
            homeName.setText(name);

        }
        public void setAwayName(String name){
            TextView awayName=(TextView) mView.findViewById(R.id.away_name);
            awayName.setText(name);
        }
        public void setNameUser1(String name){
            TextView user1=(TextView) mView.findViewById(R.id.name_user1);
            user1.setText(name);
        }
        public void setVoorPointUser1(String name){
            TextView user1=(TextView) mView.findViewById(R.id.voor_point1);
            user1.setText(name);
        }
        public void setBetValue1(String name){
            TextView user1=(TextView) mView.findViewById(R.id.bet_value1);
            user1.setText(name);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }




}
