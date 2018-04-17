package com.labs.hb.bolaku.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.adapter.MyRecyclerViewAdapter;
import com.labs.hb.bolaku.model.MatchDay;
import com.labs.hb.bolaku.model.Teams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by BwX on 3/31/2018.
 */

public class MatchDayActivity  extends AppCompatActivity {


    private FirebaseFirestore firestoreDB;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    public MatchDayActivity() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchday);



        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.matchday_list);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);


        init();
        getMatcDayList();
    }

    private void init(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        firestoreDB = FirebaseFirestore.getInstance();
    }

    private void getMatcDayList(){
        Query query = firestoreDB.collection("matchday");

        FirestoreRecyclerOptions<MatchDay> response = new FirestoreRecyclerOptions.Builder<MatchDay>()
                .setQuery(query, MatchDay.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<MatchDay, MatchDayHolder>(response) {
            @Override
            public void onBindViewHolder(MatchDayHolder holder, int position, final MatchDay model) {

                progressBar.setVisibility(View.GONE);

                holder.setNameTeam(model.getHomeTeamName(),model.getAwayTeamName());
                holder.mView.findViewById(R.id.challange_with_friend).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent intent= new Intent(MatchDayActivity.this,ChallangeFriendActivity.class);
                            intent.putExtra("home_team_id",model.getHomeTeamId());
                            intent.putExtra("home_team_name",model.getHomeTeamName());
                            intent.putExtra("away_team_id",model.getAwayTeamId());
                            intent.putExtra("away_team_name",model.getAwayTeamName());
                            intent.putExtra("match_day_id",model.getMatchDayId());

                            startActivity(intent);
                    }
                });

            }

            @Override
            public MatchDayHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.match_list, group, false);

                return new MatchDayHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public class MatchDayHolder extends RecyclerView.ViewHolder {

        View mView;



        public MatchDayHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setNameTeam(String homeName,String awayName) {
            TextView homeNameTextView = (TextView) mView.findViewById(R.id.home_name);
            homeNameTextView.setText(homeName);
            TextView awayNameTextView = (TextView) mView.findViewById(R.id.away_name);
            awayNameTextView.setText(awayName);
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
