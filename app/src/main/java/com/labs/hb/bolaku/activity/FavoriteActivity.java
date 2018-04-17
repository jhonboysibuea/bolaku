package com.labs.hb.bolaku.activity;

import android.content.Intent;
import android.content.res.TypedArray;
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
import com.labs.hb.bolaku.model.Teams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by BwX on 3/31/2018.
 */

public class FavoriteActivity  extends AppCompatActivity {


    private FirebaseFirestore firestoreDB;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    public FavoriteActivity() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);



        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.fb_favorite);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);

        init();
        getTeamList();
    }

    private void init(){
        linearLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(linearLayoutManager);
        firestoreDB = FirebaseFirestore.getInstance();
    }

    private void getTeamList(){
        Query query = firestoreDB.collection("Teams");

        FirestoreRecyclerOptions<Teams> response = new FirestoreRecyclerOptions.Builder<Teams>()
                .setQuery(query, Teams.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Teams, TeamsHolder>(response) {
            @Override
            public void onBindViewHolder(TeamsHolder holder, int position, final Teams model) {
                progressBar.setVisibility(View.GONE);

                holder.setNameTeam(model.getName());
                holder.setImageTeam(model.getTeam_pic());

            }

            @Override
            public TeamsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_favorite, group, false);

                return new TeamsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public class TeamsHolder extends RecyclerView.ViewHolder {

        View mView;

        public TeamsHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setNameTeam(String name) {
            TextView content = (TextView) mView.findViewById(R.id.menu_name);
            content.setText(name);
        }
        public void setImageTeam(String photo) {
            ImageView imageView = (ImageView) mView.findViewById(R.id.menu_photo);
            Picasso.with(mView.getContext()).load(photo)
                    .placeholder(R.drawable.a).into(imageView);
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
