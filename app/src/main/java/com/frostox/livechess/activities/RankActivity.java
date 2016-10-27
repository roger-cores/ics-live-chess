package com.frostox.livechess.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.frostox.livechess.R;
import com.frostox.livechess.adapters.RankRecyclerAdapter;
import com.frostox.livechess.entities.Rank;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {

    RecyclerView rankRecyclerView;

    RankRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        rankRecyclerView = (RecyclerView) findViewById(R.id.activity_rank_recycler_view);
        adapter = new RankRecyclerAdapter(generateRanks());

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(getIntent().getStringExtra("name") + " standings");

        rankRecyclerView.setAdapter(adapter);
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
