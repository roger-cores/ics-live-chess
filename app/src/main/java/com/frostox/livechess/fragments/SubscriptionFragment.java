package com.frostox.livechess.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frostox.livechess.R;

/**
 * Created by roger on 10/23/2016.
 */
public class SubscriptionFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public SubscriptionFragment() {
    }


    public static SubscriptionFragment newInstance(int sectionNumber) {
        SubscriptionFragment fragment = new SubscriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.launch_fragment_subscriptions, container, false);





        return rootView;


    }
}
