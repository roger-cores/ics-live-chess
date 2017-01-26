package com.frostox.livechess.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frostox.livechess.R;
import com.frostox.livechess.activities.RankActivity;
import com.frostox.livechess.activities.TournamentActivityNew;
import com.frostox.livechess.entities.Tournament;
import com.frostox.livechess.util.DateFormatter;
import com.frostox.livechess.viewholders.TournamentsViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by roger on 10/27/2016.
 */
public class TournamentsRecyclerAdapter extends RecyclerView.Adapter<TournamentsViewHolder> {



    private HashMap<String, Tournament> tournaments;
    private Activity activity;

    public TournamentsRecyclerAdapter(Activity activity, HashMap<String, Tournament> tournaments) {
        this.activity = activity;
        this.tournaments = tournaments;
    }

    @Override
    public TournamentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournaments_parent_item, parent, false);
        return new TournamentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TournamentsViewHolder holder, int position) {
        final Tournament tournament = new ArrayList<>(tournaments.values()).get(position);
        final String key = new ArrayList<>(tournaments.keySet()).get(position);

        holder.getTitle().setText(tournament.getName());
        String partialSubTitle = "";
        if(tournament.getStatus() != null){
            partialSubTitle = ", " + tournament.getStatus();
        }

        holder.getSubTitle().setText(DateFormatter.formateDateRange(new Date(tournament.getDateStart()), new Date(tournament.getDateEnd())) + partialSubTitle);

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        Log.d("date", new Date().getTime()+"");

        start.setTime(new Date(tournament.getDateStart()));
        end.setTime(new Date(tournament.getDateEnd()));

        if(tournament.getNotificationStatus()){
            holder.getNotificationButton().setImageResource(R.drawable.ic_action_notifications_button);
        } else {
            holder.getNotificationButton().setImageResource(R.drawable.ic_action_notifications_bell_button);
        }



        if(now.after(start) && now.before(end)){
            if(tournament.getLive()){
                holder.getBoard().setImageResource(R.mipmap.ic_live);
            } else {
                holder.getBoard().setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            holder.getBoard().setImageResource(R.mipmap.ic_game_offline);
        }

        holder.getExpand().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.getChildViewGroup().getVisibility() == View.VISIBLE){
                    holder.getChildViewGroup().setVisibility(View.GONE);
                    holder.getExpand().setImageResource(R.drawable.ic_action_angle_arrow_down);


                } else {
                    holder.getChildViewGroup().setVisibility(View.VISIBLE);
                    holder.getExpand().setImageResource(R.drawable.ic_action_up_arrow_key);
                }
            }
        });

        holder.getNotificationContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tournament.setNotificationStatus(!tournament.getNotificationStatus());
                if(tournament.getNotificationStatus()){
                    holder.getNotificationButton().setImageResource(R.drawable.ic_action_notifications_button);

                } else {
                    holder.getNotificationButton().setImageResource(R.drawable.ic_action_notifications_bell_button);
                }
            }
        });

        holder.getRankContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RankActivity.class);
                intent.putExtra("name", tournament.getName());
                intent.putExtra("key", key);
                activity.startActivity(intent);
            }
        });

        holder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TournamentActivityNew.class);
                intent.putExtra("name", tournament.getName());
                intent.putExtra("key", key);
                activity.startActivity(intent);
            }
        });
    }

    public HashMap<String, Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(HashMap<String, Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @Override
    public int getItemCount() {
        if(tournaments != null){
            return tournaments.size();
        } else return 0;
    }
}
