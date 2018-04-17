package com.labs.hb.bolaku.fragment;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.model.Bet;
import com.labs.hb.bolaku.model.TeamChoosen;

import org.w3c.dom.Text;

/**
 * Created by BwX on 4/6/2018.
 */

public class BetDialog  extends Dialog {

    public static final String NAME_AND_AGE_STRING_FORMAT = "%s, %d";

    private TextView rival_team;
    private TextView rival_name;
    private TextView rival_voor;
    private TextView rival_bet;
    private TextView my_team;
    private TextView my_name;
    private TextView my_voor;
    private TextView my_bet;
    private String match_id;
    private FirebaseFirestore firebaseFirestore;
    private TeamChoosen teamChoosen1;
    private TeamChoosen teamChoosen2;

    public BetDialog(@NonNull final Context context, String match_id,TeamChoosen teamChoosen1, TeamChoosen teamChoosen2) {
        super(context);
        this.match_id= match_id;
        this.teamChoosen1=teamChoosen1;
        this.teamChoosen2=teamChoosen2;
        setCanceledOnTouchOutside(false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        setContentView(R.layout.dialog_bet);
        rival_team=(TextView) findViewById(R.id.home_name);
        rival_name=(TextView) findViewById(R.id.name_user1);
        rival_voor=(TextView) findViewById(R.id.voor_point1);
        rival_bet=(TextView) findViewById(R.id.bet_value1);
        my_team=(TextView) findViewById(R.id.away_name);
        my_name=(TextView) findViewById(R.id.name_user2);
        my_voor=(TextView) findViewById(R.id.voor_point2);
        my_bet=(TextView) findViewById(R.id.bet_value2);
        findViewById(R.id.ok_bet).setOnClickListener(new OnChatClicked());
    }

    public void setRow(@NonNull final TeamChoosen team1,TeamChoosen team2) {
        rival_bet.setText(team1.getAmount_bet());
        rival_voor.setText(team1.getVoor_point());
        rival_team.setText(team1.getTeam_name());

        my_bet.setText(team2.getAmount_bet());
        my_voor.setText(team2.getVoor_point());
        my_team.setText(team2.getTeam_name());

    }


    public class OnChatClicked implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            setMyBet("","",teamChoosen1,teamChoosen2);
            dismiss();
        }
    }
    private void setMyBet(String userId,String rivalId,TeamChoosen teamChoosenMe, TeamChoosen teamChoosenRival){
        Bet bet = new Bet();
        bet.setMatchDayId(match_id);
        bet.setStatus("RUN");
        bet.setTeam1(teamChoosenMe);
        bet.setResult("RUN");
        bet.setTeam2(teamChoosenRival);
        firebaseFirestore.collection("BET_USERS").document(userId).set(bet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        firebaseFirestore.collection("BET_USERS").document(rivalId).set(bet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }

}
