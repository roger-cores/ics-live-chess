package com.frostox.livechess.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.frostox.livechess.R;
import com.frostox.livechess.adapters.TournamentsAdapter;
import com.frostox.livechess.adapters.TournamentsRecyclerAdapter;
import com.frostox.livechess.entities.Tournament;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by roger on 10/23/2016.
 */
public class TournamentsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView tournamentsListView;

    public TournamentsFragment() {}


    public static TournamentsFragment newInstance(int sectionNumber) {
        TournamentsFragment fragment = new TournamentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.launch_fragment_tournaments, container, false);
        tournamentsListView = (RecyclerView) rootView.findViewById(R.id.tournaments_listView);
        TournamentsRecyclerAdapter adapter = new TournamentsRecyclerAdapter(this.getActivity(), generateTournaments());
        tournamentsListView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        tournamentsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tournamentsListView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(tournamentsListView.getContext(),
                layoutManager.getOrientation());
        tournamentsListView.addItemDecoration(dividerItemDecoration);
        //tournamentsListView.setOnItemClickListener(this);

        return rootView;


    }

    public List<Tournament> generateTournaments(){
        ArrayList<Tournament> tournaments = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.DATE, 1);
        start.add(Calendar.MONTH, -2);
        tournaments.add(new Tournament("ICS: Under 17", start.getTime(), end.getTime(), null, false));
        end.add(Calendar.MONTH, 5);
        start.add(Calendar.MONTH, -3);
        start.add(Calendar.DATE, -45);
        end.add(Calendar.DATE, 6);
        tournaments.add(new Tournament("ICS: Under 15", start.getTime(), end.getTime(), "Ravi Vs Hari", true));
        start.add(Calendar.YEAR, -3);
        end.add(Calendar.YEAR, -1);
        tournaments.add(new Tournament("Fall 17", start.getTime(), end.getTime(), "Patel won with 8.5/11 pts!", false));
        return tournaments;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("tag", "clicked");
    }
}
