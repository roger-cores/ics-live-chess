package com.frostox.livechess.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.frostox.livechess.R;
import com.frostox.livechess.adapters.GamesRecyclerAdapter;
import com.frostox.livechess.entities.GameEntity;

import java.util.ArrayList;
import java.util.List;

public class TournamentActivity extends AppCompatActivity {

    RecyclerView gameRecyclerView;

    GamesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(getIntent().getStringExtra("name"));

        gameRecyclerView = (RecyclerView) findViewById(R.id.activity_tournament_recycler);
        adapter = new GamesRecyclerAdapter(this, generateGames());
        gameRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gameRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }


    public List<GameEntity> generateGames(){
        List<GameEntity> games = new ArrayList<>();

        games.add(new GameEntity(getString(R.string.pgn1)));
        games.add(new GameEntity(getString(R.string.pgn1)));
        games.add(new GameEntity(getString(R.string.pgn1)));

        return games;
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
