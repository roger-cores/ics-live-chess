package com.frostox.livechess.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frostox.livechess.R;
import com.frostox.livechess.entities.Tournament;
import com.frostox.livechess.util.DateFormatter;
import com.frostox.livechess.viewholders.TournamentsViewHolder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by roger on 10/27/2016.
 */
public class TournamentsAdapter extends ArrayAdapter<Tournament> {

    List<Tournament> tournaments;



    public TournamentsAdapter(Context context, int resource, int textViewResourceId, List<Tournament> objects) {
        super(context, resource, textViewResourceId, objects);
        this.tournaments = objects;
    }

    @Override
    public Tournament getItem(int position) {
        if(tournaments!=null && tournaments.size()>position){
            return tournaments.get(position);
        } else return null;
    }

    @Override
    public int getCount() {
        if(tournaments != null){
            return tournaments.size();
        } else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TournamentsViewHolder viewHolder;
        Tournament tournament = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tournaments_parent_item, null);
            viewHolder = new TournamentsViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TournamentsViewHolder) convertView.getTag();
        }

        viewHolder.getTitle().setText(tournament.getTitle());
        viewHolder.getSubTitle().setText(DateFormatter.formateDateRange(tournament.getStart(), tournament.getEnd()));

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        start.setTime(tournament.getStart());
        end.setTime(tournament.getEnd());



        if(now.after(start) && now.before(end)){
            viewHolder.getBoard().setImageResource(R.mipmap.ic_launcher);
        } else {
            viewHolder.getBoard().setImageResource(R.mipmap.ic_game_offline);
        }


        viewHolder.getExpand().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.getChildViewGroup().getVisibility() == View.VISIBLE){
                    viewHolder.getChildViewGroup().setVisibility(View.GONE);
                    viewHolder.getExpand().setImageResource(R.drawable.ic_action_angle_arrow_down);

                } else {
                    viewHolder.getChildViewGroup().setVisibility(View.VISIBLE);
                    viewHolder.getExpand().setImageResource(R.drawable.ic_action_up_arrow_key);
                }
            }
        });




        return convertView;
    }
}
