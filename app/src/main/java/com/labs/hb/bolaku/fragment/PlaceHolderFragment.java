package com.labs.hb.bolaku.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.model.Bet;
import com.labs.hb.bolaku.model.TeamChoosen;
import com.labs.hb.bolaku.model.Team_Spinner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by BwX on 4/5/2018.
 */

public   class PlaceHolderFragment extends Fragment {

    private Spinner spinner_voor;
    private Spinner spinner_team;
    private Button submit;
    private EditText amount_bet;
    private   String team_select;
    private   String voor_point;
    List<Team_Spinner> team_arrays= new LinkedList<>();
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    public PlaceHolderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_set_bet, container, false);
        spinner_voor = (Spinner) rootView.findViewById(R.id.spinner_voor);
        submit=rootView.findViewById(R.id.submit_bet);
        amount_bet=(EditText)rootView.findViewById(R.id.amount_bet);
        spinner_voor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                voor_point= spinner_voor.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Team_Spinner team_spinner1= new Team_Spinner();
        Team_Spinner team_spinner2= new Team_Spinner();
        String home_team_id=getActivity().getIntent().getStringExtra("home_team_id");
        String home_team_name=getActivity().getIntent().getStringExtra("home_team_name");
        team_spinner1.setId(home_team_id);
        team_spinner1.setName(home_team_name);
        String away_team_id=getActivity().getIntent().getStringExtra("away_team_id");
        String away_team_name=getActivity().getIntent().getStringExtra("away_team_name");
        final String matchdayId= getActivity().getIntent().getStringExtra("match_day_id");
        team_spinner2.setName(away_team_name);
        team_spinner2.setId(away_team_id);
        team_arrays.clear();
        team_arrays.add(team_spinner1);
        team_arrays.add(team_spinner2);
        addTeamSppiner(rootView,team_arrays);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bet bet = new Bet();
                bet.setMatchDayId(matchdayId);
                bet.setStatus("available");
                TeamChoosen teamChoosen= new TeamChoosen();
                teamChoosen.setTeam_id(team_select);
                teamChoosen.setUser_id(mAuth.getCurrentUser().getUid());
                teamChoosen.setTeam_name("");
                teamChoosen.setVoor_point(voor_point);
                teamChoosen.setAmount_bet(amount_bet.getText().toString());

                bet.setTeam1(teamChoosen);
                bet.setResult("RUN");
                firebaseFirestore.collection("Bet").add(bet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });

            }
        });
        return rootView;
    }

    public void addTeamSppiner(View rootView, final List<Team_Spinner> team_arrays ) {

        spinner_team = (Spinner) rootView.findViewById(R.id.spinner_team);
        List<String> spinnerTeam= new LinkedList<>();
        for(Team_Spinner team_spinner : team_arrays){
            spinnerTeam.add(team_spinner.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,spinnerTeam);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_team.setAdapter(dataAdapter);
        spinner_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                team_select=team_arrays.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
