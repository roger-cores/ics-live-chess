package com.frostox.livechess.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frostox.livechess.R;
import com.frostox.livechess.adapters.GamesRecyclerAdapter;
import com.frostox.livechess.adapters.TournamentsRecyclerAdapter;
import com.frostox.livechess.entities.GameEntity;
import com.frostox.livechess.entities.Round;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import chesspresso.game.Game;
import chesspresso.pgn.PGNReader;
import chesspresso.pgn.PGNSyntaxError;

/**
 * Created by roger on 11/3/2016.
 */
public class RoundFragment extends Fragment {


    private static final String ARG_KEY = "ARG_KEY";
    private static final String ARG_TOURNAMENT_KEY = "ARG_TOURNAMENT_KEY";

    RecyclerView gameRecyclerView;
    GamesRecyclerAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference roundRef;

    Round round;

    String tournamentKey, roundKey;


    public RoundFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RoundFragment newInstance(String tournamentKey, String key) {
        RoundFragment fragment = new RoundFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        args.putString(ARG_TOURNAMENT_KEY, tournamentKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_tournament_activity_new, container, false);
        gameRecyclerView = (RecyclerView) rootView.findViewById(R.id.round_fragment_recycler_view);

//        gameRecyclerView = (RecyclerView) rootView.findViewById(R.id.activity_tournament_recycler);
//        adapter = new GamesRecyclerAdapter(this, );
//        gameRecyclerView.setAdapter(adapter);
        gameRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        gameRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gameRecyclerView.setNestedScrollingEnabled(false);

        database = FirebaseDatabase.getInstance();
        roundRef = database.getReference("tournaments").child(getArguments().getString(ARG_TOURNAMENT_KEY)).child("rounds").child(getArguments().getString(ARG_KEY));
        tournamentKey = getArguments().getString(ARG_TOURNAMENT_KEY);
        roundKey = getArguments().getString(ARG_KEY);



        Log.d("testing in here", "tournaments/" + getArguments().getString(ARG_TOURNAMENT_KEY) + "/rounds/" + getArguments().getString(ARG_KEY));
        roundRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                round = dataSnapshot.getValue(Round.class);

                List<GameEntity> games = new ArrayList<>();

                StringReader sReader = new StringReader(round.getPgn());
                int i = 0;
                Game game = null;
                PGNReader pReader = new PGNReader(sReader, "name");

                try {
                    while((game = pReader.parseGame()) != null){
                        games.add(new GameEntity(game));
                    }
                } catch (PGNSyntaxError pgnSyntaxError) {
                    pgnSyntaxError.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                adapter = new GamesRecyclerAdapter(getActivity(), games, tournamentKey, roundKey);
                gameRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(RoundFragment.class.getName(), databaseError.getDetails());
            }
        });

        return rootView;
    }
}
