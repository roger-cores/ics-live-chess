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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by roger on 10/23/2016.
 */
public class TournamentsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView tournamentsListView;

    FirebaseDatabase database;
    DatabaseReference tournamentsRef;
    TournamentsRecyclerAdapter adapter;


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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        tournamentsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tournamentsListView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(tournamentsListView.getContext(),
                layoutManager.getOrientation());
        tournamentsListView.addItemDecoration(dividerItemDecoration);
        //tournamentsListView.setOnItemClickListener(this);


        database = FirebaseDatabase.getInstance();
        tournamentsRef = database.getReference("tournaments");

        tournamentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Tournament> tournaments = new LinkedHashMap<String, Tournament>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                     tournaments.put(postSnapshot.getKey(), postSnapshot.getValue(Tournament.class));
                }

                adapter = new TournamentsRecyclerAdapter(getActivity() , tournaments);
                tournamentsListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                tournamentsRef.removeEventListener(this);
                tournamentsRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Tournament tournament = dataSnapshot.getValue(Tournament.class);
                        adapter.getTournaments().put(dataSnapshot.getKey(), tournament);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Tournament tournament = dataSnapshot.getValue(Tournament.class);
                        adapter.getTournaments().put(dataSnapshot.getKey(), tournament);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        adapter.getTournaments().remove(dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TournamentsFragment.class.getName(), databaseError.getDetails());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TournamentsFragment.class.getName(), databaseError.getDetails());
            }
        });

        return rootView;


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("tag", "clicked");
    }
}
