package com.frostox.livechess.adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.frostox.livechess.R;
import com.frostox.livechess.wrappers.SQIWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chesspresso.Chess;
import chesspresso.game.Game;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;

/**
 * Created by roger on 13/4/16.
 */

public class GridAdapter extends BaseAdapter {


    private Activity activity;

    private int flipped = 0;

    private LayoutInflater layoutInflater;

    private Game game;

    private int fromSqi;

    private int score = 0;

    private List<SQIWrapper> sqis;

    public void flipBoard(){
        if(flipped == 0) flipped = 63;
        else flipped = 0;
    }


    public void setGame(Game game){
        this.game = game;
    }

    public void setPieceToSqi(Integer piece, int color, int index, Boolean isFromSqi){
        sqis.get(index).setPiece(piece);
        sqis.get(index).setColor(color);
        sqis.get(index).setFromSqi(isFromSqi);
    }



    // Gets the context so it can be used later
    public GridAdapter(Activity activity, LayoutInflater inflater, List<SQIWrapper> sqis) {
        this.activity = activity;
        layoutInflater = inflater;
        this.sqis = sqis;


    }

    // Total number of things contained within the adapter
    public int getCount() {
        if(sqis != null)
            return sqis.size();
        else return 0;
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        if(sqis != null)
            return sqis.get(position);
        else return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position,
                        View convertView, ViewGroup parent) {

        final SQIWrapper sqi = sqis.get(Math.abs(flipped-position));

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
           convertView = layoutInflater.inflate(R.layout.piece, null);
        }

        if(sqi.getFromSqi()== null){
            if(sqi.isBlack())
                convertView.setBackgroundColor(activity.getResources().getColor(R.color.black_bg));
            else convertView.setBackgroundColor(activity.getResources().getColor(android.R.color.white));
        } else if(sqi.getFromSqi()){
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.fromSqi));
        } else {
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.toSqi));
        }

        final ImageView piece = (ImageView) convertView.findViewById(R.id.piece);

        if(sqi.getPiece() == null){

        } else {
            setPiece(sqi.getPiece(), sqi.getColor(), piece);
        }





        return convertView;


    }

    public void setPiece(Integer pieceId, int color, ImageView pieceView){
        pieceView.setVisibility(View.VISIBLE);
        if(pieceId == Chess.PAWN){
            pieceView.setImageResource(color==1?R.drawable.black_p2:R.drawable.white_p2);
        } else if(pieceId == Chess.KING){
            pieceView.setImageResource(color==1?R.drawable.black_k2:R.drawable.white_k2);

        } else if(pieceId == Chess.QUEEN){
            pieceView.setImageResource(color==1?R.drawable.black_q2:R.drawable.white_q2);

        } else if(pieceId == Chess.BISHOP){
            pieceView.setImageResource(color==1?R.drawable.black_b2:R.drawable.white_b2);

        } else if(pieceId == Chess.KNIGHT){
            pieceView.setImageResource(color==1?R.drawable.black_n2:R.drawable.white_n2);

        } else if(pieceId == Chess.ROOK){
            pieceView.setImageResource(color==1?R.drawable.black_r2:R.drawable.white_r2);

        } else {
            pieceView.setVisibility(View.INVISIBLE);
        }
    }



    public void stepBack(){
        Move move = game.getPosition().getLastMove();
        game.goBack();
        if(move == null) return;

        sqis.get(move.getToSqi()).setPiece(game.getPosition().getPiece(move.getToSqi()));
        sqis.get(move.getToSqi()).setColor(game.getPosition().getColor(move.getToSqi()));

        sqis.get(move.getFromSqi()).setPiece(game.getPosition().getPiece(move.getFromSqi()));
        sqis.get(move.getFromSqi()).setColor(game.getPosition().getColor(move.getFromSqi()));

        this.notifyDataSetChanged();

    }

    public void stepForward(){
        game.goForward();
        Move move = game.getPosition().getLastMove();
        if(move == null) return;

        sqis.get(move.getToSqi()).setPiece(game.getPosition().getPiece(move.getToSqi()));
        sqis.get(move.getToSqi()).setColor(game.getPosition().getColor(move.getToSqi()));

        sqis.get(move.getFromSqi()).setPiece(game.getPosition().getPiece(move.getFromSqi()));
        sqis.get(move.getFromSqi()).setColor(game.getPosition().getColor(move.getFromSqi()));

        this.notifyDataSetChanged();

    }

    public String getPieceName(int piece){
        switch (piece){
            case Chess.PAWN:
                return "PAWN";
            case Chess.KING:
                return "KING";
            case Chess.QUEEN:
                return "QUEEN";
            case Chess.ROOK:
                return "ROOK";
            case Chess.BISHOP:
                return "BISHOP";
            case Chess.KNIGHT:
                return "KNIGHT";

        }

        return "UNKNOWN";
    }

}