package com.labs.hb.bolaku.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.holder.MyViewHolder;
import com.labs.hb.bolaku.model.Teams;

import java.util.List;

/**
 * Created by USER on 10/11/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private List<Teams> teams;

    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context,List <Teams> teams ) {
        this.mInflater = LayoutInflater.from(context);
        this.teams=teams;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_favorite, parent, false);
        MyViewHolder rcv = new MyViewHolder(view);
        return rcv;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String menu = teams.get(position).getName();
        holder.myTextView.setText(menu);
       // holder.myImage.setImageResource(teams.get(position).getTeam_pic());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return teams.size();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return teams.get(id).getId();
    }

}
