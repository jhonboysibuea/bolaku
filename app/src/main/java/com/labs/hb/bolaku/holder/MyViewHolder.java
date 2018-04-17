package com.labs.hb.bolaku.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.labs.hb.bolaku.R;

/**
 * Created by USER on 11/11/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView myTextView;
    public ImageView myImage;
    public MyViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        myTextView = (TextView) itemView.findViewById(R.id.menu_name);
        myImage = (ImageView) itemView.findViewById(R.id.menu_photo);
    }

    @Override
    public void onClick(View view) {

    }


}
