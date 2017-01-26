package com.frostox.livechess.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.frostox.livechess.R;
import com.frostox.livechess.adapters.RankRecyclerAdapter;
import com.frostox.livechess.entities.Rank;
import com.frostox.livechess.entities.Round;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import chesspresso.game.Game;
import chesspresso.pgn.PGNReader;
import chesspresso.pgn.PGNSyntaxError;

public class RankActivity extends AppCompatActivity {

    RecyclerView rankRecyclerView;

    RankRecyclerAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference tournamentsRef, tRef, roundsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        database = FirebaseDatabase.getInstance();
        tournamentsRef = database.getReference("tournaments");
        tRef = tournamentsRef.child(getIntent().getStringExtra("key"));
        roundsRef = tRef.child("rounds");

        roundsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LinkedHashMap<String, Rank> rankLinkedHashMap = new LinkedHashMap<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Round round = postSnapshot.getValue(Round.class);

                    try {
                        StringReader sReader = new StringReader(round.getPgn());
                        PGNReader pgnReader = new PGNReader(sReader, "name");
                        Game game;
                        while((game = pgnReader.parseGame()) != null){
                            rankLinkedHashMap.put(game.getBlack(), new Rank(0.1f, game.getBlackElo(), 0, game.getBlack(), "MUM"));
                            rankLinkedHashMap.put(game.getWhite(), new Rank(0.1f, game.getWhiteElo(), 0, game.getWhite(), "MUM"));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (PGNSyntaxError pgnSyntaxError) {
                        pgnSyntaxError.printStackTrace();
                    }
                }

                List<Rank> ranks = new ArrayList<Rank>(rankLinkedHashMap.values());
                Collections.sort(ranks);
                Collections.reverse(ranks);
                int i = 1;
                for(Rank rank: ranks){
                    rank.setRank(i);
                    i++;
                }
                adapter = new RankRecyclerAdapter(ranks);
                rankRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(RankActivity.class.getName(), databaseError.getDetails());
            }
        });

        rankRecyclerView = (RecyclerView) findViewById(R.id.activity_rank_recycler_view);
//        adapter = new RankRecyclerAdapter(generateRanks());

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(getIntent().getStringExtra("name") + " standings");

//        rankRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rankRecyclerView.getContext(),
                layoutManager.getOrientation());
        rankRecyclerView.addItemDecoration(dividerItemDecoration);
    }


    public List<Rank> generateRanks(){
        List<Rank> ranks = new ArrayList<>();

        ranks.add(new Rank(30.2f, 3453, 1, "Hary Mou", "ARM"));
        ranks.add(new Rank(25.2f, 2568, 2, "May Amy", "ARM"));
        ranks.add(new Rank(24.2f, 2145, 3, "Ravi Prasad", "ARM"));
        ranks.add(new Rank(20.1f, 2369, 4, "Michael Cores", "ARM"));
        ranks.add(new Rank(14.0f, 2798, 5, "Honda Ray", "ARM"));
        ranks.add(new Rank(9.7f, 2356, 6, "Kamasotu Igotoro", "ARM"));

        return ranks;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }
}
