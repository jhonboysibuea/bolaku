package com.labs.hb.bolaku.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.activity.ChallangeFriendActivity;
import com.labs.hb.bolaku.model.Bet;
import com.labs.hb.bolaku.model.TeamChoosen;
import com.labs.hb.bolaku.model.Team_Spinner;
import com.labs.hb.bolaku.util.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by BwX on 4/9/2018.
 */

public class RunFragment extends Fragment {

    private Spinner spinner_voor;
    private Spinner spinner_team;
    private Button submit;
    private EditText amount_bet;
    private   String team_select;
    private   String voor_point;
    List<Team_Spinner> team_arrays= new LinkedList<>();
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore firestoreDB;
    private ProgressBar progressBar;
    public RunFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_run, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.run_fragment_list);
        progressBar= (ProgressBar) rootView.findViewById(R.id.progress_bar);
        init();
        getMyBetRun();
        return rootView;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        firestoreDB = FirebaseFirestore.getInstance();
    }
    private void getMyBetRun() {
        Query query = firestoreDB.collection("Bet").whereEqualTo("result","RUN").whereEqualTo("team1.user_id",mAuth.getCurrentUser().getUid());
        query.whereEqualTo("team2.user_id",mAuth.getCurrentUser().getUid());
        query.limit(10);

        FirestoreRecyclerOptions<Bet> response = new FirestoreRecyclerOptions.Builder<Bet>()
                .setQuery(query, Bet.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Bet, BetHolder>(response) {
            @Override
            public void onBindViewHolder(BetHolder holder, int position, final Bet model) {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public  BetHolder onCreateViewHolder(ViewGroup group, int i) {

                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_bet_me, group, false);

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
