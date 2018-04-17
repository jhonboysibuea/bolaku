package com.labs.hb.bolaku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.fragment.BetDialog;
import com.labs.hb.bolaku.fragment.HistoryFragment;
import com.labs.hb.bolaku.fragment.PlaceHolderFragment;
import com.labs.hb.bolaku.fragment.RunFragment;
import com.labs.hb.bolaku.fragment.SearchFriendFragment;
import com.labs.hb.bolaku.model.Bet;
import com.labs.hb.bolaku.model.TeamChoosen;
import com.labs.hb.bolaku.util.Constants;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by BwX on 4/9/2018.
 */

public class BetActivity  extends AppCompatActivity {


    private FirebaseFirestore firestoreDB;

    private ImageView whoBet;

    FragmentTransaction ft;
    private LinearLayoutManager linearLayoutManager;
    private RunFragment runFragment;
    private HistoryFragment historyFragment;
    private String team_select;
    private String voor_team;
    private int bet;
    private BetDialog betDialog;
    private String matchday;
    private TeamChoosen teamchoosen1;
    private TeamChoosen teamchoosen2;

    public BetActivity() {
        super();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bet_activity);
        // set up the RecyclerView

        runFragment = new RunFragment();
        historyFragment = new HistoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, runFragment).commit();
        init();

        setSegment();

        ((RadioGroup) findViewById(R.id.bet)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ft = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.run:
                        ft.replace(R.id.container, runFragment);
                        break;
                    case R.id.history:
                        ft.replace(R.id.container, historyFragment);
                        break;

                }
                ft.commit();
            }
        });
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getApplication());

        firestoreDB = FirebaseFirestore.getInstance();
    }

    public void setSegment() {
        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.bet);
        segmented2.setTintColor(Color.DKGRAY);

        RadioButton place_bet = (RadioButton) findViewById(R.id.run);
        place_bet.setChecked(true);

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}