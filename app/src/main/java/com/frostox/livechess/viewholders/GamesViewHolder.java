package com.frostox.livechess.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.frostox.livechess.R;

/**
 * Created by roger on 10/27/2016.
 */
public class GamesViewHolder extends RecyclerView.ViewHolder {

    GridView board;

    TextView player1, player2, rtgPlayer1, rtgPlayer2, timePlayer1, timePlayer2;
    TextView result, tableNo;

    View container;

    public GamesViewHolder(View itemView) {
        super(itemView);
        board = (GridView) itemView.findViewById(R.id.board);
        player1 = (TextView) itemView.findViewById(R.id.game_item_p1_name);
        player2 = (TextView) itemView.findViewById(R.id.game_item_p2_name);
        rtgPlayer1 = (TextView) itemView.findViewById(R.id.game_item_p1_rtg);
        rtgPlayer2 = (TextView) itemView.findViewById(R.id.game_item_p2_rtg);
        timePlayer1 = (TextView) itemView.findViewById(R.id.game_item_p1_time);
        timePlayer2 = (TextView) itemView.findViewById(R.id.game_item_p2_time);
        container = itemView.findViewById(R.id.game_item_container);
        result = (TextView) itemView.findViewById(R.id.game_item_p1_name7);
        tableNo = (TextView) itemView.findViewById(R.id.game_item_table_no);
    }

    public TextView getResult() {
        return result;
    }

    public void setResult(TextView result) {
        this.result = result;
    }

    public View getContainer() {
        return container;
    }

    public void setContainer(View container) {
        this.container = container;
    }

    public GridView getBoard() {
        return board;
    }

    public void setBoard(GridView board) {
        this.board = board;
    }

    public TextView getPlayer1() {
        return player1;
    }

    public void setPlayer1(TextView player1) {
        this.player1 = player1;
    }

    public TextView getPlayer2() {
        return player2;
    }

    public void setPlayer2(TextView player2) {
        this.player2 = player2;
    }

    public TextView getRtgPlayer1() {
        return rtgPlayer1;
    }

    public void setRtgPlayer1(TextView rtgPlayer1) {
        this.rtgPlayer1 = rtgPlayer1;
    }

    public TextView getRtgPlayer2() {
        return rtgPlayer2;
    }

    public void setRtgPlayer2(TextView rtgPlayer2) {
        this.rtgPlayer2 = rtgPlayer2;
    }

    public TextView getTimePlayer1() {
        return timePlayer1;
    }

    public void setTimePlayer1(TextView timePlayer1) {
        this.timePlayer1 = timePlayer1;
    }

    public TextView getTimePlayer2() {
        return timePlayer2;
    }

    public void setTimePlayer2(TextView timePlayer2) {
        this.timePlayer2 = timePlayer2;
    }

    public TextView getTableNo() {
        return tableNo;
    }

    public void setTableNo(TextView tableNo) {
        this.tableNo = tableNo;
    }
}
