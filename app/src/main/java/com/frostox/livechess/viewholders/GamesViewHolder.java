package com.frostox.livechess.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.frostox.livechess.R;

/**
 * Created by roger on 10/27/2016.
 */
public class GamesViewHolder extends RecyclerView.ViewHolder {

    GridView board;

    public GamesViewHolder(View itemView) {
        super(itemView);
        board = (GridView) itemView.findViewById(R.id.board);
    }

    public GridView getBoard() {
        return board;
    }

    public void setBoard(GridView board) {
        this.board = board;
    }
}
