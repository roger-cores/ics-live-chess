package com.frostox.livechess.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frostox.livechess.R;

/**
 * Created by roger on 10/27/2016.
 */
public class TournamentsViewHolder extends RecyclerView.ViewHolder{

    private TextView title;

    private TextView subTitle;

    private ImageView board;

    private ImageView live;

    private ImageView expand;

    private LinearLayout childViewGroup;

    private LinearLayout notificationContainer;

    private ImageView notificationButton;

    private LinearLayout rankContainer;

    private RelativeLayout container;



    public TournamentsViewHolder(View itemView) {
        super(itemView);
        this.setTitle((TextView) itemView.findViewById(R.id.tournaments_parent_item_title));
        this.setSubTitle((TextView) itemView.findViewById(R.id.tournaments_parent_item_subtitle));
        this.setBoard((ImageView) itemView.findViewById(R.id.tournaments_parent_item_board));
//        this.setLive((ImageView) itemView.findViewById(R.id.tournaments_parent_item_live));
        this.setExpand((ImageView) itemView.findViewById(R.id.tournaments_parent_item_expand));
        this.setChildViewGroup((LinearLayout) itemView.findViewById(R.id.tournaments_parent_child_container));
        this.setNotificationContainer((LinearLayout) itemView.findViewById(R.id.tournaments_parent_item_notification_container));
        this.setNotificationButton((ImageView) itemView.findViewById(R.id.tournaments_parent_item_notification_button));
        this.setContainer((RelativeLayout) itemView.findViewById(R.id.tournaments_parent_item_container));
        this.setRankContainer((LinearLayout) itemView.findViewById(R.id.tournaments_parent_item_rank_container));
        //itemView.setTag(this);
    }

    public LinearLayout getRankContainer() {
        return rankContainer;
    }

    public void setRankContainer(LinearLayout rankContainer) {
        this.rankContainer = rankContainer;
    }

    public RelativeLayout getContainer() {
        return container;
    }

    public void setContainer(RelativeLayout container) {
        this.container = container;
    }

    public LinearLayout getNotificationContainer() {
        return notificationContainer;
    }

    public void setNotificationContainer(LinearLayout notificationContainer) {
        this.notificationContainer = notificationContainer;
    }

    public ImageView getNotificationButton() {
        return notificationButton;
    }

    public void setNotificationButton(ImageView notificationButton) {
        this.notificationButton = notificationButton;
    }

    public LinearLayout getChildViewGroup() {
        return childViewGroup;
    }

    public void setChildViewGroup(LinearLayout childViewGroup) {
        this.childViewGroup = childViewGroup;
    }

    public ImageView getBoard() {
        return board;
    }

    public void setBoard(ImageView board) {
        this.board = board;
    }

    public ImageView getLive() {
        return live;
    }

    public void setLive(ImageView live) {
        this.live = live;
    }

    public ImageView getExpand() {
        return expand;
    }

    public void setExpand(ImageView expand) {
        this.expand = expand;
    }

    public TextView getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(TextView subTitle) {
        this.subTitle = subTitle;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }
}
