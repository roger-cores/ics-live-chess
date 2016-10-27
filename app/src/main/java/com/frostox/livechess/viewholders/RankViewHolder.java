package com.frostox.livechess.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.frostox.livechess.R;

/**
 * Created by roger on 10/27/2016.
 */
public class RankViewHolder extends RecyclerView.ViewHolder {

    private TextView rank;

    private TextView name;

    private TextView fed;

    private TextView rtg;

    private TextView pts;


    public RankViewHolder(View itemView) {
        super(itemView);
        rank = (TextView) itemView.findViewById(R.id.rank_item_rank);
        name = (TextView) itemView.findViewById(R.id.rank_item_name);
        fed = (TextView) itemView.findViewById(R.id.rank_item_fed);
        rtg = (TextView) itemView.findViewById(R.id.rank_item_rtg);
        pts = (TextView) itemView.findViewById(R.id.rank_item_pts);
    }

    public TextView getRank() {
        return rank;
    }

    public void setRank(TextView rank) {
        this.rank = rank;
    }

    public TextView getPts() {
        return pts;
    }

    public void setPts(TextView pts) {
        this.pts = pts;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getFed() {
        return fed;
    }

    public void setFed(TextView fed) {
        this.fed = fed;
    }

    public TextView getRtg() {
        return rtg;
    }

    public void setRtg(TextView rtg) {
        this.rtg = rtg;
    }
}
