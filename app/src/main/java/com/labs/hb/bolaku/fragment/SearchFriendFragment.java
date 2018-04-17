package com.labs.hb.bolaku.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labs.hb.bolaku.R;

/**
 * Created by BwX on 4/5/2018.
 */

public  class SearchFriendFragment extends Fragment {



    public SearchFriendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_search_friend, container, false);



        return rootView;
    }



}