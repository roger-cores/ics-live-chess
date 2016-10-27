package com.frostox.livechess.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frostox.livechess.R;
import com.frostox.livechess.entities.Rank;
import com.frostox.livechess.entities.Tournament;
import com.frostox.livechess.viewholders.RankViewHolder;
import com.frostox.livechess.viewholders.TournamentsViewHolder;

import java.util.List;

/**
 * Created by roger on 10/27/2016.
 */
public class RankRecyclerAdapter extends RecyclerView.Adapter<RankViewHolder> {

    private List<Rank> ranks;

    public RankRecyclerAdapter(List<Rank> ranks) {
        this.ranks = ranks;
    }



    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        return new RankViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        Rank rank = ranks.get(position);

        holder.getRank().setText(rank.getRank() + ".");
        holder.getName().setText(rank.getName());
        holder.getFed().setText("FED: " + rank.getFed());
        holder.getRtg().setText("RTG: " + rank.getRtg());
        holder.getPts().setText(rank.getPts() + "");

    }

    @Override
    public int getItemCount() {
        if(ranks != null){
            return ranks.size();
        } else return 0;
    }
}
